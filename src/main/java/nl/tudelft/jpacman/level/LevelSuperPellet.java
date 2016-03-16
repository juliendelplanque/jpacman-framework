package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.EatableGhost;
import nl.tudelft.jpacman.util.TimerBreakable;
import nl.tudelft.jpacman.util.TimerTaskCloneable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * A level of Pac-Man. A level consists of the board with the players and the
 * AIs on it.
 *
 * Ghost can be eated for this reason a timer by ghost is necessary for his respawn
 * same thing for the PacMan hunter mode.
 *
 * @author Maximilien Charlier (maximilien.charlier@student.umons.ac.be).
 */
public class LevelSuperPellet extends Level {

    /**
     * The lock that ensures starting and stopping flee mode can't interfere with each
     * other.
     */
    private final Object startStopLock = new Object();

    /**
     * Using for exiting PacMan Hunter mode.
     */
    private TimerBreakable hunterMode;

    /**
     * The ghost of this level and, if they are running, their timer with here respawn.
     */
    private Map<EatableGhost, TimerBreakable> ghostRespawn;

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
        hunterMode = null;
        ghostRespawn = new HashMap<>();
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
            if(hunterMode != null) {
                hunterMode.cancel();
            }
            hunterMode = new TimerBreakable(new TimerTaskCloneable() {
                @Override
                public void run() {
                    stopPacManHunterMode();
                }
            });
            hunterMode.schedule(player.getTimeHunterMode());

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
        hunterMode.cancel();
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
        if(!isInProgress() && hunterMode != null){
            hunterMode.resume();
            for (Map.Entry<EatableGhost, TimerBreakable> k : ghostRespawn.entrySet()){
                k.getValue().resume();
            }
        }
        if(!isInProgress())
            startNPCs();
        super.start();
    }

    /**
     * Stops or pauses this level, no longer allowing any movement on the board
     * and stopping all NPCs.
     */
    @Override
    public void stop() {
        if(isInProgress() && hunterMode != null){
            hunterMode.pause();
            for (Map.Entry<EatableGhost, TimerBreakable> k : ghostRespawn.entrySet()){
                k.getValue().pause();
            }
        }
        if(isInProgress())
            stopNPCs();
        super.stop();
    }

    /**
     * Add a timer for respawning of death ghost.
     * @param g a death ghost.
     */
    public void addRespawnGhost(EatableGhost g){
        TimerBreakable t = new TimerBreakable(new TimerTaskCloneable() {
            @Override
            public void run() {
                g.respawn();
                /* stop and start is using for avoid sheduler ghost not start */
                stop();
                start();
            }
        });
        t.schedule(EatableGhost.RESPAWN_TIME);
        if(ghostRespawn.containsKey(g))
            ghostRespawn.replace(g, t);
        else
            ghostRespawn.put(g, t);
    }
}
