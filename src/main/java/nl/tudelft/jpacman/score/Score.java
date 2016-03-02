package nl.tudelft.jpacman.score;

import nl.tudelft.jpacman.game.Game;
/**
 * Created by Jannou on 2/03/16.
 */
public class Score {

    private static Score instance = null;

    private Game game;

    /**
     * getInstance
     * @param game
     * @return instance
     */
    public static Score getInstance(final Game game){
        ScoreFactory(game);
        return instance;
    }

    /**
     * Factory
     * @param game
     */
    private static void ScoreFactory(final Game game){
        assert game != null ;
        if(instance == null)
            instance = new Score(game) ;
    }
    private static boolean nullInstance(){
        return instance == null;
    }
    /**
     *
     * @param game
     */
    private Score(final Game game){
        this.game = game;
    }
    /*public boolean equals(Object score){
        return (Score.instance == score);
    }*/
}
