package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BargeTest {

    @Test
    @DisplayName("Barge.getSize() deve devolver 1")
    void getSize() {
        Barge barge = new Barge(Compass.NORTH, new Position(2, 3));
        assertEquals(Integer.valueOf(1), barge.getSize());
    }
}