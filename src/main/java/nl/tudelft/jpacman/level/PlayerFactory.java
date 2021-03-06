package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Factory that creates Players.
 * 
 * @author Jeroen Roosen 
 */
public class PlayerFactory {

	/**
	 * The sprite store containing the Pac-Man sprites.
	 */
	private final PacManSprites sprites;

	/**
	 * Creates a new player factory.
	 * 
	 * @param spriteStore
	 *            The sprite store containing the Pac-Man sprites.
	 */
	public PlayerFactory(PacManSprites spriteStore) {
		this.sprites = spriteStore;
	}

	/**
	 * Creates a new player with the classic Pac-Man sprites.
	 * 
	 * @return A new player.
	 */
	public Player createPacMan() {
		return new Player(sprites.getPacmanSprites(),
				sprites.getPacManDeathAnimation());
	}

    /**
     * Creates a new player with a ghost skin.
     * @param color - The color of the ghost.
     * @return A new player.
     */
	public Player createGhost(GhostColor color) {
		return new HunterPlayer(sprites.getGhostSprite(color),
				sprites.getPacmanSprites(),
				sprites.getPacManDeathAnimation());
	}
}
