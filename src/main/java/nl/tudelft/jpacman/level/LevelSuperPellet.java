package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.Ghost;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Maximilien Charlier on 4/03/16.
 */
public class LevelSuperPellet extends Level {

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
    }


    /**
     * Start flee mode
     */
    public void startFlee() {
        synchronized (startStopLock) {
            for (Map.Entry<NPC, ScheduledExecutorService> e : this.getNpcs().entrySet()) {
                e.getValue().shutdownNow(); //stop running ghost
                ((Ghost) e.getKey()).setModeFlee();
                //todo changer sprint fantomes
            }

        }
    }
}
