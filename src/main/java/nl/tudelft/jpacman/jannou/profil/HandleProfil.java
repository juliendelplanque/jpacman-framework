package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.jannou.FileHelper;

import java.util.ArrayList;

/**
 * Created by Jannou on 6/03/16.
 */
public class HandleProfil {
    private static final String path = "./src/main/resources/Profils/";
    private static HandleProfil instance = null;
    private Game game;

    /**
     * getInstance
     * @return HandleScore instance
     */
    public static HandleProfil getInstance(Game game){
        HandleProfilFactory(game);
        instance.repositExist();
        return instance;
    }

    private HandleProfil(Game _game){
        game = _game;
    }

    /*public boolean equals(HandleScore handleScore){
        return instance == handleScore;
    }*/

    /**
     */
    private static void HandleProfilFactory(Game game){
        if(instance == null)
            instance = new HandleProfil(game) ;
    }

    protected ArrayList<String> getProfils(){
        ArrayList<String> retour = FileHelper.loadProfils(path);
        return retour;
    }
    public Profil getProfil(String fileName){
        Profil retour = null;
        if(getProfils().contains(fileName)){
            retour = FileHelper.loadProfil(path + fileName);
        }
        return retour;
    }
    public boolean addNewProfil(String name){
        Profil neW = new Profil(name);
        return FileHelper.writeProfil(neW,path+name+".xml");
    }
    public boolean removeProfil(String name){
        String fileName = name+".xml";
        boolean retour = false;
        if(getProfils().contains(fileName)){
            retour = FileHelper.deleteProfil(path + fileName);
        }
        return retour;
    }

    protected boolean repositExist(){
        return FileHelper.exist(path,false);
    }


}
