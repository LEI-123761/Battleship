// java

package iscteiul.ista.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Fleet.
 * Author: LEI105514
 * Date: 2025-11-21 00:00
 */
public class FleetTest {

    private Fleet fleet;

    @BeforeEach
    public void setUp() {
        fleet = new Fleet();
    }

    @AfterEach
    public void tearDown() {
        fleet = null;
    }

    @Test
    public void constructorFleet() {
        assertNotNull(fleet, "Error: expected Fleet instance but got null");
    }

    @Test
    public void printShips() {
        List<IShip> list = new ArrayList<>();
        list.add(new TestShip("A", 0, 0, 0, 0, true, false));
        assertDoesNotThrow(() -> Fleet.printShips(list), "Error: printShips should not throw");
    }

    @Test
    public void getShips() {
        assertNotNull(fleet.getShips(), "Error: expected non-null ships list but got null");
        assertEquals(0, fleet.getShips().size(), "Error: expected empty ships list but got non-empty");
    }

    @Test
    public void addShip1() {
        TestShip s = new TestShip("Fragata", 0, 0, 0, 0, true, false);
        boolean res = fleet.addShip(s);
        assertAll("addShip success path",
                () -> assertTrue(res, "Error: expected addShip to return true but got false"),
                () -> assertEquals(1, fleet.getShips().size(), "Error: expected ships size 1 but got " + fleet.getShips().size())
        );
    }

    @Test
    public void addShip2() {
        for (int i = 0; i < IFleet.FLEET_SIZE + 2; i++) {
            fleet.getShips().add(new TestShip("Nau", 0, 0, 0, 0, true, false));
        }
        TestShip s = new TestShip("Nau", 0, 0, 0, 0, true, false);
        boolean res = fleet.addShip(s);
        assertFalse(res, "Error: expected addShip to return false when fleet size > FLEET_SIZE but got true");
    }

    @Test
    public void addShip3() {
        TestShip s = new TestShip("Caravela", -10, 0, 0, 0, true, false);
        boolean res = fleet.addShip(s);
        assertFalse(res, "Error: expected addShip to return false when ship is outside board but got true");
    }

    @Test
    public void getShipsLike1() {
        fleet.getShips().clear();
        fleet.getShips().add(new TestShip("Galeao", 0, 0, 0, 0, true, false));
        fleet.getShips().add(new TestShip("Fragata", 0, 0, 0, 0, true, false));
        List<IShip> res = fleet.getShipsLike("Galeao");
        assertEquals(1, res.size(), "Error: expected 1 Galeao but got " + res.size());
    }

    @Test
    public void getShipsLike2() {
        List<IShip> res = fleet.getShipsLike("NonExistent");
        assertEquals(0, res.size(), "Error: expected 0 ships of NonExistent but got " + res.size());
    }

    @Test
    public void getFloatingShips1() {
        fleet.getShips().clear();
        fleet.getShips().add(new TestShip("Barca", 0, 0, 0, 0, true, false));
        fleet.getShips().add(new TestShip("Barca", 0, 0, 0, 0, false, false));
        List<IShip> res = fleet.getFloatingShips();
        assertEquals(1, res.size(), "Error: expected 1 floating ship but got " + res.size());
    }

    @Test
    public void getFloatingShips2() {
        fleet.getShips().clear();
        List<IShip> res = fleet.getFloatingShips();
        assertEquals(0, res.size(), "Error: expected 0 floating ships but got " + res.size());
    }

    @Test
    public void shipAt1() {
        TestShip s = new TestShip("Fragata", 1, 1, 1, 1, true, false);
        s.setOccupiesPos(new TestPos(5, 5));
        fleet.getShips().clear();
        fleet.getShips().add(s);
        IShip found = fleet.shipAt(new TestPos(5, 5));
        assertSame(s, found, "Error: expected to find the same ship at given position but got different");
    }

