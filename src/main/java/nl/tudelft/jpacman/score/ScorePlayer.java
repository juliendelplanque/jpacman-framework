package nl.tudelft.jpacman.score;

/**
 * Created by Jannou on 2/03/16.
 */
public class ScorePlayer {
    private int score ;
    private String player; //profil par la suite ?
    public ScorePlayer(){}
    public ScorePlayer(int score, String name){
        this.score = score;
        this.player = name;
    }
    public ScorePlayer copy(){
        return new ScorePlayer(this.getScore(), this.getPlayer());
    }
    public int getScore(){
        return this.score;
    }
    public String getPlayer(){
        return this.player;
    }
    public void setScore(int sco){
        this.score = sco;
    }
    public void setPlayer(String pa){
        this.player= pa;
    }
    public String toString() {
        return  getPlayer() + " has scored " + getScore() ;
    }
}
