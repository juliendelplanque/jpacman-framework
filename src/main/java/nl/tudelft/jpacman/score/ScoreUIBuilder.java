package nl.tudelft.jpacman.score;

import nl.tudelft.jpacman.game.Game;

/**
 * Builder to create a ScoreUI to show Hall of Fame
 * Created by Jannou on 2/03/16.
 */
public class ScoreUIBuilder {
    private Game game ;
    private ScoreUI instance = null;

    public ScoreUIBuilder(final Game game ){
        assert game != null;
        this.game = game;


    }
    public void createScoreUI(){
        if(instance == null){
            instance = new ScoreUI();
        }
    }
    public ScoreUI getInstance(){
        return instance;
    }

    protected void reset(){

    }
    protected void close(){

    }
}