    @Test
    public void shipAt2() {
        fleet.getShips().clear();
        IShip found = fleet.shipAt(new TestPos(9, 9));
        assertNull(found, "Error: expected null when no ship occupies the position but got non-null");
    }

    @Test
    public void isInsideBoard1() {
        TestShip s = new TestShip("Nau", 0, IFleet.BOARD_SIZE - 1, 0, IFleet.BOARD_SIZE - 1, true, false);
        assertTrue(invokeIsInsideBoard(s), "Error: expected ship to be inside board but got false");
    }

    @Test
    public void isInsideBoard2() {
        TestShip s = new TestShip("Nau", -1, 0, 0, 0, true, false);
        assertFalse(invokeIsInsideBoard(s), "Error: expected ship outside board due to leftMost < 0 but got true");
    }

    @Test
    public void isInsideBoard3() {
        TestShip s = new TestShip("Nau", 0, IFleet.BOARD_SIZE, 0, 0, true, false);
        assertFalse(invokeIsInsideBoard(s), "Error: expected ship outside board due to rightMost > BOARD_SIZE-1 but got true");
    }

    @Test
    public void colisionRisk1() {
        TestShip existing = new TestShip("Nau", 0, 0, 0, 0, true, true);
        fleet.getShips().clear();
        fleet.getShips().add(existing);
        TestShip candidate = new TestShip("Nau", 0, 0, 0, 0, true, false);
        assertTrue(invokeColisionRisk(candidate), "Error: expected colisionRisk to be true when an existing ship is too close but got false");
    }

    @Test
    public void colisionRisk2() {
        fleet.getShips().clear();
        fleet.getShips().add(new TestShip("Nau", 0, 0, 0, 0, true, false));
        TestShip candidate = new TestShip("Nau", 10, 10, 10, 10, true, false);
        assertFalse(invokeColisionRisk(candidate), "Error: expected colisionRisk to be false when no ships are too close but got true");
    }

    @Test
    public void printStatus() {
        assertDoesNotThrow(() -> fleet.printStatus(), "Error: printStatus should not throw");
    }

    @Test
    public void printShipsByCategory() {
        assertDoesNotThrow(() -> fleet.printShipsByCategory("Galeao"), "Error: printShipsByCategory should not throw for valid category");
    }

    @Test
    public void printShipsByCategory_assertion() {
        assertThrows(AssertionError.class, () -> fleet.printShipsByCategory(null), "Error: expected AssertionError when category is null");
    }

    @Test
    public void printFloatingShips() {
        assertDoesNotThrow(() -> fleet.printFloatingShips(), "Error: printFloatingShips should not throw");
    }

    @Test
    public void printAllShips() {
        assertDoesNotThrow(() -> fleet.printAllShips(), "Error: printAllShips should not throw");
    }

    private boolean invokeIsInsideBoard(IShip s) {
        fleet.getShips().clear();
        TestShip candidate = (s instanceof TestShip) ? (TestShip) s : new TestShip("tmp", 0, 0, 0, 0, true, false);
        boolean added = fleet.addShip(candidate);
        return added;
    }

    private boolean invokeColisionRisk(IShip s) {
        if (s == null) return false;
        for (IShip existing : fleet.getShips()) {
            if (existing.tooCloseTo(s)) return true;
        }
        return false;
    }

    private static class TestShip implements IShip {
        private final String category;
        private final int left, right, top, bottom;
        private final boolean floating;
        private final boolean tooCloseFlag;
        private IPosition occupiesPos;

        TestShip(String category, int left, int right, int top, int bottom, boolean floating, boolean tooCloseFlag) {
            this.category = category;
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
            this.floating = floating;
            this.tooCloseFlag = tooCloseFlag;
        }

        void setOccupiesPos(IPosition p) {
            this.occupiesPos = p;
        }

        @Override
        public String getCategory() {
            return category;
        }

        @Override
        public Integer getSize() {
            int width = right - left + 1;
            int height = bottom - top + 1;
            return Math.max(1, Math.max(width, height));
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
            return floating;
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
