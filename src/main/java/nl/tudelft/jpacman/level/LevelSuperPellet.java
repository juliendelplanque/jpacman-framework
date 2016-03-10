package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.EatableGhost;
import nl.tudelft.jpacman.util.TimerBreakable;
import nl.tudelft.jpacman.util.TimerTaskCloneable;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Maximilien Charlier on 4/03/16.
 */
public class LevelSuperPellet extends Level {

    private TimerBreakable timerHunterMode;

    /**
     * The NPCs of this level and, if they are running, their schedules.
     */
    // private final Map<NPC, TimerBreakable> ghostRespawn;

    /**
     * The lock that ensures starting and stopping flee mode can't interfere with each
     * other.
     */
    private final Object startStopLock = new Object();

    /**
     * Creates a new level for the board.
     *
     * @param b
     *            The board for the level.
     * @param ghosts
     *            The ghosts on the board.
     * @param startPositions
     *            The squares on which players start on this board.
     * @param collisionMap
     *            The collection of collisions that should be handled.
     */
    public LevelSuperPellet(Board b, List<NPC> ghosts, List<Square> startPositions,
                 CollisionMap collisionMap) {
        super(b, ghosts, startPositions, collisionMap);
        timerHunterMode = null;
    }

    /**
     * Start PacMan Hunter Ghost mode.
     *          Ghost speed is divided by two
     *          Ghost can be heated by PacMan
     *          Ghost was blue
     *          Ghost move randomly
     */
    public void startPacManHunterMode(Player player) {
        synchronized (startStopLock) {
            /**
             * Entering in PacManHunterMode for 7 or 5 second.
             * Time depends of player.getTimeHunterMode()
             */
            if(timerHunterMode != null) {
                timerHunterMode.cancel();
            }
            timerHunterMode = new TimerBreakable(new TimerTaskCloneable() {
                @Override
                public void run() {
                    stopPacManHunterMode();
                }
            });
            timerHunterMode.schedule(player.getTimeHunterMode());

            /*
                Change ghost to fleeing ghost.
             */
            for (Map.Entry<NPC, ScheduledExecutorService> e : this.getNpcs().entrySet()) {
                ((EatableGhost) e.getKey()).setModeFlee();
                setSpeedNPCs(e.getKey(), ((EatableGhost) e.getKey()).getSpeed());
            }

        }
    }

    /**
     * Stop PacMan hunter mode
     *      Ghost speed reset to default
     *      Ghost can heat PacMan
     *      Ghost have the origin sprite
     */
    public void stopPacManHunterMode(){
        timerHunterMode.cancel();
        for (Map.Entry<NPC, ScheduledExecutorService> e : this.getNpcs().entrySet()) {
            ((EatableGhost) e.getKey()).setModeHunt();
            setSpeedNPCs(e.getKey(), ((EatableGhost) e.getKey()).getSpeed());
        }
    }

    /**
     * Starts or resumes this level, allowing movement and (re)starting the
     * NPCs.
     */
    @Override
    public void start() {
        if(!isInProgress() && timerHunterMode != null){
            timerHunterMode.resume();
        }
        super.start();
    }

    /**
     * Stops or pauses this level, no longer allowing any movement on the board
     * and stopping all NPCs.
     */
    @Override
    public void stop() {
        if(isInProgress() && timerHunterMode != null){
            timerHunterMode.pause();
        }
        super.stop();
    }
}
