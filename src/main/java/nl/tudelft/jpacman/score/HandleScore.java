package nl.tudelft.jpacman.score;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;

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
        return instance;
    }


    private HandleScore(final Game game){
        this.game = game;
    }
    public boolean equals(HandleScore handleScore){
        return instance == handleScore;
    }

    protected void addHighScore(){
        for(Player e : game.getPlayers()){
            HandleScore.addHighScore(e.getScore(), "Jannou"); // changer jannou par e.getprof.getname
        }
    }

    protected ArrayList<ScorePlayer> loadScores() {
        return HandleScore.loadScore();
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

    /**
     * creat scores.txt if it doesn't exist an return false, return true otherwise
     * @return true if file exist false otherwise
     */
    protected static boolean historyExist(){
        File f = new File(path);
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
    private static ArrayList<ScorePlayer> loadScore() {
        ArrayList<ScorePlayer> retour = new ArrayList<ScorePlayer>();
        try{
            File f = new File (path);
            FileReader fr = new FileReader (f);
            BufferedReader br = new BufferedReader (fr);
            try
            {
                String line = br.readLine();
                while (line != null)
                {
                    String[] data = line.split(" :: ");
                    if (data.length == 2) {
                        ScorePlayer tp = new ScorePlayer(Integer.parseInt(data[0]),data[1]);
                        retour.add(tp);
                    }
                    line = br.readLine();
                }
                br.close();
                fr.close();
            }
            catch (IOException exception)
            {
                System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
            }
        }catch (FileNotFoundException exception){
            System.out.println ("Le fichier n'a pas été trouvé");
        }
        return retour;
    }

    /**
     * get lowest high score
     * @return the lowest score
     */
    private static int getLowestScore(){
        ArrayList<ScorePlayer> retour = loadScore();
        if (retour.size() ==0) {
            return -1;
        }
        return retour.get(retour.size()-1).getScore();

    }

    /**
     * add new high score
     * @param score of player
     * @param name of player
     */
    private static void addHighScore(int score , String name){
        ArrayList<ScorePlayer> retour =loadScore();
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
        File f = new File(path);
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
    protected static boolean reset(){
        File f = new File(path);
        boolean succes = false;
        if(f.exists()){
            succes = f.delete();
        }
        if(succes){
            return !HandleScore.historyExist();
        }
        return false;
    }



}
