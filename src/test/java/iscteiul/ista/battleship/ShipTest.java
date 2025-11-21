// java
package iscteiul.ista.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Ship.
 * Author: LEI-122673
 * Date: 2025-11-21 12:00
 * Cyclomatic Complexity:
 * - buildShip(): 6
 * - constructor: 1
 * - getCategory(): 1
 * - getPositions(): 1
 * - getPosition(): 1
 * - getBearing(): 1
 * - stillFloating(): 3
 * - getTopMostPos(): 3
 * - getBottomMostPos(): 3
 * - getLeftMostPos(): 3
 * - getRightMostPos(): 3
 * - occupies(): 3
 * - tooCloseTo(IShip): 3
 * - tooCloseTo(IPosition): 3
 * - shoot(): 3
 * - toString(): 1
 */
public class ShipTest {

    private TestShip ship;

    // Minimal concrete Ship subclass for controlled tests
    static class TestShip extends Ship {
        public TestShip(Compass bearing, IPosition pos) {
            super("test", bearing, pos);
            // ensure initial position present
            positions.clear();
            positions.add(pos);
        }

        @Override
        public Integer getSize() {
            return positions.size();
        }
    }

    @BeforeEach
    public void setUp() {
        ship = new TestShip(Compass.NORTH, new Position(0, 0));
    }

    @AfterEach
    public void tearDown() {
        ship = null;
    }

    // buildShip() -> 6 tests (cases + default)
    @Test
    public void buildShip1() {
        Ship s = Ship.buildShip("barca", Compass.EAST, new Position(0, 0));
        assertNotNull(s, "Error: expected a Barge instance but got null");
    }

    @Test
    public void buildShip2() {
        Ship s = Ship.buildShip("caravela", Compass.EAST, new Position(0, 0));
        assertNotNull(s, "Error: expected a Caravel instance but got null");
    }

    @Test
    public void buildShip3() {
        Ship s = Ship.buildShip("nau", Compass.EAST, new Position(0, 0));
        assertNotNull(s, "Error: expected a Carrack instance but got null");
    }

    @Test
    public void buildShip4() {
        Ship s = Ship.buildShip("fragata", Compass.EAST, new Position(0, 0));
        assertNotNull(s, "Error: expected a Frigate instance but got null");
    }

    @Test
    public void buildShip5() {
        Ship s = Ship.buildShip("galeao", Compass.EAST, new Position(0, 0));
        assertNotNull(s, "Error: expected a Galleon instance but got null");
    }

    @Test
    public void buildShip6() {
        Ship s = Ship.buildShip("unknown-kind", Compass.EAST, new Position(0, 0));
        assertNull(s, "Error: expected null for unknown ship kind but got a Ship instance");
    }

    // constructor (1)
    @Test
    public void constructor() {
        assertAll("constructor checks",
                () -> assertEquals("test", ship.getCategory(), "Error: expected category 'test' but got " + ship.getCategory()),
                () -> assertEquals(Compass.NORTH, ship.getBearing(), "Error: expected bearing NORTH but got " + ship.getBearing()),
                () -> assertEquals(0, ship.getPosition().getRow(), "Error: expected position row 0 but got " + ship.getPosition().getRow())
        );
    }

    // getCategory() (1)
    @Test
    public void getCategory() {
        assertEquals("test", ship.getCategory(), "Error: expected category 'test' but got " + ship.getCategory());
    }

    // getPositions() (1)
    @Test
    public void getPositions() {
        assertNotNull(ship.getPositions(), "Error: expected non-null positions list but got null");
        assertEquals(1, ship.getPositions().size(), "Error: expected positions size 1 but got " + ship.getPositions().size());
    }

    // getPosition() (1)
    @Test
    public void getPosition() {
        assertEquals(0, ship.getPosition().getRow(), "Error: expected position row 0 but got " + ship.getPosition().getRow());
    }

    // getBearing() (1)
    @Test
    public void getBearing() {
        assertEquals(Compass.NORTH, ship.getBearing(), "Error: expected bearing NORTH but got " + ship.getBearing());
    }

    // stillFloating() -> 3 tests (true early, false all hit, false size==0)
    @Test
    public void stillFloating1() {
        // one position not hit -> should return true
        ship.positions.clear();
        ship.positions.add(new Position(2, 2)); // not shot
        ship.positions.add(new Position(2, 3)); // not shot
        ship.positions.get(1).shoot(); // make second hit so first remains not hit
        assertTrue(ship.stillFloating(), "Error: expected stillFloating() true because at least one position is not hit");
    }

