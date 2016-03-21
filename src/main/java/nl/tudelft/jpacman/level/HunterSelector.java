package nl.tudelft.jpacman.level;

import java.util.List;
import java.util.TimerTask;

/**
 * A HunterSelector is managing who is the hunter, at each
 * time this task is performed, the hunter who is hunting change
 * forming a cycle with the hunters available.
 *
 * @author Julien Delplanque
 */
public class HunterSelector extends TimerTask {
    private int currentHunterIndex;
    private List<Hunter> hunters;

    /**
     * Creates an HunterSelector instance.
     * @param hunters
     *              The hunters to manage.
     */
    public HunterSelector(List<Hunter> hunters){
        this.hunters = hunters;
        this.currentHunterIndex = 0;
    }

    /**
     * Makes the next hunter be the current hunter and returns it.
     * @return The new current hunter.
     */
    public Hunter nextHunter(){
        this.currentHunterIndex++;
        this.currentHunterIndex %= this.hunters.size();
        return currentHunter();
    }

    /**
     * Returns the current hunter.
     * @return The current hunter.
     */
    public Hunter currentHunter(){
        return this.hunters.get(this.currentHunterIndex);
    }
    
    @Override
    public void run() {
        this.currentHunter().setHunting(false);
        this.nextHunter().setHunting(true);
    }
}
