package nl.tudelft.jpacman.jannou.score.feat;

/**
 * Created by Jannou on 6/03/16.
 */
public class Eat5Cherry extends  Feat {

    public Eat5Cherry(){
        setName("Eat5Cherry");
        setValue(100);
        setDesc("beat Dev1's score");
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