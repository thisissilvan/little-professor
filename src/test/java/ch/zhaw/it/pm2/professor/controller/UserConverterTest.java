package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.controller.converter.UserConverter;
import ch.zhaw.it.pm2.professor.exception.UserConversionException;
import ch.zhaw.it.pm2.professor.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
/**
 * UserConverterTest.
 * Test-methods are described here.
 */
class UserConverterTest {

    @Mock
    User firstUser;

    @Mock
    User secondUser;

    @Mock
    User thirdUser;

    @Mock
    User fourthUser;

    private static final String FIRST_USER_EXPECTED = "Cellestine+18";
    private static final String SECOND_USER_EXPECTED = "Timo+21";
    private static final String THIRD_USER_EXPECTED = "Dekyi+34";
    private static final String FOURTH_USER_EXPECTED = "Sydney+44";
    private static final String USER_TOO_MANY_ATTRIBUTES = "Cellestine+2+3";

    /**
     * Set Up with four User Mocks.
     * Using the static methods of the UserConverter-class for the tests.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(firstUser.getName()).thenReturn("Cellestine");
        when(firstUser.getScore()).thenReturn(2);
        when(firstUser.getHighscore()).thenReturn(18);

        when(secondUser.getName()).thenReturn("Timo");
        when(secondUser.getScore()).thenReturn(5);
        when(secondUser.getHighscore()).thenReturn(21);

        when(thirdUser.getName()).thenReturn("Dekyi");
        when(thirdUser.getScore()).thenReturn(7);
        when(thirdUser.getHighscore()).thenReturn(34);

        when(fourthUser.getName()).thenReturn("Sydney");
        when(fourthUser.getScore()).thenReturn(10);
        when(fourthUser.getHighscore()).thenReturn(44);
    }

    /**
     * Test toString:
     * Calling the toString-method with all four Mocks. Should pass and verify that the
     * methods to produce the String should be called from every Mock once.
     * @throws UserConversionException
     */
    @Test
    void testAllUserToString() throws UserConversionException {
        String actualStringOne = UserConverter.toString(firstUser);
        String actualStringTwo = UserConverter.toString(secondUser);
        String actualStringThree = UserConverter.toString(thirdUser);
        String actualStringFour = UserConverter.toString(fourthUser);

        assertEquals(FIRST_USER_EXPECTED, actualStringOne);
        verify(firstUser, times(1)).getName();
        verify(firstUser, times(1)).getHighscore();

        assertEquals(SECOND_USER_EXPECTED, actualStringTwo);
        verify(secondUser, times(1)).getName();
        verify(secondUser, times(1)).getHighscore();

        assertEquals(THIRD_USER_EXPECTED, actualStringThree);
        verify(thirdUser, times(1)).getName();
        verify(thirdUser, times(1)).getHighscore();

        assertEquals(FOURTH_USER_EXPECTED, actualStringFour);
        verify(fourthUser, times(1)).getName();
        verify(fourthUser, times(1)).getHighscore();
    }

    /**
     * Test toString:
     * Null Pointer
     * @throws UserConversionException
     */
    @Test
    void testNullUserToString() throws UserConversionException {
        UserConversionException thrown = assertThrows(
                UserConversionException.class,
                () -> UserConverter.toString(null));
    }

    /**
     * Test toObject:
     * Should pass with Mock
     * @throws UserConversionException
     */
    @Test
    void testToObjectAllUsers() throws UserConversionException {
        User actualUserOne = UserConverter.toObject(FIRST_USER_EXPECTED);
        User expectedUserOne = new User(firstUser.getName(),0,firstUser.getHighscore());

        User actualUserTwo = UserConverter.toObject(SECOND_USER_EXPECTED);
        User expectedUserTwo = new User(secondUser.getName(),0,secondUser.getHighscore());

        User actualUserThree = UserConverter.toObject(THIRD_USER_EXPECTED);
        User expectedUserThree = new User(thirdUser.getName(),0,thirdUser.getHighscore());

        User actualUserFour = UserConverter.toObject(FOURTH_USER_EXPECTED);
        User expectedUserFour = new User(fourthUser.getName(),0,fourthUser.getHighscore());

        assertEquals(expectedUserOne, actualUserOne);
        assertEquals(expectedUserTwo, actualUserTwo);
        assertEquals(expectedUserThree, actualUserThree);
        assertEquals(expectedUserFour, actualUserFour);
    }

    /**
     * Test toObject:
     * Null
     * @throws UserConversionException
     */
    @Test
    void testNullUserToObject() throws UserConversionException {
        UserConversionException thrown = assertThrows(
                UserConversionException.class,
                () -> UserConverter.toObject(null));
    }

    /**
     * Test toObject:
     * Too many attributes
     * @throws UserConversionException
     */
    @Test
    void testUserTooManyAttributes() throws UserConversionException {
        UserConversionException thrown = assertThrows(
                UserConversionException.class,
                () -> UserConverter.toObject(USER_TOO_MANY_ATTRIBUTES));
    }
}