    @Test
    public void stillFloating2() {
        // all positions hit -> should return false
        ship.positions.clear();
        ship.positions.add(new Position(1, 1));
        ship.positions.add(new Position(1, 2));
        ship.positions.forEach(IPosition::shoot);
        assertFalse(ship.stillFloating(), "Error: expected stillFloating() false because all positions are hit");
    }

    @Test
    public void stillFloating3() {
        // zero size -> loop not entered -> should return false
        ship.positions.clear();
        assertEquals(0, ship.getSize(), "Error: expected size 0 but got " + ship.getSize());
        assertFalse(ship.stillFloating(), "Error: expected stillFloating() false for a ship with no positions");
    }

    // getTopMostPos() -> 3 tests
    @Test
    public void getTopMostPos1() {
        ship.positions.clear();
        ship.positions.add(new Position(5, 0));
        assertEquals(5, ship.getTopMostPos(), "Error: expected top-most row 5 but got " + ship.getTopMostPos());
    }

    @Test
    public void getTopMostPos2() {
        ship.positions.clear();
        ship.positions.add(new Position(1, 1));
        ship.positions.add(new Position(3, 1)); // first is top
        assertEquals(1, ship.getTopMostPos(), "Error: expected top-most row 1 but got " + ship.getTopMostPos());
    }

    @Test
    public void getTopMostPos3() {
        ship.positions.clear();
        ship.positions.add(new Position(4, 1));
        ship.positions.add(new Position(2, 1)); // second is top (smaller)
        assertEquals(2, ship.getTopMostPos(), "Error: expected top-most row 2 but got " + ship.getTopMostPos());
    }

    // getBottomMostPos() -> 3 tests
    @Test
    public void getBottomMostPos1() {
        ship.positions.clear();
        ship.positions.add(new Position(7, 0));
        assertEquals(7, ship.getBottomMostPos(), "Error: expected bottom-most row 7 but got " + ship.getBottomMostPos());
    }

    @Test
    public void getBottomMostPos2() {
        ship.positions.clear();
        ship.positions.add(new Position(1, 1));
        ship.positions.add(new Position(0, 1)); // second is bottom? actually bigger number -> ensure first is bottom
        ship.positions.add(new Position(2, 1));
        assertEquals(2, ship.getBottomMostPos(), "Error: expected bottom-most row 2 but got " + ship.getBottomMostPos());
    }

    @Test
    public void getBottomMostPos3() {
        ship.positions.clear();
        ship.positions.add(new Position(3, 1));
        ship.positions.add(new Position(6, 1)); // larger -> bottom should be 6
        assertEquals(6, ship.getBottomMostPos(), "Error: expected bottom-most row 6 but got " + ship.getBottomMostPos());
    }

    // getLeftMostPos() -> 3 tests
    @Test
    public void getLeftMostPos1() {
        ship.positions.clear();
        ship.positions.add(new Position(0, 4));
        assertEquals(4, ship.getLeftMostPos(), "Error: expected left-most column 4 but got " + ship.getLeftMostPos());
    }

    @Test
    public void getLeftMostPos2() {
        ship.positions.clear();
        ship.positions.add(new Position(0, 3));
        ship.positions.add(new Position(0, 5)); // first is left
        assertEquals(3, ship.getLeftMostPos(), "Error: expected left-most column 3 but got " + ship.getLeftMostPos());
    }

    @Test
    public void getLeftMostPos3() {
        ship.positions.clear();
        ship.positions.add(new Position(0, 8));
        ship.positions.add(new Position(0, 2)); // smaller -> left should be 2
        assertEquals(2, ship.getLeftMostPos(), "Error: expected left-most column 2 but got " + ship.getLeftMostPos());
    }

    // getRightMostPos() -> 3 tests
    @Test
    public void getRightMostPos1() {
        ship.positions.clear();
        ship.positions.add(new Position(0, 2));
        assertEquals(2, ship.getRightMostPos(), "Error: expected right-most column 2 but got " + ship.getRightMostPos());
    }

    @Test
    public void getRightMostPos2() {
        ship.positions.clear();
        ship.positions.add(new Position(0, 1));
        ship.positions.add(new Position(0, 1)); // same -> right remains 1
        assertEquals(1, ship.getRightMostPos(), "Error: expected right-most column 1 but got " + ship.getRightMostPos());
    }

    @Test
    public void getRightMostPos3() {
        ship.positions.clear();
        ship.positions.add(new Position(0, 3));
        ship.positions.add(new Position(0, 9)); // larger -> right should be 9
        assertEquals(9, ship.getRightMostPos(), "Error: expected right-most column 9 but got " + ship.getRightMostPos());
    }

