package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * Eat5Fruits
 */
public class Eat5Fruits extends  Feat {
    /**
     * Construteur Eat5Fruits
     */
    public Eat5Fruits(){
        setName("Eat5Fruits");
        setValue(100);
        setDesc("Eat 5 Fruits : "+getValue()+" bonus points ");
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