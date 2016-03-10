package nl.tudelft.jpacman.npc.ghost;

import java.util.*;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * An antagonist in the game of Pac-Man, a ghost.
 * 
 * @author Jeroen Roosen 
 */
public abstract class EatableGhost extends Ghost {

	/**
	 * The default score value of a ghost.
	 */
	public static final int SCORE = 200;

	/**
	 * Time in milli second before the respawn of a ghost.
	 */
	public static final long RESPAWN_TIME = 5000;

	/**
	 * The sprite map of flee ghost, one sprite for each direction.
	 */
	private static  Map<Direction, Sprite> spritesFlee;

	/**
	 * Define if the ghost hunt PacMan or flee PacMan.
	 */
	private GhostMode mode;

	/**
	 * The initial position of the ghost, util for is respawn.
	 */
	private Square initialPosition;

	/**
	 * Creates a new ghost.
	 *
	 * @param spriteMap
	 *            The sprites for every direction.
	 */
	protected EatableGhost(Map<Direction, Sprite> spriteMap) {
		super(spriteMap);
		mode = GhostMode.HUNT;
	}

	@Override
	public Sprite getSprite() {
		if(mode == GhostMode.HUNT)
            return super.getSprite();
        /* else mode == GhostMode.FLEE */
        return spritesFlee.get(getDirection());
	}

	/**
	 * Set the flee map of ghost.
	 * @param spriteMap the flee map of ghost.
     */
	 public static void setFleeSprite(Map<Direction, Sprite> spriteMap){
		spritesFlee = spriteMap;
	}

    /**
     * Set the ghost for hunt PacMan.
     */
    public void setModeHunt(){
        this.mode = GhostMode.HUNT;
    }

    /**
     * Set the ghost for flee PacMan.
     */
    public void setModeFlee(){
        this.mode = GhostMode.FLEE;
    }

    /**
     * @return if the Ghost flee Pacman.
     */
    public boolean isModeFlee(){
        return this.mode == GhostMode.FLEE;
    }

    /**
     * @return if the Ghost hunt Pacman.
     */
    public boolean isModeHunt(){
        return this.mode == GhostMode.HUNT;
    }

	/**
	 * Set the initial position of the ghost, util for is respawn.
	 * @param position initial position of the ghost.
     */
	public void setInitialPosition(Square position){
		this.initialPosition = position;
	}

	/**
	 * Re aad ghost in is initial position.
	 */
	public void respawn(){
		occupy(initialPosition);
		setModeHunt();
	}

	/**
	 *
	 * @return Speed of ghost
     */
	public float getSpeed(){
		if(isModeFlee())
			return 0.5f;
		return 1;
	}
}
