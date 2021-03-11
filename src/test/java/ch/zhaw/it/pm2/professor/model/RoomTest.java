package ch.zhaw.it.pm2.professor.model;

import org.junit.jupiter.api.Test;

import static ch.zhaw.it.pm2.professor.model.HouseTest.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * RoomTest.
 * Test-methods are described here.
 */
class RoomTest {

    private final static String[] TEST_ARRAY = {"this", "is", "a", "test", "array", "lorem", "ipsum", "tralala"};
    private final static String[] EMPTY_ARRAY = {};

    /**
     * Test addToHouse:
     * Add all rooms the house and assert the given String[].
     */
    @Test
    void testAddToHouseNoHallway() {
        Room.ROOM_UP.addToHouse(EMPTY_HOUSE);
        Room.ROOM_DOWN.addToHouse(EMPTY_HOUSE);
        Room.ROOM_RIGHT.addToHouse(EMPTY_HOUSE);
        Room.ROOM_LEFT.addToHouse(EMPTY_HOUSE);
        String[] actualFullHouse = EMPTY_HOUSE;
        assertArrayEquals(EXPECTED_FULL_HOUSE_NO_USERDATA, actualFullHouse);
    }

    /**
     * Test addToHouse:
     * If passed a null-object, a NullPointerException should get thrown.
     */
    @Test
    void testAddToNull() throws NullPointerException {
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> Room.ROOM_UP.addToHouse(null));
    }

    /**
     * Test addToHouse:
     * If a random String-Array which doesn't fit the requirements for a room gets passed to the
     * method addToHouse, an StringIndexOutOfBoundsException should get thrown.
     * @throws StringIndexOutOfBoundsException
     */
    @Test
    void testAddTestArrayToHouse() throws StringIndexOutOfBoundsException {
        StringIndexOutOfBoundsException thrown = assertThrows(
                StringIndexOutOfBoundsException.class,
                () -> Room.ROOM_UP.addToHouse(TEST_ARRAY));
    }

    /**
     * Test addToHouse:
     * If a empty String-Array which doesn't fit the requirements for a room gets passed to the
     * method addToHouse, an ArrayIndexOutOfBoundsException should get thrown.
     * @throws ArrayIndexOutOfBoundsException
     */
    @Test
    void testAddEmptyArrayToHouse() throws ArrayIndexOutOfBoundsException {
        ArrayIndexOutOfBoundsException thrown = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> Room.ROOM_UP.addToHouse(EMPTY_ARRAY));
    }
}