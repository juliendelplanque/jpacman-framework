package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.jannou.FileHelper;

import java.util.ArrayList;

/**
 * Gerer les profils ( chargement, ecriture, suppression, mise a jour)
 * Created by Jannou on 6/03/16.
 */
public class HandleProfil {
    private static final String path = "./src/main/resources/Profils/";
    private static HandleProfil instance = null;

    /**
     * Retourne une instance de HandleProfil (unique instance pour tout le jeux)
     * @return une instance de HandleProfil (unique instance pour tout le jeux)
     */
    public static HandleProfil getInstance(){
        HandleProfilFactory();
        instance.init();
        return instance;
    }

    /**
     * Constructeur private
     */
    private HandleProfil(){
    }

    /**
     * Factory private
     */
    private static void HandleProfilFactory(){
        if(instance == null)
            instance = new HandleProfil() ;
    }

    /**
     * Retourne le nom de tous les fichiers contenu dans le dossier Profils
     * @return ArrayList<String> contenant les noms
     */
    protected ArrayList<String> getProfils(){
        return FileHelper.loadProfils(path);
    }

    /**
     * Charge un profil sur base du nom du fichier ou est enregistre le profil
     * @param fileName le nom du fichier ou est enregistre le profil
     * @return un profil
     */
    public Profil getProfil(final  String fileName){
        Profil retour = null;
        if(getProfils().contains(fileName)){
            retour = FileHelper.loadProfil(path + fileName);
        }
        return retour;
    }

    /**
     * Permet de rajouter un nouveau profil
     * @param name le nom du nouveau profil
     * @return true si le profil a ete enregistre false sinon
     */
    public boolean addNewProfil(final  String name){
        Profil neW = new Profil(name);
        return FileHelper.writeProfil(neW,path+name+".xml");
    }

    /**
     * Permet de supprimer un profil
     * @param name le nom du profil a supprimer
     * @return true si fichier a ete supprime false sinon
     */
    public boolean removeProfil(final String name){
        String fileName = name+".xml";
        boolean retour = false;
        if(getProfils().contains(fileName)){
            retour = FileHelper.deleteProfil(path + fileName);
        }
        return retour;
    }

    /**
     * Verifie si le dossier Profils existe
     * @return true si le dossier existe false sinon
     */
    protected boolean repositExist(){
        return FileHelper.exist(path);
    }

    /**
     * Initialise le dossier Profils
     */
    private void init(){
        if(!instance.repositExist())
            FileHelper.init(path, false);
    }

    /**
     * Permet de mettre a jour un profil
     * @param profil le profil a mettre a jour
     */
    public void updateProfil(final Profil profil) {
        removeProfil(profil.getName());
        FileHelper.writeProfil(profil,path+profil.getName()+".xml");

    }
}
