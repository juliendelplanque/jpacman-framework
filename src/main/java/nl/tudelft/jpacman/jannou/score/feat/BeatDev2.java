package nl.tudelft.jpacman.jannou.score.feat;

/**
 * BeatDev2
 * Created by Jannou on 6/03/16.
 */
public class BeatDev2 extends  Feat {

    public BeatDev2(){
        setName("BeatDev2");
        setValue(100);
        setDesc("beat Dev2's score");
        setRealised(true);
    }
    public String toMaps(){
        return  "<feat>\n\t<name>"+getName() + "</name>\n\t<description>" + getDesc() +  "</description>\n\t<value>" + getValue() +
                "</value>\n\t<realised>" +isRealised() +"<realised>\n</feat>" ;
    }
    public boolean condition(int i,int j){
        if(i>=1780)
            return true;
        return false;
    }

    @Override
    public void updatestate(int i) {
    }
}
