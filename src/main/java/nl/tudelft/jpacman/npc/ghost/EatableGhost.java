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
	 * The timer use for automatically re-aad ghost in the map.
	 */
	private Timer respawn;

	/**
	 * When game is resume,
	 * Time in milli second remaining before the respawn of ghost.
	 */
	private int respawnResume;

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
	 * Re aad ghost after 5seconds in is initial position.
	 */
	public void respawnGhost(){
		respawnGhost(5000);
	}

	/**
	 * Re add ghost in is initial position.
	 * @param millisecond time before is re add.
	 */
	private void respawnGhost(int millisecond){
		respawn = new Timer();
		respawn.schedule(new TimerTask() {
			@Override
			public void run() {
				occupy(initialPosition);
				setModeHunt();

			}
		}, millisecond);
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
