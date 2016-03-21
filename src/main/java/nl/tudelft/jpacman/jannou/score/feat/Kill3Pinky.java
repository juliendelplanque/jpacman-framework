package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.npc.ghost.Pinky;

/**
 * Kill3Pinky
 */
public class Kill3Pinky extends  Feat {

    public Kill3Pinky(){
        setName("Kill3Pinky");
        setValue(100);
        setDesc("Kill 3 Pinky : "+getValue()+" bonus points ");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toMaps(){
        return  "<feat>\n\t<name>"+getName() + "</name>\n\t<description>" + getDesc() +  "</description>\n\t<value>" + getValue() +
                "</value>\n\t<realised>" +isRealised() +"</realised>\n\t<state>"+getState()+"</state>\n\t</feat>" ;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean condition(int i, Ghost g){
        if(g instanceof Pinky){
            setState(getState()+1);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatestate() {
        if(getState() == 3)
            setRealised(true);
    }
}