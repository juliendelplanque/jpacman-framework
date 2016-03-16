package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * BeatDev1
 */
public class BeatDev1 extends  Feat {

    public BeatDev1(){
        setName("BeatDev1");
        setValue(100);
        setDesc("Beat Dev1's score : "+getValue()+" bonus points ");
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
        return i >= 1780;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatestate() {
        setRealised(true);
    }
}
