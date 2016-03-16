package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * BeatDev3
 * Created by Jannou on 6/03/16.
 */
public class BeatDev3 extends  Feat {

    public BeatDev3(){
        setName("BeatDev3");
        setValue(100);
        setDesc("Beat Dev3's score : "+getValue()+" bonus points ");
    }
    public String toMaps(){
        return  "<feat>\n\t<name>"+getName() + "</name>\n\t<description>" + getDesc() +  "</description>\n\t<value>" + getValue() +
                "</value>\n\t<realised>" +isRealised() +"</realised>\n\t<state>"+getState()+"</state>\n\t</feat>" ;
    }
    public boolean condition(int i, Ghost g){
        if(i>=1770)
            return true;
        return false;
    }

    @Override
    public void updatestate() {
        setRealised(true);
    }
}
