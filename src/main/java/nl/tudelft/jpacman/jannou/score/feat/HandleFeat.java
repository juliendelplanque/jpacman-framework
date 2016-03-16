package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.jannou.FileHelper;
import nl.tudelft.jpacman.jannou.profil.HandleProfil;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.ghost.Ghost;

import java.util.ArrayList;
/**
 * Gestion des exploits , ajout, mise a jour , mise a jour du score du jouer
 * Created by Jannou on 10/03/16.
 */
public class HandleFeat {
    private static final String path = "./src/main/java/nl/tudelft/jpacman/jannou/score/feat/";

    /**
     * Retour tous les exploits qu'un joueur peut realiser
     * @return tous les exploits qu'un joueur peut realiser
     */
    public static ArrayList<Feat> feats(){
        ArrayList<Feat> feat = new ArrayList<>();
        for(String s : FileHelper.loadProfils(path)){
            String clasS = s.split(".java")[0];
            if(!(clasS.equals("Feat") || clasS.equals("HandleFeat")))
                try
                {
                    feat.add((Feat)Class.forName("nl.tudelft.jpacman.jannou.score.feat."+clasS).newInstance());
                }
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
                {
                    // La classe n'existe pas
                }
        }

        return feat;
    }

    /**
     * Met a jour un exploit particulier
     * @param player le joueur
     * @param _feat l exploit a mettre a jour
     */
    private static void updateFeat(Player player, Feat _feat){
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
            _feat.updatestate();
            if(_feat.isRealised()) {
                System.out.println(_feat.getName());
                updateScore(player, _feat);
                player.getProfil().proposeFeats().remove(_feat);
            }
            if(!old)
                player.getProfil().addFeat(_feat);
            HandleProfil.getInstance().updateProfil(player.getProfil());
        }
    }

    /**
     * Met a jour le score du joueur
     * @param player le joueur
     * @param _feat l exploit realise
     */
    private static void updateScore(Player player, Feat _feat){
        player.addPoints(_feat.getValue());
    }
    public static void trigger(int score, Ghost kill, Player player){
        if( player.getProfil() !=null) {
            for (Feat f : player.getProfil().proposeFeats()) { //on ne regarde que les feat qui n'ont pas encore ete realisee
                if (f.condition(score, kill))
                    updateFeat(player, f); // on update le feat
            }
        }
    }

    /**
     * Permet de creer une instance d une classe qui implemente Feat sur base du nom de cette classe
     * @param name le nom de la classe
     * @return une instance de Feat
     */
    public static Feat createFeat(String name){
        try
        {
            return ((Feat)Class.forName("nl.tudelft.jpacman.jannou.score.feat."+name).newInstance());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            // La classe n'existe pas
        }
        return null;
    }

}
