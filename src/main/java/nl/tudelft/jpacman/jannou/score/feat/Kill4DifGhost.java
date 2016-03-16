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
        if (g instanceof Blinky)
            return up(1);
        if (g instanceof Clyde)
            return up(3);
        if (g instanceof Inky)
            return up(5);
        return g instanceof Pinky && up(11);
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
        if(i ==11){
            if(getState()<9)
                setState(getState()+11);
        }
        if(i ==5) {
            if (getState() ==15)
                setState(getState() + 5);
            if (getState() < 5)
                setState(getState() + 5);
            if (getState() == 12)
                setState(getState() + 5);
            if (getState() == 11)
                setState(getState() + 5);
            if (getState() == 14)
                setState(getState() + 5);
        }
        if(i ==3) {
            if (getState() ==17)
                setState(getState() + 3);
            if (getState() ==16)
                setState(getState() + 3);
            if (getState() == 12)
                setState(getState() + 3);
            if (getState() ==11)
                setState(getState() + 3);
            if (getState() == 6)
                setState(getState() + 3);
            if (getState() <3)
                setState(getState() + 3);
        }
        if(i ==1) {
            if (getState() ==0)
                setState(getState() + 1);
            if (getState() ==11)
                setState(getState() + 1);
            if (getState() ==8)
                setState(getState() + 1);
            if (getState() == 14)
                setState(getState() + 1);
            if (getState() == 16)
                setState(getState() + 1);
            if (getState() == 19)
                setState(getState() + 1);
        }
        return true;
    }
}