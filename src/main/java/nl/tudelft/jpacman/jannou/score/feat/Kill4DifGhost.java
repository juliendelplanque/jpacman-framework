package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.*;

/**
 * Kill4DifGhost
 */
public class Kill4DifGhost extends  Feat {

    public Kill4DifGhost(){
        setName("Kill4DifGhost");
        setValue(200);
        setDesc("Kill 4 Different Ghost : "+getValue()+" bonus points ");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toMaps(){
        return  "<feat>\n\t\t<name>"+getName() + "</name>\n\t\t<description>" + getDesc() +  "</description>\n\t\t<value>" + getValue() +
                "</value>\n\t\t<realised>" +isRealised() +"</realised>\n\t<state>"+getState()+"</state>\n\t</feat>" ;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean condition(int i, Ghost g) {
        if (g instanceof Clyde)
            return up(3);
        if (g instanceof Inky)
            return up(5);
        if (g instanceof Pinky)
            up(11);
        if (g instanceof Blinky)
            return up(1);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatestate() {
        if (getState() == 20)
            setRealised(true);
    }
    private boolean up(int i){
        if(i ==11 && getState()<10)
                setState(getState()+11);
        if(i ==5 &&  (getState() ==15 || getState() < 5 || getState() == 12 || getState() == 11 ||getState() == 14))
                setState(getState() + 5);
        if(i ==3 && (getState() ==17 || getState() ==16 || getState() == 12 || getState() ==11 || getState() == 6 || getState() <3))
                setState(getState() + 3);
        if(i ==1 && (getState() ==0 || getState() ==11 || getState() ==8 || getState() == 14 || getState() == 16 || getState() == 19)) {
            setState(getState() + 1);
        }
        return true;
    }
}