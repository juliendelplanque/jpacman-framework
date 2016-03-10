package nl.tudelft.jpacman.jannou.score;

import nl.tudelft.jpacman.game.Game;

/**
 * Builder to create a ScoreUI to show Hall of Fame
 * Created by Jannou on 2/03/16.
 */
public class ScoreUIBuilder {
    private Game game ;
    private ScoreUI instance = null;
    private HandleScore hScore;

    public ScoreUIBuilder(final Game _game ){
        this.game = _game;
        hScore=HandleScore.getInstance(game);
        updateScore();
    }
    public void createScoreUI(){
        if(instance == null){
            instance = new ScoreUI(hScore);
        }
    }
    public ScoreUI getInstance(){ //maybe use to regroupe all Jframe into once
        return instance;
    }
    public void updateScore(){
        //profilup;
        hScore.addHighScore();
    }
}
