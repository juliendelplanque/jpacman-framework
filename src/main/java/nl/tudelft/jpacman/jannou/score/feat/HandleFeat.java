package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.jannou.FileHelper;
import nl.tudelft.jpacman.jannou.profil.HandleProfil;
import nl.tudelft.jpacman.jannou.profil.Profil;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.ghost.Ghost;

import java.util.ArrayList;
/**
 * Created by Jannou on 10/03/16.
 */

public class HandleFeat {
    private static final String path = "./src/main/java/nl/tudelft/jpacman/jannou/score/feat/";

    /**
     * get all possible feats
     * @return all possible feats
     */
    public static ArrayList<Feat> feats(){
        ArrayList<Feat> feat = new ArrayList<Feat>();
        for(String s : FileHelper.loadProfils(path)){
            String clasS = s.split(".java")[0];
            if(!(clasS.equals("Feat") || clasS.equals("HandleFeat")))
                try
                {
                    feat.add((Feat)Class.forName("nl.tudelft.jpacman.jannou.score.feat."+clasS).newInstance());
                }
                catch (ClassNotFoundException e)
                {
                    // La classe n'existe pas
                }
                catch (InstantiationException e)
                {
                    // La classe est abstract ou est une interface ou n'a pas de constructeur accessible sans paramètre
                }
                catch (IllegalAccessException e)
                {
                    // La classe n'est pas accessible
                }
        }

        return feat;
    }
    private static boolean checkFeats(Profil profil, Feat feat){
        for(Feat f :profil.getFeats()){
            if(f.getName().equals(feat.getName()))
                return true;
        }
        return false;
    }
    private static void updateFeat(Player player, Feat _feat){
        Feat feat = _feat;
        boolean old = false;
        if(player.getProfil() != null){ // pour les tests, normalement on ne peut pas commencer game si on n'a pas
            // selectionner de profil or a ce stade le game est en cours donc on a forcement un profil.
            /*if(player.getProfil().getFeats().contains(_feat)){
                old= true;
            }*/

            for(Feat f :player.getProfil().getFeats()){  //recuperer feat du player
                if(f.getName().equals(_feat.getName())) { //si player a deja commencer exploit _feat
                    //feat = f;
                    old= true;
                }
            }
            feat.updatestate();
            if(feat.isRealised())
                updateScore(player,feat);
            if(!old)
                player.getProfil().addFeat(feat);
            HandleProfil.getInstance().updateProfil(player.getProfil());
        }
    }
    private static void updateScore(Player player, Feat _feat){
        player.addPoints(_feat.getValue());
    }
    public static void trigger(int score, Ghost kill, Player player){
        if( player.getProfil() !=null) {
            for (Feat f : player.getProfil().proposeFeats()) { //on ne regarde que les feat qui n'ont pas encore ete realisee
                if (f.condition(score, kill)) {
                    updateFeat(player, f); // on update le feat (on envois l'instance)
                }
            }
        }
    }
    public static Feat createFeat(String name){
        try
        {
            return ((Feat)Class.forName("nl.tudelft.jpacman.jannou.score.feat."+name).newInstance());
        }
        catch (ClassNotFoundException e)
        {
            // La classe n'existe pas
        }
        catch (InstantiationException e)
        {
            // La classe est abstract ou est une interface ou n'a pas de constructeur accessible sans paramètre
        }
        catch (IllegalAccessException e)
        {
            // La classe n'est pas accessible
        }
        return null;
    }

}
