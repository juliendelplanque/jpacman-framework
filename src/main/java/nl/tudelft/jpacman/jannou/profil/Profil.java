package nl.tudelft.jpacman.jannou.profil;
import nl.tudelft.jpacman.jannou.score.feat.Feat;
import nl.tudelft.jpacman.jannou.score.feat.HandleFeat;

import java.util.ArrayList;

/**
 * Object representant un profil
 * Created by Jannou on 5/03/16.
 */
public class Profil {
    private final String name;
    private int bestScore  = 0;
    private ArrayList<Feat> feats = new ArrayList<>();

    /**
     * Constructeur de Profil
     * @param _name le nom du profil
     */
    public Profil(String _name){
        name = _name;
    }

    /**
     * Retourne le nom associe au profil
     * @return le nom associe au profil
     */
    public String getName(){
        return name;
    }

    /**
     * Retourne le meilleur score associe au profil
     * @return le meilleur score asssocie au profil
     */
    public int getBestScore(){
        return bestScore;
    }

    /**
     * Retourne l ensemble des exploits associe au profil
     * @return l ensemble des exploits associe au profil
     */
    public ArrayList<Feat> getFeats(){
        return feats ;
    }

    /**
     * Modifie le meilleur score associe au profil
     * @param score le nouveau meilleur score associe au profil
     */
    public void setBestScore(final int score){
        bestScore = score;
    }

    /**
     * Ajoute un exploit a associe au profil
     * @param feat l exploit a associe au profil
     */
    public void addFeat(final Feat feat){
        feats.add(feat);
    }

    /**
     * Retourne un String represantant le profil
     * @return un String representant le profil
     */
    public String toMaps() {
        String feat = "";
        for(Feat f :getFeats()){
            if(f != getFeats().get(getFeats().size()-1)){
                feat += f.toMaps()+" \n";
            }
            else{
                feat += f.toMaps();
            }
        }
        return  "<player>\n\t<name>"+getName() + "</name>\n\t<bestScore>" + getBestScore() + "</bestScore>\n\t"+feat+"\n</player>";
    }

    /**
     * Retourne une ArrayList contenant les exploits qui ne sont pas encore realise pour ce profil
     * @return une ArrayList contenant les exploits qui ne sont pas encore realise pour ce profil
     */
    public ArrayList<Feat> proposeFeats() {
        ArrayList<Feat> propose = HandleFeat.feats();
        for(int j = 0; j<propose.size();j++){
            for(Feat f :getFeats()){
                if(propose.get(j).getName().equals(f.getName())){
                    propose.add(j,f);
                    propose.remove(j+1);
                }
            }
            if(propose.get(j).isRealised())
                propose.remove(j);
        }
        return propose;
    }

    /**
     * toString()
     * @return toString()
     */
    public String toString() {
        int k=0;
        for(Feat t: getFeats()){
            if(t.isRealised())
                k+=1;
        }
        return  getName() + "  (Best score : " + getBestScore() + ", Feats  : " + k+")" ;
    }
}
