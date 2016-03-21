package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.EatableGhost;
import nl.tudelft.jpacman.npc.ghost.Ghost;

import java.util.List;

/**
 * Creates new {@link Level}s from text representations.
 *
 * @author Maximilien Charlier (maximilien.charlier@student.umons.ac.be).
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
        Level l = super.parseMap(map);
        if(!checkSuperPellet()){
            throw new PacmanConfigurationException("Unable to create correct level.\n" +
                    Integer.toString(getNbSuperPellet())+ " super pellet\n" +
                    "Exepted: 0 or 4.");
        }
        return l;
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

                /* Set initial position of Ghost */
                for (Unit g: ghostSquare.getOccupants()){
                    if( g instanceof Ghost){
                        ((EatableGhost) g).setInitialPosition(ghostSquare);
                    }
                }
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

    /**
     * @return if there are 0 or 4 Super Pellet.
     */
    public boolean checkSuperPellet(){
        return nbSuperPellet == 0 | nbSuperPellet == 4;
    }

    /**
     * @return the number of Super Pellet in the board.
     */
    public int getNbSuperPellet(){
        return nbSuperPellet;
    }
}
