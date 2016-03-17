package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * BeatDev3
 */
public class BeatDev3 extends  Feat {

    public BeatDev3(){
        setName("BeatDev3");
        setValue(100);
        setDesc("Beat Dev3's score : "+getValue()+" bonus points ");
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
    public boolean condition(final int i, final Ghost g){
        return i>=2000;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void updatestate() {
        setRealised(true);
    }
}
