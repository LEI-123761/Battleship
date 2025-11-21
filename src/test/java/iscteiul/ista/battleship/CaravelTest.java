package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaravelTest {

    @Test
    @DisplayName("Caravel.getSize() deve devolver e")
    void getSize() {
        Caravel caravel = new Caravel(Compass.NORTH, new Position(2, 3));
        assertEquals(Integer.valueOf(2), caravel.getSize());
    }
}