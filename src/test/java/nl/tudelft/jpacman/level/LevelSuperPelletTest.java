package nl.tudelft.jpacman.level;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.EatableGhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

/**
 * Tests various aspects of Super Pellet level.
 *
 * @author Maximilien Charlier (maximilien.charlier@student.umons.ac.be).
 */
public class LevelSuperPelletTest {

    /**
     * The level under test.
     */
    private LevelSuperPellet level;

    /**
     * An NPC on this level.
     */
    private final NPC ghost = mock(NPC.class);

    /**
     * Starting position 1.
     */
    private final Square square1 = mock(Square.class);

    /**
     * Starting position 2.
     */
    private final Square square2 = mock(Square.class);

    /**
     * The board for this level.
     */
    private final Board board = mock(Board.class);

    /**
     * The collision map.
     */
    private final CollisionMap collisions = mock(CollisionMap.class);

    /**
     * Sets up the level with the default board, a single NPC and a starting
     * square.
     */
    @Before
    public void setUp() {
        final long defaultInterval = 100L;
        List<NPC> ghosts = new ArrayList<>();
        EatableGhostFactory factory = new EatableGhostFactory(new PacManSprites());
        ghosts.add(factory.createBlinky());
        ghosts.add(factory.createClyde());
        ghosts.add(factory.createInky());
        ghosts.add(factory.createPinky());
        level = new LevelSuperPellet(board, ghosts, Lists.newArrayList(
                square1, square2), collisions);
        when(ghost.getInterval()).thenReturn(defaultInterval);
    }

    /**
     * Validates the state of the level when it isn't started yet.
     */
    @Test
    public void noStart() {
        assertFalse(level.isInProgress());
    }

    /**
     * Validates the state of the level when it is stopped without starting.
     */
    @Test
    public void stop() {
        level.stop();
        assertFalse(level.isInProgress());
    }

    /**
     * Validates the state of the level when it is started.
     */
    @Test
    public void start() {
        level.start();
        assertTrue(level.isInProgress());
    }

    /**
     * Validates the state of the level when it is started then stopped.
     */
    @Test
    public void startStop() {
        level.start();
        level.stop();
        assertFalse(level.isInProgress());
    }
}