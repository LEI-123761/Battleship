package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarrackTest {

    @Test
    @DisplayName("Carrack.getSize() deve devolver 3")
    void getSize() {
        Carrack carrack = new Carrack(Compass.NORTH, new Position(2, 3));
        assertEquals(Integer.valueOf(3), carrack.getSize());
    }
}