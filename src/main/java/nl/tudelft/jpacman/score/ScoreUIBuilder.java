package nl.tudelft.jpacman.score;

import nl.tudelft.jpacman.game.Game;

/**
 * Created by Jannou on 2/03/16.
 */
public class ScoreUIBuilder {
    private Game game ;

    public ScoreUIBuilder(final Game game ){
        assert game != null;
        this.game = game;

    }
    public ScoreUI createScoreUI(){
        return new ScoreUI();
    }


}
