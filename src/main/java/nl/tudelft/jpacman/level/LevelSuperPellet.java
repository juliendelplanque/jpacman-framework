package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.Ghost;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Maximilien Charlier on 4/03/16.
 */
public class LevelSuperPellet extends Level {

    private Timer timerHunterMode;

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
     * Start Pacman Hunter Ghost mode.
     *          Ghost speed is divided by two
     *          Ghost can be heated by PacMan
     *          Ghost was blue
     *          Ghost move randomly
     */
    public void startPacmanHunterMode(Player player) {
        synchronized (startStopLock) {
            /**
             * Entering in PacmanHunterMode for 7 or 5 second.
             * Time depends of player.getTimeHunterMode()
             */
            if(timerHunterMode == null) {
                timerHunterMode = new Timer();
            }
            else {
                timerHunterMode.cancel();
            }
            timerHunterMode = new Timer();
            timerHunterMode.schedule(new TimerTask() {
                @Override
                public void run() {
                    stopPacManHunterMode();
                }
            }, player.getTimeHunterMode());

            /*
                Change ghost to fleeing ghost.
             */
            for (Map.Entry<NPC, ScheduledExecutorService> e : this.getNpcs().entrySet()) {
                changeSpeedNPCs(e.getKey(), 2);
                ((Ghost) e.getKey()).setModeFlee();
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
            changeSpeedNPCs(e.getKey(), 1);
            ((Ghost) e.getKey()).setModeHunt();
        }
    }
}
