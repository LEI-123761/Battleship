// java
package iscteiul.ista.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Galleon.
 * Author: LEI-122673
 * Date: 2025-11-21 12:00
 */
public class GalleonTest {

    private Galleon galleon;

    @BeforeEach
    public void setUp() {
        galleon = new Galleon(Compass.NORTH, new Position(2, 3));
    }

    @AfterEach
    public void tearDown() {
        galleon = null;
    }

    @Test
    public void constructor1() {
        Galleon g = new Galleon(Compass.NORTH, new Position(2, 3));
        assertAll("NORTH fill",
                () -> assertEquals(Integer.valueOf(5), g.getSize(), "Error: expected size 5 but got " + g.getSize()),
                () -> assertTrue(g.getPositions().contains(new Position(2, 3)), "Error: expected position (2,3) present"),
                () -> assertTrue(g.getPositions().contains(new Position(2, 4)), "Error: expected position (2,4) present"),
                () -> assertTrue(g.getPositions().contains(new Position(2, 5)), "Error: expected position (2,5) present"),
                () -> assertTrue(g.getPositions().contains(new Position(3, 4)), "Error: expected position (3,4) present"),
                () -> assertTrue(g.getPositions().contains(new Position(4, 4)), "Error: expected position (4,4) present")
        );
    }

    @Test
    public void constructor2() {
        Galleon g = new Galleon(Compass.EAST, new Position(4, 5));
        assertAll("EAST fill",
                () -> assertEquals(Integer.valueOf(5), g.getSize(), "Error: expected size 5 but got " + g.getSize()),
                () -> assertTrue(g.getPositions().contains(new Position(4, 5)), "Error: expected position (4,5) present"),
                () -> assertTrue(g.getPositions().contains(new Position(5, 3)), "Error: expected position (5,3) present"),
                () -> assertTrue(g.getPositions().contains(new Position(5, 4)), "Error: expected position (5,4) present"),
                () -> assertTrue(g.getPositions().contains(new Position(5, 5)), "Error: expected position (5,5) present"),
                () -> assertTrue(g.getPositions().contains(new Position(6, 5)), "Error: expected position (6,5) present")
        );
    }

    @Test
    public void constructor3() {
        Galleon g = new Galleon(Compass.SOUTH, new Position(1, 2));
        assertAll("SOUTH fill",
                () -> assertEquals(Integer.valueOf(5), g.getSize(), "Error: expected size 5 but got " + g.getSize()),
                () -> assertTrue(g.getPositions().contains(new Position(1, 2)), "Error: expected position (1,2) present"),
                () -> assertTrue(g.getPositions().contains(new Position(2, 2)), "Error: expected position (2,2) present"),
                () -> assertTrue(g.getPositions().contains(new Position(3, 1)), "Error: expected position (3,1) present"),
                () -> assertTrue(g.getPositions().contains(new Position(3, 2)), "Error: expected position (3,2) present"),
                () -> assertTrue(g.getPositions().contains(new Position(3, 3)), "Error: expected position (3,3) present")
        );
    }

    @Test
    public void constructor4() {
        Galleon g = new Galleon(Compass.WEST, new Position(0, 0));
        assertAll("WEST fill",
                () -> assertEquals(Integer.valueOf(5), g.getSize(), "Error: expected size 5 but got " + g.getSize()),
                () -> assertTrue(g.getPositions().contains(new Position(0, 0)), "Error: expected position (0,0) present"),
                () -> assertTrue(g.getPositions().contains(new Position(1, 0)), "Error: expected position (1,0) present"),
                () -> assertTrue(g.getPositions().contains(new Position(1, 1)), "Error: expected position (1,1) present"),
                () -> assertTrue(g.getPositions().contains(new Position(1, 2)), "Error: expected position (1,2) present"),
                () -> assertTrue(g.getPositions().contains(new Position(2, 0)), "Error: expected position (2,0) present")
        );
    }

    @Test
    public void constructor5() {
        // Production code currently uses assertions for null checks, which throw AssertionError when enabled.
        assertThrows(AssertionError.class,
                () -> new Galleon(null, new Position(0, 0)),
                "Error: expected AssertionError when passing null bearing (production uses assert checks)");
    }

    @Test
    public void constructor6() {
        assertThrows(IllegalArgumentException.class,
                () -> new Galleon(Compass.UNKNOWN, new Position(0, 0)),
                "Error: expected IllegalArgumentException for unsupported bearing");
    }

    @Test
    public void getSize() {
        assertEquals(Integer.valueOf(5), galleon.getSize(), "Error: expected getSize() to return 5 but got " + galleon.getSize());
    }
}

