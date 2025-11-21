// java
package iscteiul.ista.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Game.
 * Author: LEI105514
 * Date: 2025-11-21 00:00
 * Cyclomatic Complexity:
 * - constructor: 1
 * - fire(): 5
 * - getShots(): 1
 * - getRepeatedShots(): 1
 * - getInvalidShots(): 1
 * - getHits(): 1
 * - getSunkShips(): 1
 * - getRemainingShips(): 1
 * - validShot(): 5 (private, covered indirectly)
 * - repeatedShot(): 3 (private, covered indirectly)
 * - printBoard(): 6
 * - printValidShots(): 1
 * - printFleet(): 2
 */
public class GameTest {

    private Fleet fleet;
    private Game game;

    @BeforeEach
    public void setUp() throws Exception {
        fleet = new Fleet();
        game = new Game(fleet);

        // Garantir que os contadores privados estejam inicializados para evitar NPE
        try {
            Field fInvalid = Game.class.getDeclaredField("countInvalidShots");
            Field fRepeated = Game.class.getDeclaredField("countRepeatedShots");
            Field fHits = Game.class.getDeclaredField("countHits");
            Field fSinks = Game.class.getDeclaredField("countSinks");
            fInvalid.setAccessible(true);
            fRepeated.setAccessible(true);
            fHits.setAccessible(true);
            fSinks.setAccessible(true);

            if (fInvalid.get(game) == null) fInvalid.set(game, Integer.valueOf(0));
            if (fRepeated.get(game) == null) fRepeated.set(game, Integer.valueOf(0));
            if (fHits.get(game) == null) fHits.set(game, Integer.valueOf(0));
            if (fSinks.get(game) == null) fSinks.set(game, Integer.valueOf(0));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void tearDown() {
        fleet = null;
        game = null;
    }

    // constructor: 1
    @Test
    public void constructor() {
        assertNotNull(game, "Error: expected Game instance but got null");
    }

    // fire(): 5 tests (fire1..fire5)
    @Test
    public void fire1() {
        // invalid shot -> out of bounds row < 0
        IPosition pos = new TestPos(-1, 0);
        IShip result = game.fire(pos);
        assertAll(
                () -> assertNull(result, "Error: expected null result for invalid shot but got non-null"),
                () -> assertEquals(1, game.getInvalidShots(), "Error: expected invalidShots == 1 after invalid shot but got " + game.getInvalidShots())
        );
    }

    @Test
    public void fire2() {
        // repeated shot -> same valid position fired twice
        IPosition pos = new TestPos(1, 1);
        game.fire(pos); // first attempt
        IShip second = game.fire(pos); // repeated
        assertAll(
                () -> assertNull(second, "Error: expected null for repeated shot result but got non-null"),
                () -> assertEquals(1, game.getRepeatedShots(), "Error: expected repeatedShots == 1 after repeating shot but got " + game.getRepeatedShots())
        );
    }

    @Test
    public void fire3() {
        // miss valid shot -> no ship at position
        IPosition pos = new TestPos(2, 2);
        IShip result = game.fire(pos);
        assertAll(
                () -> assertNull(result, "Error: expected null result for miss but got non-null"),
                () -> assertEquals(0, game.getHits(), "Error: expected hits == 0 after miss but got " + game.getHits()),
                () -> assertEquals(0, game.getSunkShips(), "Error: expected sunkShips == 0 after miss but got " + game.getSunkShips())
        );
    }

    @Test
    public void fire4() {
        // hit but not sink -> ship remains floating after shot
        TestShip s = new TestShip("Sloop", 0, 0, 0, 0, true, false, false, 2); // size 2 so not sunk on first shot
        TestPos p = new TestPos(3, 3);
        s.setOccupiesPos(p);
        fleet.getShips().clear();
        fleet.getShips().add(s);

        IShip res = game.fire(p);
        assertAll(
                () -> assertNull(res, "Error: expected null when hit does not sink but got a ship"),
                () -> assertEquals(1, game.getHits(), "Error: expected hits == 1 after hit but not sink but got " + game.getHits()),
                () -> assertEquals(0, game.getSunkShips(), "Error: expected sunkShips == 0 when ship still floating but got " + game.getSunkShips())
        );
    }

    @Test
    public void fire5() {
        // hit and sink -> ship sinks and is returned
        TestShip s = new TestShip("Brig", 0, 0, 0, 0, true, false, true, 1); // sinks on first shot
        TestPos p = new TestPos(4, 4);
        s.setOccupiesPos(p);
        fleet.getShips().clear();
        fleet.getShips().add(s);

        IShip res = game.fire(p);
        assertAll(
                () -> assertNotNull(res, "Error: expected ship returned when sink occurs but got null"),
                () -> assertEquals(1, game.getHits(), "Error: expected hits == 1 after sinking shot but got " + game.getHits()),
                () -> assertEquals(1, game.getSunkShips(), "Error: expected sunkShips == 1 after sinking but got " + game.getSunkShips())
        );
    }

    // getShots(): 1
    @Test
    public void getShots() {
        assertEquals(0, game.getShots().size(), "Error: expected 0 shots initially but got " + game.getShots().size());
        IPosition p = new TestPos(5, 5);
        game.fire(p);
        assertEquals(1, game.getShots().size(), "Error: expected 1 shot after fire but got " + game.getShots().size());
    }

    // getRepeatedShots(): 1
    @Test
    public void getRepeatedShots() {
        IPosition p = new TestPos(6, 6);
        game.fire(p);
        game.fire(p);
        assertEquals(1, game.getRepeatedShots(), "Error: expected repeatedShots == 1 after repeating shot but got " + game.getRepeatedShots());
    }

    // getInvalidShots(): 1
    @Test
    public void getInvalidShots() {
        game.fire(new TestPos(-5, 0));
        assertEquals(1, game.getInvalidShots(), "Error: expected invalidShots == 1 for out of bounds shot but got " + game.getInvalidShots());
    }

    // getHits(): 1
    @Test
    public void getHits() {
        TestShip s = new TestShip("Galeao", 0, 0, 0, 0, true, false, true, 1);
        TestPos p = new TestPos(7, 7);
        s.setOccupiesPos(p);
        fleet.getShips().clear();
        fleet.getShips().add(s);
        game.fire(p);
        assertEquals(1, game.getHits(), "Error: expected hits == 1 after hitting ship but got " + game.getHits());
    }

    // getSunkShips(): 1
    @Test
    public void getSunkShips() {
        TestShip s = new TestShip("Clipper", 0, 0, 0, 0, true, false, true, 1);
        TestPos p = new TestPos(8, 8);
        s.setOccupiesPos(p);
        fleet.getShips().clear();
        fleet.getShips().add(s);
        game.fire(p);
        assertEquals(1, game.getSunkShips(), "Error: expected sunkShips == 1 after sink but got " + game.getSunkShips());
    }

    // getRemainingShips(): 1
    @Test
    public void getRemainingShips() {
        TestShip a = new TestShip("A", 0, 0, 0, 0, true, false, false, 1); // floating
        TestShip b = new TestShip("B", 0, 0, 0, 0, true, false, false, 1); // floating
        TestShip c = new TestShip("C", 0, 0, 0, 0, true, false, true, 1);  // sunk
        fleet.getShips().clear();
        TestPos pa = new TestPos(1, 1);
        TestPos pb = new TestPos(2, 2);
        TestPos pc = new TestPos(3, 3);
        a.setOccupiesPos(pa); b.setOccupiesPos(pb); c.setOccupiesPos(pc);
        fleet.getShips().add(a); fleet.getShips().add(b); fleet.getShips().add(c);
        // sink c
        game.fire(pc);
        assertEquals(2, game.getRemainingShips(), "Error: expected 2 remaining (floating) ships but got " + game.getRemainingShips());
    }

    // validShot(): 5 tests (covered indirectly via fire)
    @Test
    public void validShot1() {
        // row < 0 => invalid
        game.fire(new TestPos(-1, 0));
        assertEquals(1, game.getInvalidShots(), "Error: expected invalidShots == 1 for row < 0 but got " + game.getInvalidShots());
    }

    @Test
    public void validShot2() {
        // row > BOARD_SIZE => invalid
        int out = Fleet.BOARD_SIZE + 1;
        game.fire(new TestPos(out, 0));
        assertEquals(1, game.getInvalidShots(), "Error: expected invalidShots == 1 for row > BOARD_SIZE but got " + game.getInvalidShots());
    }

    @Test
    public void validShot3() {
        // col < 0 => invalid
        game.fire(new TestPos(0, -1));
        assertEquals(1, game.getInvalidShots(), "Error: expected invalidShots == 1 for col < 0 but got " + game.getInvalidShots());
    }

    @Test
    public void validShot4() {
        // col > BOARD_SIZE => invalid
        int out = Fleet.BOARD_SIZE + 1;
        game.fire(new TestPos(0, out));
        assertEquals(1, game.getInvalidShots(), "Error: expected invalidShots == 1 for col > BOARD_SIZE but got " + game.getInvalidShots());
    }

    @Test
    public void validShot5() {
        // all inside bounds => valid (no invalid increment)
        game.fire(new TestPos(0, 0));
        assertEquals(0, game.getInvalidShots(), "Error: expected invalidShots == 0 for valid shot but got " + game.getInvalidShots());
    }

    // repeatedShot(): 3 tests (covered via fire)
    @Test
    public void repeatedShot1() {
        // no repeat
        game.fire(new TestPos(1, 2));
        assertEquals(0, game.getRepeatedShots(), "Error: expected repeatedShots == 0 when no repeat happened but got " + game.getRepeatedShots());
    }

    @Test
    public void repeatedShot2() {
        // single repeat
        TestPos p = new TestPos(2, 3);
        game.fire(p);
        game.fire(p);
        assertEquals(1, game.getRepeatedShots(), "Error: expected repeatedShots == 1 after single repeat but got " + game.getRepeatedShots());
    }

    @Test
    public void repeatedShot3() {
        // different positions -> only identical repeats counted
        TestPos a = new TestPos(3, 4);
        TestPos b = new TestPos(4, 5);
        game.fire(a);
        game.fire(b);
        game.fire(a);
        assertEquals(1, game.getRepeatedShots(), "Error: expected repeatedShots == 1 for one repeated position but got " + game.getRepeatedShots());
    }

    // printBoard(): 6 tests
    @Test
    public void printBoard1() {
        // empty positions
        assertDoesNotThrow(() -> game.printBoard(new ArrayList<>(), '.'), "Error: printBoard should not throw for empty positions");
    }

    @Test
    public void printBoard2() {
        // single position inside board
        List<IPosition> list = new ArrayList<>();
        list.add(new TestPos(0, 0));
        assertDoesNotThrow(() -> game.printBoard(list, 'X'), "Error: printBoard should not throw for single position");
    }

    @Test
    public void printBoard3() {
        // multiple positions, including duplicates
        List<IPosition> list = new ArrayList<>();
        list.add(new TestPos(1, 1));
        list.add(new TestPos(1, 1));
        list.add(new TestPos(2, 2));
        assertDoesNotThrow(() -> game.printBoard(list, '#'), "Error: printBoard should not throw for multiple positions including duplicates");
    }

    @Test
    public void printBoard4() {
        // positions at board limits
        List<IPosition> list = new ArrayList<>();
        list.add(new TestPos(Fleet.BOARD_SIZE - 1, Fleet.BOARD_SIZE - 1));
        assertDoesNotThrow(() -> game.printBoard(list, '$'), "Error: printBoard should not throw for positions at board limits");
    }

    @Test
    public void printBoard5() {
        // many positions
        List<IPosition> list = new ArrayList<>();
        for (int i = 0; i < Math.min(5, Fleet.BOARD_SIZE); i++) list.add(new TestPos(i, i));
        assertDoesNotThrow(() -> game.printBoard(list, '*'), "Error: printBoard should not throw for many positions");
    }

    @Test
    public void printBoard6() {
        // marker different char
        List<IPosition> list = new ArrayList<>();
        list.add(new TestPos(2, 1));
        assertDoesNotThrow(() -> game.printBoard(list, 'Z'), "Error: printBoard should not throw for different marker char");
    }

    // printValidShots(): 1
    @Test
    public void printValidShots() {
        game.fire(new TestPos(0, 1));
        assertDoesNotThrow(() -> game.printValidShots(), "Error: printValidShots should not throw");
    }

    // printFleet(): 2 tests
    @Test
    public void printFleet1() {
        // empty fleet
        fleet.getShips().clear();
        assertDoesNotThrow(() -> game.printFleet(), "Error: printFleet should not throw for empty fleet");
    }

    @Test
    public void printFleet2() {
        // fleet with one ship that has positions
        TestShip s = new TestShip("Frigate", 0, 0, 0, 0, true, false, false, 1);
        TestPos p = new TestPos(1, 1);
        s.setOccupiesPos(p);
        fleet.getShips().clear();
        fleet.getShips().add(s);
        assertDoesNotThrow(() -> game.printFleet(), "Error: printFleet should not throw for fleet with ship(s)");
    }

    // --- Helper test classes used to control behavior deterministically ---

    private static class TestShip implements IShip {
        private final String category;
        private final int left, right, top, bottom;
        private final boolean floating;
        private final boolean tooCloseFlag;
        private IPosition occupiesPos;
        private boolean sinkOnFirstShot;
        private boolean sunk = false;
        private int size;

        TestShip(String category, int left, int right, int top, int bottom, boolean floating, boolean tooCloseFlag, boolean sinkOnFirstShot, int size) {
            this.category = category;
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
            this.floating = floating;
            this.tooCloseFlag = tooCloseFlag;
            this.sinkOnFirstShot = sinkOnFirstShot;
            this.size = size;
        }

        void setOccupiesPos(IPosition p) {
            this.occupiesPos = p;
            if (p != null) p.occupy();
        }

        @Override
        public String getCategory() {
            return category;
        }

        @Override
        public Integer getSize() {
            return Integer.valueOf(Math.max(1, size));
        }

        @Override
        public java.util.List<IPosition> getPositions() {
            if (occupiesPos == null) return java.util.Collections.emptyList();
            return java.util.Collections.singletonList(occupiesPos);
        }

        @Override
        public IPosition getPosition() {
            return occupiesPos;
        }

        @Override
        public Compass getBearing() {
            return null;
        }

        @Override
        public boolean stillFloating() {
            return !sunk && floating;
        }

        @Override
        public int getTopMostPos() {
            return top;
        }

        @Override
        public int getBottomMostPos() {
            return bottom;
        }

        @Override
        public int getLeftMostPos() {
            return left;
        }

        @Override
        public int getRightMostPos() {
            return right;
        }

        @Override
        public boolean occupies(IPosition pos) {
            return occupiesPos != null && occupiesPos.equals(pos);
        }

        @Override
        public boolean tooCloseTo(IShip other) {
            return this.tooCloseFlag;
        }

        @Override
        public boolean tooCloseTo(IPosition pos) {
            if (this.tooCloseFlag) return true;
            if (occupiesPos == null || pos == null) return false;
            return occupiesPos.isAdjacentTo(pos) || occupiesPos.equals(pos);
        }

        @Override
        public void shoot(IPosition pos) {
            if (occupiesPos != null && occupiesPos.equals(pos)) {
                occupiesPos.shoot();
                if (sinkOnFirstShot || size <= 1) {
                    sunk = true;
                    size = 0;
                } else {
                    size = Math.max(0, size - 1);
                    if (size == 0) sunk = true;
                }
            }
        }

        @Override
        public String toString() {
            return "TestShip[" + category + "]";
        }
    }

    private static class TestPos implements IPosition {
        private final int x;
        private final int y;
        private boolean occupied = false;
        private boolean hit = false;

        TestPos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int getRow() {
            return x;
        }

        @Override
        public int getColumn() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestPos)) return false;
            TestPos p = (TestPos) o;
            return p.x == x && p.y == y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }

        @Override
        public boolean isAdjacentTo(IPosition other) {
            if (other == null) return false;
            int dr = Math.abs(this.getRow() - other.getRow());
            int dc = Math.abs(this.getColumn() - other.getColumn());
            if (dr == 0 && dc == 0) return false;
            return dr <= 1 && dc <= 1;
        }

        @Override
        public void occupy() {
            this.occupied = true;
        }

        @Override
        public void shoot() {
            if (this.occupied) this.hit = true;
        }

        @Override
        public boolean isOccupied() {
            return occupied;
        }

        @Override
        public boolean isHit() {
            return hit;
        }
    }
}

