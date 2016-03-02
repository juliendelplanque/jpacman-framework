package nl.tudelft.jpacman.score;

import nl.tudelft.jpacman.game.Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * manipuler le fichier scoreH
 * recuperer score jouer
 * comparer score obtenu au high score
 * Created by Jannou on 2/03/16.
 */
public class HandleScore {

    private static HandleScore instance = null;

    private Game game;

    /**
     * getInstance
     * @param Game game
     * @return HandleScore instance
     */
    public static HandleScore getInstance(final Game game){
        ScoreFactory(game);
        return instance;
    }

    /**
     * Factory
     * @param Gmae game
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
     * @param Game game
     */
    private HandleScore(final Game game){
        this.game = game;
    }
    public boolean equals(HandleScore handleScore){
        return instance == handleScore;
    }

    /**
     * creat scores.txt if it doesn't exist an return false, return true otherwise
     * @return true if file exist false otherwise
     */
    public static boolean historyExist(){
        File f = new File("./src/main/resources/scores.txt");
        if ( !f.exists() ) {
            try {
                boolean succes = f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    /**
     * load all high scores
     * @return ArrayList<ScorePlayer> of player's and player's score
     */
    public static ArrayList<ScorePlayer> loadScores() {
        ArrayList<ScorePlayer> retour = new ArrayList<ScorePlayer>();
        Scanner sc;
        try {
            sc = new Scanner(new File("./src/main/resources/scores.txt"));
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] data = line.split(" :: ");
                if (data.length == 2) {
                    retour.add(new ScorePlayer(Integer.parseInt(data[0]),data[1]));
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return retour;
    }

    /**
     * get lowest high score
     * @return the lowest score
     */
    public static int getLowestScore(){
        ArrayList<ScorePlayer> retour = loadScores();
        if (retour.size() ==0) {
            return 0;
        }
        return retour.get(retour.size()-1).getScore();

    }

    /**
     * add new high score
     * @param score of player
     * @param name of player
     */
    public void addHighScore(int score , String name){
        ArrayList<ScorePlayer> retour =loadScores();
        ScorePlayer temp1 = new ScorePlayer(score, name);
        ScorePlayer temp2 = null ;
        if( retour.size() == 0 ){
            retour.add(0 ,temp1.copy());
        }
        else{
            int i=0;

            while(retour.get(i).getScore() > score){
                i+=1;
            }
            retour.add(i, temp1);
            if(retour.size()>10){
                retour.remove(10);
            }
        }
        File f = new File("./src/main/resources/scores.txt");
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            for (ScorePlayer p : retour) {
                pw.println("" + p.getScore() + " :: " + p.getPlayer());
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void rest(){
        File f = new File("./src/main/resources/scores.txt");
        if(f.exists()){
            boolean succes = f.delete();
        }
        historyExist();
    }



}
