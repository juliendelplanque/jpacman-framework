package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * Eat5Apple
 */
public class Eat5Apple extends  Feat {
    /**
     * Constructeur Eat5Apple
     */
    public Eat5Apple(){
        setName("Eat5Apple");
        setValue(100);
        setDesc("Eat 5 Apple : "+getValue()+" bonus points ");
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
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatestate() {
    }
}
