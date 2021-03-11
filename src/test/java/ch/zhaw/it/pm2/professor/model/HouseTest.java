package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.exception.HouseIOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * HouseTest.
 * Test-methods are described here.
 */
class HouseTest {

    private House house;
    private static final String USERNAME = "Cellestine";

    static final String[] EMPTY_HOUSE =
            {"###############################################################", "# Username:%USER________%         Highscore:%HIGHSCORE%       #", "###############################################################", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "###############################################################", "# Level:%LEVEL% | Time:%TIME%             Score:%S%/  %T%     #", "###############################################################"};
    static final String[] EXPECTED_FULL_HOUSE_NO_USERDATA =
            {"###############################################################", "# Username:%USER________%         Highscore:%HIGHSCORE%       #", "###############################################################", "#                      ################                       #", "#                      #              #                       #", "#                      #     -        #                       #", "#                      #              #                       #", "#                      ################                       #", "#    ################                    ################     #", "#    #              #                    #              #     #", "#    #     +        #                    #     *        #     #", "#    #              #                    #              #     #", "#    ################                    ################     #", "#                      ################                       #", "#                      #              #                       #", "#                      #     /        #                       #", "#                      #              #                       #", "#                      ################                       #", "###############################################################", "# Level:%LEVEL% | Time:%TIME%             Score:%S%/  %T%     #", "###############################################################"};
    static final String[] HOUSE_WITH_ROOMS_AND_USERDATA =
            {"###############################################################", "# Username:Cellestine             Highscore:%HIGHSCORE%       #", "###############################################################", "#                      ################                       #", "#                      #              #                       #", "#                      #     -        #                       #", "#                      #              #                       #", "#                      ################                       #", "#    ################  ################  ################     #", "#    #              #  #              #  #              #     #", "#    #     +        #  #     --       #  #     *        #     #", "#    #              #  #              #  #              #     #", "#    ################  ################  ################     #", "#                      ################                       #", "#                      #              #                       #", "#                      #     /        #                       #", "#                      #              #                       #", "#                      ################                       #", "###############################################################", "# Level:%LEVEL% | Time:2                  Score:1000/  1000     #", "###############################################################"};
    static final String[] ENTRANCE =
            {"###############################################################", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#  ,   .     .                     .                          #", "#  | . |     |                     |                          #", "#  | ) ) ,-. | ,-. ,-. ;-.-. ,-.   |-  ,-.                    #", "#  |/|/  |-' | |   | | | | | |-'   |   | |                    #", "#  ' '   `-' ' `-' `-' ' ' ' `-'   `-' `-'                    #", "#                                                             #", "#  .   .   .   .                                           .  #", "#  | o |   |   |                    ,-                     |  #", "#  | . |-  |-  | ,-.   ;-. ;-. ,-.  |  ,-. ,-. ,-. ,-. ;-. |  #", "#  | | |   |   | |-'   | | |   | |  |- |-' `-. `-. | | |      #", "#  ' ' `-' `-' ' `-'   |-' '   `-'  |  `-' `-' `-' `-' '   o  #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "###############################################################"};
    @Mock
    private Level levelMock;


    @BeforeEach
    void setUp() throws IOException, HouseIOException {
        MockitoAnnotations.initMocks(this);
        when(levelMock.getRooms()).thenReturn(Room.values());

        house = new House(() -> 2);
        assertNotNull(house);
    }

    /**
     * Test changeState:
     * Change state to Hallway, assert that the new State is Hallway.
     *
     * @throws IOException
     * @throws HouseIOException
     */
    @Test
    void testChangeStateToHallway() throws HouseIOException, IOException {
        house.changeState(House.State.HALLWAY);
        assertEquals(House.State.HALLWAY, house.getState());
    }

    /**
     * Test changeState:
     * Change state to Entrance, then to Hallway and again to Entrance,
     * assert that the new State is Entrance.
     * @throws IOException
     * @throws HouseIOException
     */
    @Test
    void testChangeStateToHallwayAndBack() throws IOException, HouseIOException {
        house.changeState(House.State.ENTRANCE);
        house.changeState(House.State.HALLWAY);
        house.changeState(House.State.ENTRANCE);
        assertEquals(House.State.ENTRANCE, house.getState());
    }

    /**
     * Test changeState:
     * If passed a null-object, a NullPointerException should get thrown.
     * @throws NullPointerException
     */
    @Test
    void testNullState() throws NullPointerException {
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> house.changeState(null));

    }

    /**
     * Test printLevelAsArray:
     * Using a Mock to mock the Level class because printLevelAsArray requires a Level Object.
     * Changing to state Entrance and print the Level as Array.
     * Assert the actual Array with the given String[] ENTRANCE.
     * @throws IOException
     * @throws HouseIOException
     */
    @Test
    void testStateEntrancePrintAsArray() throws IOException, HouseIOException {
        house.changeState(House.State.ENTRANCE);
        String[] actualHouse = house.printLevelAsArray(levelMock);
        assertArrayEquals(ENTRANCE, actualHouse);
    }

    /**
     * Test printLevelAsArray:
     * If State is Hallway, the method should add rooms and time to the house.
     * setUsername and setScore also adds this user information.
     * Assert the actual Array with the given String[] HOUSE_WITH_ROOMS_AND_USERDATA.
     * @throws IOException
     * @throws HouseIOException
     */
    @Test
    void testSetUserDataAndAddRoomsInStateHallway() throws IOException, HouseIOException {
        house.changeState(House.State.HALLWAY);
        house.setUsername(USERNAME);
        house.setScore(1000);
        house.setTotalScore(1000);
        String[] actualHouse = house.printLevelAsArray(levelMock);

        assertArrayEquals(HOUSE_WITH_ROOMS_AND_USERDATA, actualHouse);
    }
}