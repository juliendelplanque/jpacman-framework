package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * Eat5Cherry
 */
public class Eat5Cherry extends  Feat {

    public Eat5Cherry(){
        setName("Eat5Cherry");
        setValue(100);
        setDesc("Eat 5 Cherry : "+getValue()+" bonus points ");
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
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatestate() {
    }
}