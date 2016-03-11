package nl.tudelft.jpacman.jannou.score.feat;

/**
 * Created by Jannou on 10/03/16.
 */
public class Eat5Melon extends  Feat {

    public Eat5Melon(){
        setName("Eat5Melon");
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