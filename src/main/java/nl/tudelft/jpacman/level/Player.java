package nl.tudelft.jpacman.level;

import java.util.Map;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.EatableGhost;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;
import nl.tudelft.jpacman.jannou.profil.Profil;

/**
 * A player operated unit in our game.
 * 
 * @author Jeroen Roosen, Maximilien Charlier (maximilien.charlier@student.umons.ac.be).
 */
public class Player extends Unit {

	/**
	 * The amount of points accumulated by this player.
	 */
	private int score;

	/**
	 * The animations for every direction.
	 */
	private final Map<Direction, Sprite> sprites;

	/**
	 * The animation that is to be played when Pac-Man dies.
	 */
	private final AnimatedSprite deathSprite;

	/**
	 * <code>true</code> iff this player is alive.
	 */
	private boolean alive;

	/**
	 * Number of superPellet heated by pacman.
	 */
	private int superPelletHeated;

	/**
	 * Number of ghost heated by PacMan (in the last ghost hunter mode)
	 */
	private int ghostHeated;
    /*
	 * The profil associated to the player.
	 */
	private Profil profil;

	/**
	 * Creates a new player with a score of 0 points.
	 * 
	 * @param spriteMap
	 *            A map containing a sprite for this player for every direction.
	 * @param deathAnimation
	 *            The sprite to be shown when this player dies.
	 */
	Player(Map<Direction, Sprite> spriteMap, AnimatedSprite deathAnimation) {
		this.score = 0;
		this.alive = true;
		this.sprites = spriteMap;
		this.deathSprite = deathAnimation;
		deathSprite.setAnimating(false);
		this.setDefaultMode();
		this.superPelletHeated = 0;
	}

	/**
	 * Returns whether this player is alive or not.
	 * 
	 * @return <code>true</code> iff the player is alive.
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Sets whether this player is alive or not.
	 * 
	 * @param isAlive
	 *            <code>true</code> iff this player is alive.
	 */
	public void setAlive(boolean isAlive) {
		if (isAlive) {
			deathSprite.setAnimating(false);
		}
		if (!isAlive) {
			deathSprite.restart();
		}
		this.alive = isAlive;
	}

	/**
	 * Returns the amount of points accumulated by this player.
	 * 
	 * @return The amount of points accumulated by this player.
	 */
	public int getScore() {
		return score;
	}

	@Override
	public Sprite getSprite() {
		if (isAlive()) {
			return sprites.get(getDirection());
		}
		return deathSprite;
	}

	/**
	 * Adds points to the score of this player.
	 * 
	 * @param points
	 *            The amount of points to add to the points this player already
	 *            has.
	 */
	public void addPoints(int points) {
		score += points;
	}

	/**
	 * Defined that the game mode is standard.
	 * 		PacMan are hunted by ghosts and reset value of number ghost heated.
	 */
	public void setDefaultMode(){
		this.ghostHeated = 0;
	}

	/**
	 * Defined that the game mode is hunter.
	 * 		Ghosts are hunted by PacMan and reset value of number ghost heated.
	 */
	public void setHunterMode(){
		this.ghostHeated = 0;
		this.superPelletHeated += 1;
	}

	/**
	 *	For the first ghost hunted score is 200 points
	 *		for the second ghost, score is 400 points
	 *		for the third ghost, score is 800 points
	 *		for the fourth ghost eated, score is 1600 points.
	 * @return the score value for the ghost hunted.
     */
	public int getGhostHeatedScore(){
		if (ghostHeated > 4) {
			return 0;
		}
		return EatableGhost.SCORE * (int) Math.pow(2, ghostHeated);
	}

	/**
	 * Add one ghost in ghostHeated.
	 */
	public void addHeatedGhost(){
		this.ghostHeated += 1;
	}

	/**
	 * Depends of the number of super pellet Pacman have heated.
	 * 			For the first two time of HunterMode is 7 seconds,
	 * 			for the next two time of HunterMode is 5 seconds.
	 * @return Time of Pacman hunter mode in millisecond.
     */
	public int getTimeHunterMode(){
		if(superPelletHeated <=2)
			return 7000;
		else
			return 5000;
	}

    /*
	 * Set the profil associated to this player
	 * @param _profil the new profil associated to this player
     */
	public void setProfil(Profil _profil){
		profil = _profil;
	}

	/**
	 * Returns the profil of this player
	 * @return The profil of this player
     */
	public Profil getProfil(){
		return profil;
	}
}
