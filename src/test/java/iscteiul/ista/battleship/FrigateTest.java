package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FrigateTest {

    @Test
    @DisplayName("Frigate.getSize() deve devolver 4")
    void getSize() {
        Frigate frigate = new Frigate(Compass.NORTH, new Position(2, 3));
        assertEquals(Integer.valueOf(4), frigate.getSize());
    }
}