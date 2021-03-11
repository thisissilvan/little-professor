package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.Config;

import java.util.Arrays;
import java.util.List;

import static ch.zhaw.it.pm2.professor.Config.MAX_CHARS_USERNAME;
import static ch.zhaw.it.pm2.professor.Config.MIN_CHARS_USERNAME;

/**
 * The class Parser creates a DisplayIO. It reads the user input and transfers it into a command. The class
 * itself returns a Config.Command object.
 */
public class Parser {
    /**
     * Method parse to parse an input. It takes a List with accepted commands and an input-string which comes from the
     * user. If the input is not null, the method iterates with the given string over the List. As soon as a command
     * matches another command from the list, the return-value will be a Config.Command object with the given command.
     * @param acceptedCommands  a List with accepted commands
     * @param input             an input coming from the user
     * @return                  a Config.Command object
     * @throws InvalidInputException if the input does not match another command from the list, this exception gets
     *                               thrown
     */
    public Config.Command parseInput(List<Config.Command> acceptedCommands, String input) throws InvalidInputException {
        if (input == null) {
            throw new NullPointerException("The input must not be null.");
        }
        for (Config.Command command : acceptedCommands) {

            if (command.toString().equals(input.trim())) {
                return command;
            }
        }
        throw new InvalidInputException("This Input is invalid, please provide a valid command.");
    }

    /**
     * The method parseInput with another signature. As accepted commands it takes an Array of commands.
     * @param acceptedCommands  an array of accepted commands
     * @param input             an input from the user
     * @return                  an Config.Command object which gets created in the parseInput method with the
     *                          List signature
     * @throws InvalidInputException if the input does not match another command from the Array, this exception gets
     *                               thrown
     */
    public Config.Command parseInput(Config.Command[] acceptedCommands, String input) throws InvalidInputException {
        return parseInput(Arrays.asList(acceptedCommands), input);
    }

    /**
     * Method to parse a username input and returns the name if it matches the requirements. Extra spaces getting
     * deleted automatically.
     * @param input     the input String (username) the user chooses
     * @return          the input without extra spaces if this is valid
     * @throws InvalidInputException    if the input does not match the requirements, this exception gets thrown
     */
    public String parseName(String input) throws InvalidInputException {
        if (input == null) {
            throw new NullPointerException("The input must not be null.");
        }
        if (input.trim().length() < MIN_CHARS_USERNAME) {
            throw new InvalidInputException("Please choose at least " + MIN_CHARS_USERNAME + " chars for your username.");
        }
        if (input.trim().length() > MAX_CHARS_USERNAME) {
            throw new InvalidInputException("The username has to many chars, please provide an username " +
                    "with " + MAX_CHARS_USERNAME + " chars maximum.");
        }
        if (!input.trim().matches("[A-Za-z]+")) {
            throw new InvalidInputException("The username must only contain upper and lowercase letters.");
        }
        return input.trim();
    }
}