package nl.tudelft.jpacman.score;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;

/**
 * Builder to create a ScoreUI to show Hall of Fame
 * Created by Jannou on 2/03/16.
 */
public class ScoreUIBuilder {
    private Game game ;
    private ScoreUI instance = null;
    private HandleScore hScore;

    public ScoreUIBuilder(final Game game ){
        assert game != null;
        this.game = game;
        HandleScore.historyExist();
        hScore=HandleScore.getInstance(game);
        hScore.addHighScore();
    }
    public void createScoreUI(){
        if(instance == null){
            instance = new ScoreUI(hScore);
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
