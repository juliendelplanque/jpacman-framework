package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;

import java.util.ArrayList;
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

    /**
     * Parses the text representation of the board into an actual level.
     *
     * <ul>
     * <li>Supported characters:
     * <li>' ' (space) an empty square.
     * <li>'#' (bracket) a wall.
     * <li>'.' (period) a square with a pellet.
     * <li>'P' (capital P) a starting square for players.
     * <li>'G' (capital G) a square with a ghost.
     * <li>'*' (star) a square with a superPellet.
     * </ul>
     *
     * @param map
     *            The text representation of the board, with map[x][y]
     *            representing the square at position x,y.
     * @return The level as represented by this text.
     */
    @Override
    public Level parseMap(char[][] map) {
        return parseMap(map);
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
