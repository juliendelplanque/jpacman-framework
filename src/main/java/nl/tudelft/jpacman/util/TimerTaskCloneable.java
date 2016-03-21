package nl.tudelft.jpacman.util;

import java.util.TimerTask;

public abstract class TimerTaskCloneable extends TimerTask implements Cloneable{

    public TimerTaskCloneable(){
        super();
    }

    public TimerTaskCloneable clone() throws CloneNotSupportedException {
        return (TimerTaskCloneable) super.clone();
    }

}
