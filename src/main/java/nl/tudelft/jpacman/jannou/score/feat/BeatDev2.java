package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * BeatDev2
 * Created by Jannou on 6/03/16.
 */
public class BeatDev2 extends  Feat {

    public BeatDev2(){
        setName("BeatDev2");
        setValue(100);
        setDesc("Beat Dev2's score : "+getValue()+" bonus points ");
    }
    public String toMaps(){
        return  "<feat>\n\t<name>"+getName() + "</name>\n\t<description>" + getDesc() +  "</description>\n\t<value>" + getValue() +
                "</value>\n\t<realised>" +isRealised() +"</realised>\n\t<state>"+getState()+"</state>\n\t</feat>" ;
    }
    public boolean condition(int i, Ghost g){
        if(i>=2000){
            setRealised(true);
            return true;
        }
        return false;
    }

    @Override
    public void updatestate() {
    }
}
