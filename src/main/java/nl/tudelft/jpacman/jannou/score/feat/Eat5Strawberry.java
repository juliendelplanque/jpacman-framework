package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * Eat5Strawberry
 * Created by Jannou on 10/03/16.
 */
public class Eat5Strawberry extends  Feat {

    public Eat5Strawberry(){
        setName("Eat5Strawberry");
        setValue(100);
        setDesc("Eat 5 Strawberry : "+getValue()+" bonus points ");
    }
    public String toMaps(){
        return  "<feat>\n\t<name>"+getName() + "</name>\n\t<description>" + getDesc() +  "</description>\n\t<value>" + getValue() +
                "</value>\n\t<realised>" +isRealised() +"</realised>\n\t<state>"+getState()+"</state>\n\t</feat>" ;
    }
    public boolean condition(int i, Ghost g){
        return false;
    }

    @Override
    public void updatestate() {
    }

}
