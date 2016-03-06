package nl.tudelft.jpacman.jannou.profil;
import nl.tudelft.jpacman.jannou.score.feat.Feat;

import java.util.ArrayList;

/**
 * object to represent a player's profil
 * Created by Jannou on 5/03/16.
 */
public class Profil {
    private final String name;
    private int bestScore  = 0;
    private ArrayList<Feat> feats = new ArrayList<Feat>();

    public Profil(String _name){
        name = _name;
    }

    public String getName(){
        return name;
    }

    public int getBestScore(){
        return bestScore;
    }

    public ArrayList<Feat> getFeats(){
        return feats ;
    }

    public void setBestScore(int score){
        bestScore = score;
    }

    public void addFeat(Feat feat){
        feats.add(feat);
    }

    public String toMaps() {
        String featsName = "";
        for(Feat f :getFeats()){
            if(f != getFeats().get(getFeats().size()-1))
                featsName += (f+ " & ");
            else{
                featsName += f;
            }
        }
        return  "<player>\n\t<name> : "+getName() + " </name>\n\t<bestScore> : " + getBestScore() + " </bestScore>\n\t<feats> : " + featsName +" </feats>\n</player>" ;
    }

    public String toString() {
        return  "Name : "+getName() + " bestScore : " + getBestScore() + " number or feats  : " + getFeats().size() ;
    }

}
