package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GalleonTest {

    @Test
    @DisplayName("Galleon.getSize() deve devolver 5")
    void getSize() {
        Galleon galleon = new Galleon(Compass.NORTH, new Position(2, 3));
        assertEquals(Integer.valueOf(5), galleon.getSize());
    }
}