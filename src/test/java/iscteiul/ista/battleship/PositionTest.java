package iscteiul.ista.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Position.
 * Author: LEI-122673
 * Date: 2025-11-21 12:00
 * Cyclomatic Complexity:
 * - constructor: 1
 * - getRow(): 1
 * - getColumn(): 1
 * - hashCode(): 1
 * - equals(Object): 4
 * - isAdjacentTo(IPosition): 4
 * - occupy(): 1
 * - shoot(): 1
 * - isOccupied(): 1
 * - isHit(): 1
 * - toString(): 1
 */
public class PositionTest {

    private Position pos;

    @BeforeEach
    public void setUp() {
        pos = new Position(0, 0);
    }

    @AfterEach
    public void tearDown() {
        pos = null;
    }

    // constructor() -> 1
    @Test
    public void constructor() {
        assertAll("constructor checks",
                () -> assertEquals(0, pos.getRow(), "Error: expected row 0 but got " + pos.getRow()),
                () -> assertEquals(0, pos.getColumn(), "Error: expected column 0 but got " + pos.getColumn()),
                () -> assertFalse(pos.isOccupied(), "Error: expected isOccupied() false but got true"),
                () -> assertFalse(pos.isHit(), "Error: expected isHit() false but got true"),
                () -> assertTrue(pos.toString().contains("Linha = 0") && pos.toString().contains("Coluna = 0"),
                        "Error: expected toString() to contain coordinates but was: " + pos.toString())
        );
    }

    // getRow() -> 1
    @Test
    public void getRow() {
        assertEquals(0, pos.getRow(), "Error: expected getRow() to return 0 but got " + pos.getRow());
    }

    // getColumn() -> 1
    @Test
    public void getColumn() {
        assertEquals(0, pos.getColumn(), "Error: expected getColumn() to return 0 but got " + pos.getColumn());
    }

    // hashCode() -> 1
    @Test
    public void hashCodeTest() {
        Position same = new Position(0, 0);
        // both positions have same row/column and default booleans -> hashCodes should match
        assertEquals(pos.hashCode(), same.hashCode(),
                "Error: expected equal positions to have same hashCode but got " + pos.hashCode() + " and " + same.hashCode());
    }

    // equals(Object) -> 4 tests
    @Test
    public void equals1() {
        // same object -> true
        assertTrue(pos.equals(pos), "Error: expected equals(this) true but got false");
    }

    @Test
    public void equals2() {
        // non-IPosition object -> false
        Object other = new Object();
        assertFalse(pos.equals(other), "Error: expected equals(non-IPosition) false but got true");
    }

    @Test
    public void equals3() {
        // different IPosition with same coordinates -> true
        Position sameCoords = new Position(0, 0);
        assertTrue(pos.equals(sameCoords), "Error: expected equals(Position with same coords) true but got false");
    }

    @Test
    public void equals4() {
        // different IPosition with different coordinates -> false
        Position diff = new Position(1, 0);
        assertFalse(pos.equals(diff), "Error: expected equals(Position with different coords) false but got true");
    }

    // isAdjacentTo(IPosition) -> 4 tests (both axes within 1 -> true; row within1 col not -> false; row not col within1 -> false; null -> NPE)
    @Test
    public void isAdjacentTo1() {
        // both row and column differences <= 1 -> true
        Position other = new Position(1, 1);
        assertTrue(pos.isAdjacentTo(other), "Error: expected isAdjacentTo((1,1)) true but got false");
    }

    @Test
    public void isAdjacentTo2() {
        // row difference <=1, column difference >1 -> false
        Position other = new Position(1, 3);
        assertFalse(pos.isAdjacentTo(other), "Error: expected isAdjacentTo((1,3)) false but got true");
    }

    @Test
    public void isAdjacentTo3() {
        // row difference >1, column difference <=1 -> false
        Position other = new Position(3, 1);
        assertFalse(pos.isAdjacentTo(other), "Error: expected isAdjacentTo((3,1)) false but got true");
    }

    @Test
    public void isAdjacentTo4() {
        // null input -> expect NullPointerException
        assertThrows(NullPointerException.class, () -> pos.isAdjacentTo(null),
                "Error: expected NullPointerException when calling isAdjacentTo(null)");
    }

    // occupy() -> 1
    @Test
    public void occupy() {
        assertFalse(pos.isOccupied(), "Error: expected isOccupied() false before occupy() but got true");
        pos.occupy();
        assertTrue(pos.isOccupied(), "Error: expected isOccupied() true after occupy() but got false");
    }

    // shoot() -> 1
    @Test
    public void shoot() {
        assertFalse(pos.isHit(), "Error: expected isHit() false before shoot() but got true");
        pos.shoot();
        assertTrue(pos.isHit(), "Error: expected isHit() true after shoot() but got false");
    }

    // isOccupied() -> 1
    @Test
    public void isOccupied() {
        assertFalse(pos.isOccupied(), "Error: expected isOccupied() false for fresh position but got true");
        pos.occupy();
        assertTrue(pos.isOccupied(), "Error: expected isOccupied() true after occupy() but got false");
    }

    // isHit() -> 1
    @Test
    public void isHit() {
        assertFalse(pos.isHit(), "Error: expected isHit() false for fresh position but got true");
        pos.shoot();
        assertTrue(pos.isHit(), "Error: expected isHit() true after shoot() but got false");
    }

    // toString() -> 1
    @Test
    public void toStringTest() {
        String s = pos.toString();
        assertTrue(s.contains("Linha = 0") && s.contains("Coluna = 0"),
                "Error: expected toString() to contain 'Linha = 0' and 'Coluna = 0' but was: " + s);
    }
}
