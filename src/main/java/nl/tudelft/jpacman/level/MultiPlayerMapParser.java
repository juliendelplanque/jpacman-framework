package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;

import java.util.List;

/**
 * The parser for multi-players map.
 * @author Julien Delplanque
 */
public class MultiPlayerMapParser extends MapParser {
    /**
     * Creates a new map parser.
     *
     * @param levelFactory The factory providing the NPC objects and the level.
     * @param boardFactory The factory providing the board.
     */
    public MultiPlayerMapParser(LevelFactory levelFactory, BoardFactory boardFactory) {
        super(levelFactory, boardFactory);
    }

    protected Square makeGhostSquare(List<NPC> ghosts) {
        Square ghostSquare = boardCreator.createGround();
        NPC ghost = levelCreator.createHunter();
        ghosts.add(ghost);
        ghost.occupy(ghostSquare);
        return ghostSquare;
    }
}
