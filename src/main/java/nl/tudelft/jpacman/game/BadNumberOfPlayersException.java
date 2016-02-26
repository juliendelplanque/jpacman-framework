package nl.tudelft.jpacman.game;

/**
 * This Exception is thrown when the number of players chosen
 * for a multi-player game is more than 4 or less than 2.
 */
public class BadNumberOfPlayersException extends RuntimeException {
    public BadNumberOfPlayersException(){
        super("This gameplay expect at least 2 players and at more 4 players.");
    }
}
