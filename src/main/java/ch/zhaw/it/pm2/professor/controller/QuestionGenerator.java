package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.Config;
import ch.zhaw.it.pm2.professor.model.Question;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * This class creates questions. The operation, range and if it has double numbers, can be defined.
 * The question and answer are saved in a static Question-object once it's generated.
 */
public class QuestionGenerator {
    protected Question question;
    protected ScriptEngine engine;
    private static final int PLACES = 1;
    protected boolean hasDouble;
    protected LevelFactory.Difficulty difficulty;

    /**
     * Every QuestionGenerator-instance has to be defined if it has double numbers or not.
     *
     * @param hasDouble will the questions include double numbers
     */
    public QuestionGenerator(boolean hasDouble, LevelFactory.Difficulty difficulty) {
        question = new Question();
        ScriptEngineManager sem = new ScriptEngineManager();
        engine = sem.getEngineByName("JavaScript");
        this.hasDouble = hasDouble;
        this.difficulty = difficulty;
    }

    /**
     * Receive a random integer number of a given range. The bounds are inclusive.
     *
     * @param start of the range
     * @param end   of the range
     * @return a random integer
     */
    protected int getRandomInt(int start, int end) {
        return start + (int) (new Random().nextFloat() * (end - start));
    }

    /**
     * Receive a random double number of a given range rounded to one decimal place. The bounds are inclusive.
     *
     * @param start of the range
     * @param end   of the range
     * @return a random double
     */
    protected double getRandomDouble(int start, int end) {
        double randomDouble = start + new Random().nextDouble() * (end - start);
        return roundDouble(randomDouble);
    }

    /**
     * Round the double to value of PLACES places after decimal.
     *
     * @param randomDouble to round
     * @return rounded double
     */
    protected double roundDouble(double randomDouble) {
        BigDecimal bd = BigDecimal.valueOf(randomDouble);
        bd = bd.setScale(PLACES, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Generate a question with the given operation and range. If the generator can use doubles, then questions will
     * randomly switch between integers and doubles.
     *
     * @param operation  for the question, valid are +, -, * and /
     * @param lowerBound of the range
     * @param upperBound of the range
     * @return question as a String
     * @throws IllegalArgumentException if the given operation is invalid
     */

    public String getQuestion(String operation, int lowerBound, int upperBound) {
        if (checkOperator(operation)) {
            throw new IllegalArgumentException("Operation is invalid");
        }
        //if hasDouble is true then questions will randomly switch between integers and doubles
        int choose = 0;
        if (hasDouble) {
            choose = getRandomInt(0, 2);
        }
        switch (choose) {
            case 0:
                setQuestionInt(operation, lowerBound, upperBound);
                break;
            case 1:
                setQuestionDouble(operation, lowerBound, upperBound);
        }
        try {
            String answer = engine.eval(question.getQuestion()).toString();
            //if the answer of a question with double numbers result in a full number, then the ending will be removed
            if (choose == 1) {
                double roundedAnswer = roundDouble(Double.parseDouble(answer));
                answer = String.valueOf(roundedAnswer);
            }
            if (answer.endsWith(".0")) {
                answer = answer.replace(".0", "");
            }
            question.setAnswer(answer);
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
        return question.getQuestion();
    }

    /**
     * This method sets the numbers of the question. It will request new numbers until the divisionCheck passes.
     *
     * @param operation  of the question
     * @param lowerBound start range
     * @param upperBound end range
     */
    protected void setQuestionDouble(String operation, int lowerBound, int upperBound) {
        double num3;
        double num4;
        do {
            num3 = getRandomDouble(lowerBound, upperBound);
            num4 = getRandomDouble(lowerBound, upperBound);
        } while (!divisionCheck(num3, num4, operation));
        question.setQuestion(num3 + " " + operation + " " + num4);
    }

    /**
     * This method sets the numbers of the question. It will request new numbers until the divisionCheck
     * and subtractionCheckForBeginner passes.
     *
     * @param operation  of the question
     * @param lowerBound start range
     * @param upperBound end range
     */
    protected void setQuestionInt(String operation, int lowerBound, int upperBound) {
        int num1;
        int num2;
        do {
            num1 = getRandomInt(lowerBound, upperBound);
            num2 = getRandomInt(lowerBound, upperBound);
            //check if the result of a subtraction is negative
        } while (!subtractionCheckForBeginner(num1, num2, operation) || !divisionCheck(num1, num2, operation));
        question.setQuestion(num1 + " " + operation + " " + num2);
    }

    /**
     * This method will return the answer of the question as a String.
     *
     * @return answer as a String
     */
    public String getAnswer() {
        return question.getAnswer();
    }

    /**
     * Check if operator is valid.
     *
     * @param operation to control
     * @return if it's valid or not
     */
    protected boolean checkOperator(String operation) {
        return !operation.equals(Config.Operation.ADDITION.toString())
                && !operation.equals(Config.Operation.SUBTRACTION.toString())
                && !operation.equals(Config.Operation.MULTIPLICATION.toString())
                && !operation.equals(Config.Operation.DIVISION.toString());
    }

    /**
     * Check for level BEGINNER and operation Subtraction if the result is negative.
     * Compare the values of the minuend and subtrahend.
     *
     * @param num1      minuend
     * @param num2      subtrahend
     * @param operation to control
     * @return it only returns false if the level is BEGINNER, operation is - and the
     * subtrahend is bigger than the minuend
     */
    protected boolean subtractionCheckForBeginner(int num1, int num2, String operation) {
        if (difficulty.equals(LevelFactory.Difficulty.BEGINNER)
                && operation.equals(Config.Operation.SUBTRACTION.toString())) {
            return num1 >= num2;
        }
        return true;
    }

    /**
     * Check if it's division through zero or if the result isn't a natural number.
     *
     * @param num1      dividend
     * @param num2      divisor
     * @param operation to control
     * @return false if either is the case
     */
    protected boolean divisionCheck(int num1, int num2, String operation) {
        if (operation.equals(Config.Operation.DIVISION.toString())) {
            return (num2 != 0) && num1 % num2 == 0;
        }
        return true;
    }

    /**
     * Check if it's division through zero or if the result isn't a natural number.
     *
     * @param num1      dividend
     * @param num2      divisor
     * @param operation to control
     * @return false if either is the case
     */
    protected boolean divisionCheck(double num1, double num2, String operation) {
        if (operation.equals(Config.Operation.DIVISION.toString())) {
            return (num2 != 0.0) && num1 % num2 == 0.00;
        }
        return true;
    }
}
