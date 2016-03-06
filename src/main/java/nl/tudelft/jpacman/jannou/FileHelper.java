package nl.tudelft.jpacman.jannou;

import nl.tudelft.jpacman.jannou.score.ScorePlayer;

import java.io.*;
import java.util.ArrayList;

/**
 * Handle files
 * Created by Jannou on 6/03/16.
 */
public class FileHelper {

    /**
     * creat file at path if it doesn't exist an return false, return true otherwise
     * @return true if file exist false otherwise
     */
    protected static boolean exist(String path){
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
    protected static ArrayList<ScorePlayer> loadScores(String path) {
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
     * add new high score
     * @param toWrite something to be written
     * @param path file's path
     */
    protected static void writeScores(ArrayList<ScorePlayer> toWrite, String path){
        File f = new File(path);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            for (ScorePlayer p : toWrite) {
                pw.println("" + p.getScore() + " :: " + p.getPlayer());
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reset the file at given path
     * @param path
     * @return
     */
    protected static boolean reset(String path){
        File f = new File(path);
        boolean succes = false;
        if(f.exists()){
            succes = f.delete();
        }
        if(succes){
            return !exist(path);
        }
        return false;
    }
}
