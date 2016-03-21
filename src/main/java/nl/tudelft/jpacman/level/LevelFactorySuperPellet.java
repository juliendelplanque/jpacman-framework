package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;

import java.util.List;

/**
 * Factory that creates levels and units.
 *
 * @author Maximilien Charlier (maximilien.charlier@student.umons.ac.be).
 */
public class LevelFactorySuperPellet extends LevelFactory {

    /**
     * Creates a new level factory with superPellet.
     *
     * @param spriteStore
     *            The sprite store providing the sprites for units.
     * @param ghostFactory
     *            The factory providing ghosts.
     */
    public LevelFactorySuperPellet(PacManSprites spriteStore, GhostFactory ghostFactory) {
        super(spriteStore, ghostFactory);
    }


    /**
     * Creates a new level from the provided data.
     *
     * @param board
     *            The board with all ghosts and pellets occupying their squares.
     * @param ghosts
     *            A list of all ghosts on the board.
     * @param startPositions
     *            A list of squares from which players may start the game.
     * @return A new level for the board.
     */
    public Level createLevel(Board board, List<NPC> ghosts,
                             List<Square> startPositions) {

        // We'll adopt the simple collision map for now.

        PlayerCollisionsSuperPellet collisionMap = new PlayerCollisionsSuperPellet();

        LevelSuperPellet level = new LevelSuperPellet(board, ghosts, startPositions, collisionMap);
        collisionMap.addLevel(level);
        return level;
    }
}
