package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;

import java.util.List;

/**
 * Created by neosw on 3/03/16.
 */
public class MapParserSuperPellet extends MapParser {

    private int nbSuperPellet = 0;
    /**
     * Creates a new map parser.
     *
     * @param levelFactory
     *            The factory providing the NPC objects and the level.
     * @param boardFactory
     *            The factory providing the Square objects and the board.
     */
    public MapParserSuperPellet(LevelFactory levelFactory, BoardFactory boardFactory) {
        super(levelFactory,boardFactory);
    }

    @Override
    protected void addSquare(Square[][] grid, List<NPC> ghosts,
                             List<Square> startPositions, int x, int y, char c) {
        switch (c) {
            case ' ':
                grid[x][y] = boardCreator.createGround();
                break;
            case '#':
                grid[x][y] = boardCreator.createWall();
                break;
            case '.':
                Square pelletSquare = boardCreator.createGround();
                grid[x][y] = pelletSquare;
                levelCreator.createPellet().occupy(pelletSquare);
                break;
            case 'G':
                Square ghostSquare = makeGhostSquare(ghosts);
                grid[x][y] = ghostSquare;
                break;
            case 'P':
                Square playerSquare = boardCreator.createGround();
                grid[x][y] = playerSquare;
                startPositions.add(playerSquare);
                break;
            case '*':
                Square superPelletSquare = boardCreator.createGround();
                grid[x][y] = superPelletSquare;
                levelCreator.createSuperPellet().occupy(superPelletSquare);
                nbSuperPellet++;
                break;
            default:
                throw new PacmanConfigurationException("Invalid character at "
                        + x + "," + y + ": " + c);
        }
    }
}
