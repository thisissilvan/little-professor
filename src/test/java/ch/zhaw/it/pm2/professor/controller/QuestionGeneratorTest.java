package ch.zhaw.it.pm2.professor.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QuestionGeneratorTest {
    QuestionGenerator questionGenerator;
    DeterministicQuestionGenerator deterministicGenerator;

    /**
     * Tests if the generator saves questions and answers correctly for integer numbers.
     */
    @Test
    void questionIntTest() {
        deterministicGenerator = new DeterministicQuestionGenerator(false, 3, LevelFactory.Difficulty.BEGINNER);
        String question = deterministicGenerator.getQuestion("+", 0, 10);
        String answer = deterministicGenerator.getAnswer();
        assertEquals("3 + 3", question);
        assertEquals("6", answer);
    }

    /**
     * Tests if the generator saves questions and answers correctly for double numbers.
     */
    @Test
    void questionDoubleTest() {
        deterministicGenerator = new DeterministicQuestionGenerator(true, 2.3, LevelFactory.Difficulty.ADVANCED);
        String question = deterministicGenerator.getQuestion("+", 0, 10);
        String answer = deterministicGenerator.getAnswer();
        assertEquals("2.3 + 2.3", question);
        assertEquals("4.6", answer);
    }

    /**
     * Tests if questions with doubles have a full number result, then the ending .0 is deleted.
     */
    @Test
    void doubleResultIsRoundedTest() {
        deterministicGenerator = new DeterministicQuestionGenerator(true, 2.5, LevelFactory.Difficulty.INTERMEDIATE);
        String question = deterministicGenerator.getQuestion("+", 0, 10);
        String answer = deterministicGenerator.getAnswer();
        assertEquals("2.5 + 2.5", question);
        assertEquals("5", answer);
        assertNotEquals("5.0", answer);
    }

    /**
     * Tests if questions are random.
     */
    @Test
    void isRandomTest() {
        questionGenerator = new QuestionGenerator(false, LevelFactory.Difficulty.BEGINNER);
        String question = questionGenerator.getQuestion("-", 0, 10);
        String question2 = questionGenerator.getQuestion("-", 0, 10);
        assertNotEquals(question, question2);
    }

    /**
     * Tests if the numbers in questions are within the given range.
     */
    @Test
    void isInRangeTest() {
        questionGenerator = new QuestionGenerator(false, LevelFactory.Difficulty.ADVANCED);
        String question = questionGenerator.getQuestion("-", 0, 100);
        String[] split = question.split(" ");
        int num1 = Integer.parseInt(split[0]);
        int num2 = Integer.parseInt(split[2]);
        assert (num1 >= 0);
        assert (num1 <= 100);
        assert (num2 >= 0);
        assert (num2 <= 100);
    }

    /**
     * Tests if the result of a question is correctly calculated and saved.
     */
    @Test
    void correctResultTest() {
        questionGenerator = new QuestionGenerator(false, LevelFactory.Difficulty.INTERMEDIATE);
        String question = questionGenerator.getQuestion("*", 0, 100);
        String answer = questionGenerator.getAnswer();
        String[] split = question.split(" ");
        int num1 = Integer.parseInt(split[0]);
        int num2 = Integer.parseInt(split[2]);
        int expectedResult = num1 * num2;
        int actualResult = Integer.parseInt(answer);
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Tests if IllegalArgumentException is thrown when an invalid operation is received.
     */
    @Test
    void invalidOperation() {
        questionGenerator = new QuestionGenerator(true, LevelFactory.Difficulty.BEGINNER);
        Exception exception = assertThrows(RuntimeException.class, () -> questionGenerator.getQuestion("&", 0, 100));
        String expectedMessage = "Operation is invalid";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }
}