    // occupies() -> 3 tests (true, false, null -> AssertionError)
    @Test
    public void occupies1() {
        ship.positions.clear();
        Position p = new Position(2, 2);
        ship.positions.add(p);
        assertTrue(ship.occupies(p), "Error: expected occupies() true for the same position but got false");
    }

    @Test
    public void occupies2() {
        ship.positions.clear();
        ship.positions.add(new Position(1, 1));
        assertFalse(ship.occupies(new Position(9, 9)), "Error: expected occupies() false for a different position but got true");
    }

    @Test
    public void occupies3() {
        // assertion inside method should raise AssertionError when null is passed (requires -ea at runtime)
        assertThrows(AssertionError.class, () -> ship.occupies(null), "Error: expected AssertionError when calling occupies(null)");
    }

    // tooCloseTo(IShip) -> 3 tests (true found, false none, null assertion)
    @Test
    public void tooCloseTo1() {
        ship.positions.clear();
        ship.positions.add(new Position(2, 2));
        TestShip other = new TestShip(Compass.NORTH, new Position(3, 3)); // adjacent to (2,2)
        other.positions.clear();
        other.positions.add(new Position(3, 3));
        assertTrue(ship.tooCloseTo(other), "Error: expected tooCloseTo(IShip) true for adjacent positions but got false");
    }

    @Test
    public void tooCloseTo2() {
        ship.positions.clear();
        ship.positions.add(new Position(0, 0));
        TestShip other = new TestShip(Compass.NORTH, new Position(5, 5));
        other.positions.clear();
        other.positions.add(new Position(9, 9));
        assertFalse(ship.tooCloseTo(other), "Error: expected tooCloseTo(IShip) false when no positions are adjacent but got true");
    }

    @Test
    public void tooCloseTo3() {
        assertThrows(AssertionError.class, () -> ship.tooCloseTo((IShip) null), "Error: expected AssertionError when calling tooCloseTo(null)");
    }

    // tooCloseTo(IPosition) -> 3 tests (adjacent true, none false, empty positions false)
    @Test
    public void tooCloseToPos1() {
        ship.positions.clear();
        ship.positions.add(new Position(4, 4));
        IPosition p = new Position(5, 5); // adjacent diagonally
        assertTrue(ship.tooCloseTo(p), "Error: expected tooCloseTo(IPosition) true for adjacent positions but got false");
    }

    @Test
    public void tooCloseToPos2() {
        ship.positions.clear();
        ship.positions.add(new Position(0, 0));
        IPosition p = new Position(9, 9);
        assertFalse(ship.tooCloseTo(p), "Error: expected tooCloseTo(IPosition) false for distant positions but got true");
    }

    @Test
    public void tooCloseToPos3() {
        ship.positions.clear(); // empty positions list -> no adjacency found
        IPosition p = new Position(0, 0);
        assertFalse(ship.tooCloseTo(p), "Error: expected tooCloseTo(IPosition) false for ship with no positions but got true");
    }

    // shoot(IPosition) -> 3 tests (hit, miss, null -> AssertionError)
    @Test
    public void shoot1() {
        ship.positions.clear();
        Position target = new Position(2, 2);
        ship.positions.add(target);
        assertFalse(target.isHit(), "Error: expected target initially not hit but it is hit");
        ship.shoot(new Position(2, 2)); // Position.equals likely compares row/col
        assertTrue(target.isHit(), "Error: expected target to be hit after shoot() but it was not");
    }

    @Test
    public void shoot2() {
        ship.positions.clear();
        ship.positions.add(new Position(1, 1));
        Position notPresent = new Position(9, 9);
        ship.shoot(notPresent);
        assertFalse(ship.positions.get(0).isHit(), "Error: expected existing position not to be hit when shooting a different position");
    }

    @Test
    public void shoot3() {
        assertThrows(AssertionError.class, () -> ship.shoot(null), "Error: expected AssertionError when calling shoot(null)");
    }

    // toString() (1)
    @Test
    public void toStringTest() {
        String s = ship.toString();
        assertAll(
                () -> assertTrue(s.contains("test"), "Error: expected toString() to contain category 'test' but it was: " + s),
                () -> assertTrue(
                        s.toLowerCase().contains("north") || s.contains(" n ") || s.contains(" N "),
                        "Error: expected toString() to contain bearing (e.g. NORTH or single-letter N/n) but it was: " + s
                )
        );
    }
}
