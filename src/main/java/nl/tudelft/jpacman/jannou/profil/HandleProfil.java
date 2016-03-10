package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.jannou.FileHelper;

import java.util.ArrayList;

/**
 * Handle profils load create  write delete etc
 * Created by Jannou on 6/03/16.
 */
public class HandleProfil {
    private static final String path = "./src/main/resources/Profils/";
    private static HandleProfil instance = null;

    /**
     * getInstance
     * @return HandleScore instance
     */
    public static HandleProfil getInstance(){
        HandleProfilFactory();
        instance.init();
        return instance;
    }

    private HandleProfil(){
    }

    private static void HandleProfilFactory(){
        if(instance == null)
            instance = new HandleProfil() ;
    }

    protected ArrayList<String> getProfils(){
        return FileHelper.loadProfils(path);
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
        return FileHelper.exist(path);
    }
    private void init(){
        if(!instance.repositExist())
            FileHelper.init(path, false);
    }


    public void updateProfil(Profil profil) {
        removeProfil(profil.getName());
        FileHelper.writeProfil(profil,path+profil.getName()+".xml");

    }
}
