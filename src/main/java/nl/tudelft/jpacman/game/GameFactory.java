package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Hunter;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.GhostColor;

import java.util.ArrayList;

/**
 * Factory that provides Game objects.
 * 
 * @author Jeroen Roosen 
 */
public class GameFactory {

	/**
	 * The factory providing the player objects.
	 */
	private final PlayerFactory playerFact;

	/**
	 * Creates a new game factory.
	 * 
	 * @param playerFactory
	 *            The factory providing the player objects.
	 */
	public GameFactory(PlayerFactory playerFactory) {
		this.playerFact = playerFactory;
	}

	/**
	 * Creates a game for a single level with one player.
	 * 
	 * @param level
	 *            The level to create a game for.
	 * @return A new single player game.
	 */
	public Game createSinglePlayerGame(Level level) {
		return new SinglePlayerGame(playerFact.createPacMan(), level);
	}

	/**
	 * Returns the player factory associated with this game factory.
	 * @return the player factory associated with this game factory.
	 */
	protected PlayerFactory getPlayerFactory() {
		return playerFact;
	}

	/**
	 * Returns a game for a single level multi-player without Pacman game.
	 *
     * @param level - The level to create a fame for.
	 * @return A new multi-player without Pacman game.
     */
	public Game createMultiPlayerWithoutPacmanGame(int numberOfPlayers, Level level){
        ArrayList<Player> players = new ArrayList<>();
        switch (numberOfPlayers) {
            case 4: players.add(playerFact.createGhost(GhostColor.RED));
            case 3: players.add(playerFact.createGhost(GhostColor.CYAN));
            case 2:
                players.add(playerFact.createGhost(GhostColor.ORANGE));
                players.add(playerFact.createGhost(GhostColor.PINK));
                break;
            default:
                throw new NotEnoughPlayersException();
        }
        return new MultiplayerWithoutPacmanGame(players, level); }
}
