package nl.tudelft.jpacman.jannou.score;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.jannou.FileHelper;
import nl.tudelft.jpacman.jannou.profil.HandleProfil;
import nl.tudelft.jpacman.level.Player;

import java.util.ArrayList;
/**
 * manipuler le fichier scoreH
 * recuperer score jouer
 * comparer score obtenu au high score
 * Created by Jannou on 2/03/16.
 */
public class HandleScore {
    private static final String path = "./src/main/resources/scores.txt";

    private static HandleScore instance = null;
    private static HandleProfil instance2 = null;
    private Game game;

    /**
     * getInstance
     * @param game  /Game
     * @return HandleScore instance
     */
    public static HandleScore getInstance(final Game game){
        ScoreFactory(game);
        instance.init();
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
        if(instance2 == null)
            instance2 = HandleProfil.getInstance() ;
    }
    private static boolean nullInstance(){
        return instance == null;
    }

    /**
     * check if there is new high score
     */
    protected void addHighScore(){
        for(Player e : game.getPlayers()) {
            if (e.getProfil().getBestScore()< e.getScore()){
                e.getProfil().setBestScore(e.getScore());
                instance2.updateProfil(e.getProfil());
            }
            addHighScores(e.getScore(), e.getProfil().getName());
        }
    }

    private static void addHighScores(int score,String name){
        ArrayList<ScorePlayer> retour =getScores();
        int size =retour.size();
        assert size<=0;
        if(size == 0){
            retour.add(0 ,new ScorePlayer(score,name));
        }
        else if(size<10){
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
        return FileHelper.loadScores(path);
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
        return FileHelper.exist(path);
    }
    protected void reset(){
        FileHelper.reset(path, true);
        writeDevsScore();
    }
    private static void writeDevsScore(){
        addHighScores(1780,"Dev1");
    }
    private void init(){
        if(!instance.historyExist()){
            FileHelper.init(path, true);
            writeDevsScore();
        }
    }

}
