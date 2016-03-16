package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.npc.ghost.Inky;

/**
 * Created by Jannou on 10/03/16.
 */
public class Kill3Inky extends  Feat {

    public Kill3Inky(){
        setName("Kill3Inky");
        setValue(100);
        setDesc("Kill 3 Inky : "+getValue()+" bonus points ");
    }
    public String toMaps(){
        return  "<feat>\n\t<name>"+getName() + "</name>\n\t<description>" + getDesc() +  "</description>\n\t<value>" + getValue() +
                "</value>\n\t<realised>" +isRealised() +"</realised>\n\t\t<state>"+getState()+"</state>\n\t</feat>" ;
    }
    public boolean condition(int i, Ghost g){
        if(g instanceof Inky){
            setState(getState()+1);
            return true;
        }
        return false;
    }

    @Override
    public void updatestate() {
        if(getState() == 3)
            setRealised(true);
    }
}