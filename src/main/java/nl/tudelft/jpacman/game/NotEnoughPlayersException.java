package nl.tudelft.jpacman.game;

/**
 * Created by julien on 25/02/16.
 */
public class NotEnoughPlayersException extends RuntimeException {
    public NotEnoughPlayersException(){
        super("This gameplay expect at least 2 players.");
    }
}
