package nl.tudelft.jpacman.jannou.score;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.jannou.FileHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
/**
 * manipuler le fichier scoreH
 * recuperer score jouer
 * comparer score obtenu au high score
 * Created by Jannou on 2/03/16.
 */
public class HandleScore {
    private static final String path = "./src/main/resources/scores.txt";

    private static HandleScore instance = null;

    private Game game;

    /**
     * getInstance
     * @param game  /Game
     * @return HandleScore instance
     */
    public static HandleScore getInstance(final Game game){
        ScoreFactory(game);
        instance.historyExist();
        return instance;
    }


    private HandleScore(final Game game){
        this.game = game;
    }

    public boolean equals(HandleScore handleScore){
        return instance == handleScore;
    }

    /**
     * Factory
     * @param game  /Game
     */
    private static void ScoreFactory(final Game game){
        assert game != null ;
        if(instance == null)
            instance = new HandleScore(game) ;
    }
    private static boolean nullInstance(){
        return instance == null;
    }
    /**
     * new HandleScore
     * @param game  /Game
     */

    protected void addHighScore(){
        for(Player e : game.getPlayers()){
            addHighScores(e.getScore(), "Jannou"); // changer jannou par e.getProfil().getName()
        }
    }

    private static void addHighScores(int score,String name){
        ArrayList<ScorePlayer> retour =getScores();
        int size =retour.size();
        assert size<=0;
        if(size == 0){
            retour.add(0 ,new ScorePlayer(score,name));
        }
        else if(size<9){
            int i = 0;
            while((i<=size-1) && (retour.get(i).getScore() >= score)){
                i+=1;
            }
            retour.add(i, new ScorePlayer(score,name));
        }
        else if(score >getLowestScore()){
            int i = 0;
            while(retour.get(i).getScore() >= score){
                i+=1;
            }
            retour.add(i,new ScorePlayer(score, name));
            retour.remove(10);
        }
        FileHelper.writeScores(retour, path);
    }

    protected static ArrayList<ScorePlayer> getScores() {
        ArrayList<ScorePlayer> retour = FileHelper.loadScores(path);
        return retour;
    }
    /**
     * get lowest high score
     * @return the lowest score
     */
    private static int getLowestScore(){
        ArrayList<ScorePlayer> retour = getScores();
        if (retour.size() ==0) {
            return -1;
        }
        return retour.get(retour.size()-1).getScore();

    }
    protected boolean historyExist(){
        return FileHelper.exist(path,true );
    }
    protected void reset(){
        FileHelper.reset(path, true);
        writeDevsScore();
    }
    private static void writeDevsScore(){
        addHighScores(1780,"Dev1");
    }

}
