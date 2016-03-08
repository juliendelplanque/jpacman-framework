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
public abstract class Ghost extends NPC {

	/**
	 * The sprite map of flee gost, one sprite for each direction.
	 */
	private static  Map<Direction, Sprite> spritesFlee;


	/**
	 * Define if the ghost hunt pacman or flee pacman.
	 */
	private static boolean MODE_FLEE = true;
	private static boolean MODE_HUNT = false;
	private boolean mode;

	/**
	 * The default score value of a ghost.
	 */
	private static final int SCORE = 200;

	/**
	 * The sprite map, one sprite for each direction.
	 */
	private Map<Direction, Sprite> sprites;

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
	protected Ghost(Map<Direction, Sprite> spriteMap) {
		this.sprites = spriteMap;
		mode = false;
	}

	@Override
	public Sprite getSprite() {
		if(mode == MODE_HUNT)
            return sprites.get(getDirection());
        // else mode = MODE_FLEE
        return spritesFlee.get(getDirection());
	}

	/**
	 * Determines a possible move in a random direction.
	 * 
	 * @return A direction in which the ghost can move, or <code>null</code> if
	 *         the ghost is shut in by inaccessible squares.
	 */
	protected Direction randomMove() {
		Square square = getSquare();
		List<Direction> directions = new ArrayList<>();
		for (Direction d : Direction.values()) {
			if (square.getSquareAt(d).isAccessibleTo(this)) {
				directions.add(d);
			}
		}
		if (directions.isEmpty()) {
			return null;
		}
		int i = new Random().nextInt(directions.size());
		return directions.get(i);
	}

	 public static void setFleeSprite(Map<Direction, Sprite> spriteMap){
		spritesFlee = spriteMap;
	}

    /**
     * Set the ghost for hunt PacMan.
     */
    public void setModeHunt(){
        this.mode = MODE_HUNT;
    }

    /**
     * Set the ghost for flee PacMan.
     */
    public void setModeFlee(){
        this.mode = MODE_FLEE;
    }

    /**
     * @return if the Ghost flee Pacman.
     */
    public boolean isModeFlee(){
        return this.mode == MODE_FLEE;
    }

    /**
     * @return if the Ghost hunt Pacman.
     */
    public boolean isModeHunt(){
        return this.mode == MODE_HUNT;
    }

    /**
     *
     * @return the default value for the score when a ghost are eated.
     */
    public static int getScore() {
        return SCORE;
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
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				occupy(initialPosition);
				setModeHunt();
			}
		}, 5000);
	}
}
