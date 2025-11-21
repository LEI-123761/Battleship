// java
package iscteiul.ista.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Compass.
 * Author: LEI-122673
 * Date: 2025-11-21 12:00
 * Cyclomatic Complexity:
 * - constructor: 1
 * - getDirection(): 1
 * - toString(): 1
 * - charToCompass(char): 5
 */
public class CompassTest {

    private Compass bearing;

    @BeforeEach
    public void setUp() {
        bearing = Compass.NORTH;
    }

    @AfterEach
    public void tearDown() {
        bearing = null;
    }

    // constructor() -> 1
    @Test
    public void constructor() {
        assertAll("constructor checks",
                () -> assertNotNull(bearing, "Error: expected Compass.NORTH not null but was null"),
                () -> assertEquals('n', bearing.getDirection(), "Error: expected NORTH.getDirection() to be 'n' but was " + bearing.getDirection()),
                () -> assertEquals("n", bearing.toString(), "Error: expected NORTH.toString() to be \"n\" but was: " + bearing.toString())
        );
    }

    // getDirection() -> 1
    @Test
    public void getDirection() {
        // verify a different enum constant to show method behavior
        char westDir = Compass.WEST.getDirection();
        assertEquals('o', westDir, "Error: expected WEST.getDirection() to be 'o' but was " + westDir);
    }

    // toString() -> 1
    @Test
    public void toStringTest() {
        String s = Compass.EAST.toString();
        assertEquals("e", s, "Error: expected EAST.toString() to be \"e\" but was: " + s);
    }

    // charToCompass(char) -> 5 tests (cases 'n','s','e','o' and default)
    @Test
    public void charToCompass1() {
        Compass c = Compass.charToCompass('n');
        assertEquals(Compass.NORTH, c, "Error: expected charToCompass('n') to return NORTH but got " + c);
    }

    @Test
    public void charToCompass2() {
        Compass c = Compass.charToCompass('s');
        assertEquals(Compass.SOUTH, c, "Error: expected charToCompass('s') to return SOUTH but got " + c);
    }

    @Test
    public void charToCompass3() {
        Compass c = Compass.charToCompass('e');
        assertEquals(Compass.EAST, c, "Error: expected charToCompass('e') to return EAST but got " + c);
    }

    @Test
    public void charToCompass4() {
        Compass c = Compass.charToCompass('o');
        assertEquals(Compass.WEST, c, "Error: expected charToCompass('o') to return WEST but got " + c);
    }

    @Test
    public void charToCompass5() {
        // default path for any other character
        Compass c = Compass.charToCompass('x');
        assertEquals(Compass.UNKNOWN, c, "Error: expected charToCompass('x') to return UNKNOWN but got " + c);
    }
}
