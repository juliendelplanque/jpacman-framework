package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.*;

/**
 * Created by Jannou on 10/03/16.
 */
public class Kill4Ghost extends  Feat {

    public Kill4Ghost(){
        setName("Kill4Ghost");
        setValue(100);
        setDesc("Kill 4 Ghost : "+getValue()+" bonus points ");
    }
    public String toMaps(){
        return  "<feat>\n\t<name>"+getName() + "</name>\n\t<description>" + getDesc() +  "</description>\n\t<value>" + getValue() +
                "</value>\n\t<realised>" +isRealised() +"</realised>\n\t\t<state>"+getState()+"</state>\n\t</feat>" ;
    }
    public boolean condition(int i, Ghost g){
        if((g instanceof Blinky) || (g instanceof Clyde) || (g instanceof Inky) || (g instanceof Pinky)){
            setState(getState()+1);
            return true;
        }
        return false;
    }

    @Override
    public void updatestate() {
        if(getState() == 4)
            setRealised(true);
    }
}