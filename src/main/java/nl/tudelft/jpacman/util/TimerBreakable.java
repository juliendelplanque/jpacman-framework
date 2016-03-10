package nl.tudelft.jpacman.util;

import java.util.*;

/**
 * Timer with pause and start option.
 *
 * Created by Maximilien Charlier on 10/03/16.
 */
public class TimerBreakable {
    private Timer t;
    private long timerEnd, timeRemaining;
    private TimerTaskCloneable timerTask;
    private boolean inPause;


    public TimerBreakable(TimerTaskCloneable task){
        t = new Timer();
        timerTask = task;
        inPause = false;
    }

    public void schedule(long delay){
        t = new Timer();
        TimerTaskCloneable timerTaskClone = null;
        try {
            timerTaskClone = timerTask.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        t.schedule(timerTaskClone, delay);
        timerEnd = System.currentTimeMillis() + delay;
    }

    public void cancel(){
        t.cancel();
        timerTask = null;
    }

    /**
     * Pause the timer and save the time left before is expiration.
     */
    public void pause(){
        if(!inPause & timerTask != null) {
            t.cancel();
            t.purge();
            timeRemaining = timerEnd - System.currentTimeMillis();
            inPause = true;
        }
    }

    /**
     * Restart the timer with the task and the time left before is expiration
     */
    public void resume(){
        if(inPause & timerTask != null){
            if(timeRemaining > 0) {
                schedule(timeRemaining);
            }
            inPause = false;
        }
    }


}

