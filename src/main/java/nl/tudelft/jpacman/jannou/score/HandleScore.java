package nl.tudelft.jpacman.jannou.score;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.jannou.FileHelper;
import nl.tudelft.jpacman.jannou.profil.HandleProfil;
import nl.tudelft.jpacman.level.Player;

import java.util.ArrayList;
/**
 * Gestion des high scores, ajout, et remise a zero du fichier
 * Created by Jannou on 2/03/16.
 */
public class HandleScore {
    private static final String path = "./src/main/resources/scores.txt";

    private static HandleScore instance = null;
    private static HandleProfil instance2 = null;
    private Game game;
    /**
     * Retourne une instance de HandleScore (unique instance pour tout le jeux)
     * @param game instance de Game
     * @return une instance de HandleScore (unique instance pour tout le jeux)
     */
    public static HandleScore getInstance(final Game game){
        ScoreFactory(game);
        instance.init();
        return instance;
    }

    /**
     * Constructeur private
     * @param game isntance de Game
     */
    private HandleScore(final Game game){
        this.game = game;
    }

    /**
     * verifier si deux instance de HandleScore sont identique ou non
     * @param handleScore instance a tester
     * @return true si les deux instance sont les meme false sinon
     */
    public boolean equals(HandleScore handleScore){
        return instance == handleScore;
    }

    /**
     * Factory private
     * @param game  /Game
     */
    private static void ScoreFactory(final Game game){
        assert game != null ;
        if(instance == null)
            instance = new HandleScore(game) ;
        if(instance2 == null)
            instance2 = HandleProfil.getInstance() ;
    }

    /**
     * Met a jour le high score associe au profil du joueur et verifie si il y a un nouveau high score a ajouter
     */
    protected void addHighScore(){
        for(Player e : game.getPlayers()) {
            if (e.getProfil() != null) {
                if (e.getProfil().getBestScore() < e.getScore()) {
                    e.getProfil().setBestScore(e.getScore());
                    instance2.updateProfil(e.getProfil());
                }
                addHighScores(e.getScore(), e.getProfil().getName());
            }
        }
    }

    /**
     * Ajoute un score
     * @param score le score a ajouter
     * @param name le nom a associer au score
     */
    public static void addHighScores(int score,String name){
        ArrayList<ScorePlayer> retour =getScores();
        int size =retour.size();
        assert size>=0;
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

    /**
     * Retourne l ensemble des high scores enregistres
     * @return l ensemble des high scores enregistres
     */
    protected static ArrayList<ScorePlayer> getScores() {
        return FileHelper.loadScores(path);
    }
    /**
     * Retourne le plus petit des high scores
     * @return le plus petit des high scores
     */
    private static int getLowestScore(){
        ArrayList<ScorePlayer> retour = getScores();
        if (retour.size() ==0) {
            return -1;
        }
        return retour.get(retour.size()-1).getScore();
    }

    /**
     * Verifie si le fichier scores.txt existe
     * @return true si le fichier existe false sinon
     */
    protected boolean historyExist(){
        return FileHelper.exist(path);
    }

    /**
     * Remet le fichier des high scores a zero
     */
    protected void reset(){
        FileHelper.reset(path, true);
        writeDevsScore();
    }

    /**
     * Permet d ecrire le score realise par chaque dev
     */
    private static void writeDevsScore(){
        addHighScores(1780,"Dev1");
    }

    /**
     * Initialise le fichier des high scores
     */
    private void init(){
        if(!instance.historyExist()){
            FileHelper.init(path, true);
            writeDevsScore();
        }
    }

}
