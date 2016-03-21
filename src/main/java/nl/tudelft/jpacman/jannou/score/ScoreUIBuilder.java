package nl.tudelft.jpacman.jannou.score;

import nl.tudelft.jpacman.game.Game;

/**
 * Constructeur de la gui et des ellement necessaire a la gestin des scores
 * Created by Jannou on 2/03/16.
 */
public class ScoreUIBuilder {
    private Game game ;
    private ScoreUI instance = null;
    private HandleScore hScore;

    /**
     * Constructeur ScoreUIBuilder
     * @param _game instande de Game
     */
    public ScoreUIBuilder(final Game _game ){
        this.game = _game;
        hScore=HandleScore.getInstance(game);
        updateScore();
    }

    /**
     * Creer une instance de ScoreUI
     */
    public void createScoreUI(){
        if(instance == null){
            instance = new ScoreUI(hScore);
        }
    }

    /**
     * Retourne l instance de ScoreUI
     * @return null si instance non initilise,  une instance de ScoreUI sinon
     */
    public ScoreUI getInstance(){ //maybe use to regroupe all Jframe into once
        return instance;
    }
    public void updateScore(){
        //profilup;
        hScore.addHighScore();
    }

    /**
     * created for test
     * @return instance HandleScore
     */
    public HandleScore gethScore(){
        return hScore;
    }
}
