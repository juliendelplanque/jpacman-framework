package nl.tudelft.jpacman.jannou.score;

/**
 * Created by Jannou on 2/03/16.
 */
public class ScorePlayer {
    private int score ;
    private String player; //profil par la suite ?
    public ScorePlayer(){} // createur vide
    public ScorePlayer(int score, String name){
        this.score = score;
        this.player = name;
    }
    public int getScore(){
        return this.score;
    }
    public String getPlayer(){
        return this.player;
    }
    public String toString() {
        return  getPlayer() + " has scored " + getScore() ;
    }
}
