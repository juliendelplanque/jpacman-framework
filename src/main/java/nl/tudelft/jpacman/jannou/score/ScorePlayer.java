package nl.tudelft.jpacman.jannou.score;

/**
 * Classe representant un score d un joueur
 * Created by Jannou on 2/03/16.
 */
public class ScorePlayer {
    private int score ;
    private String player; //profil par la suite ?

    /**
     * Constructeur ScorePlayer
     * @param score le score associe au joueur
     * @param name le nom associe au joueur
     */
    public ScorePlayer(final int score, final String name){
        this.score = score;
        this.player = name;
    }

    /**
     * Retourne le meilleur score associe au joueur
     * @return le meilleur score associe au joueur
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Retourne le nom associe au joueur
     * @return le nom associe au joueur
     */
    public String getPlayer(){
        return this.player;
    }

    /**
     * toString()
     * @return toString()
     */
    public String toString() {
        return  getPlayer() + " has scored " + getScore() ;
    }
}
