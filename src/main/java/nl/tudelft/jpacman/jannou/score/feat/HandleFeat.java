package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.jannou.FileHelper;
import nl.tudelft.jpacman.jannou.profil.HandleProfil;
import nl.tudelft.jpacman.jannou.profil.Profil;
import nl.tudelft.jpacman.level.Player;

import java.util.ArrayList;
/**
 * Created by Jannou on 10/03/16.
 */

public class HandleFeat {
    private static final String path = "./src/main/java/nl/tudelft/jpacman/jannou/score/feat/";

    /**
     * getInstance
     * @return HandleScore instance
     */

    private static ArrayList<Feat> feats(){
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
        Feat feat = null;
        int i = 0;
        for(Feat f :player.getProfil().getFeats()){
            if(f.getName().equals(_feat.getName())){
                i = 1;
                if( f.isRealised()==false ){
                    i=2;
                }
                feat = f;
            }
        }
        if( i ==0)
            newFeat(player, _feat);
        if( i == 2){
            feat.updatestate(1);
            if(feat.isRealised())
                updateScore(player,feat);
        }
        HandleProfil.getInstance().updateProfil(player.getProfil());
    }
    private static boolean newFeat(Player player, Feat feat){
        if(feat.isRealised())
            updateScore(player,feat);
        player.getProfil().addFeat(feat);
        return true;
    }
    private static void updateScore(Player player, Feat _feat){
        player.addPoints(_feat.getValue());
    }
    public static void trigger(int score,int kill, Player player){
        for(Feat f : feats()){
            if(f.condition(score, kill)){
                updateFeat(player , f);
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
