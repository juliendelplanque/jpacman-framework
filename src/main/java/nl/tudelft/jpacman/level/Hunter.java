package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;

import java.util.ArrayList;
import java.util.Map;

/**
 * Special player to play a multi-players game.
 * @author Julien Delplanque
 */
public class Hunter extends Player {
    /**
     * The score to add/remove to the player who kill/is killed.
     */
    public static final int EAT_HUNTER_SCORE = 50;
    private final Map<Direction, Sprite> huntingSpriteMap;
    private Square initialPosition;
    private boolean isHunting;
    private ArrayList<RespawnListener> respawnListeners;

    /**
     * Creates a new hunter with a score of 0 points. After instantiation,
     * it is not hunting directly.
     *
     * @param spriteMap
     *            A map containing a sprite for this player for every direction.
     * @param deathAnimation
     *            The sprite to be shown when this player dies.
     */
    Hunter(Map<Direction, Sprite> spriteMap, Map<Direction, Sprite> huntingSpriteMap, AnimatedSprite deathAnimation){
        super(spriteMap, deathAnimation);
        this.isHunting = false;
        this.huntingSpriteMap = huntingSpriteMap;
        this.respawnListeners = new ArrayList<>();
        this.initialPosition = this.getSquare();
    }

    @Override
    public Sprite getSprite(){
        if (this.isAlive() && this.isHunting()){
            return huntingSpriteMap.get(getDirection());
        }
        return super.getSprite();
    }

    public Square getInitialPosition() {
        return initialPosition;
    }

    /**
     * Override to set the initial position if it is not already set.
     * @param target
     *          The position to occupy.
     */
    @Override
    public void occupy(Square target) {
        super.occupy(target);
        if(this.initialPosition == null)
            this.initialPosition = target;
    }

    /**
     * Returns true if the hunter is hunting, else return false.
     * @return true if the hunter is hunting, else return false.
     */
    public boolean isHunting() {
        return isHunting;
    }

    /**
     * Make the hunter hunt or not.
     * @param hunting
     *          Make the hunter hunt if true, else the hunter can
     *          be killed.
     */
    public void setHunting(boolean hunting) {
        isHunting = hunting;
    }

    /**
     * Add a RespawnListener to the RespawnListeners list.
     * @param respawnListener
     *          The object implementing {@link RespawnListener} to add.
     */
    public void addRespawnListener(RespawnListener respawnListener){
        this.respawnListeners.add(respawnListener);
    }

    /**
     * Inform RespawnListeners that this Hunter need to respawn.
     */
    private void informRespawnListeners(){
        for(RespawnListener listener : this.respawnListeners)
            listener.hunterNeedRespawn(this);
    }

    /**
     * Respawn myself at the initial position.
     */
    public void respawn() {
        this.leaveSquare();
        this.occupy(this.getInitialPosition());
        this.setAlive(true);
    }

    /**
     * Kills this hunter and sends {@link NeedRespawnEvent} to RespawnListeners.
     */
    public void kill(){
        this.setAlive(false);
        this.informRespawnListeners();
    }

    /**
     * Kill the other Hunter in parameter.
     * @param toKill
     *          The hunter to kill.
     */
    public void kill(Hunter toKill) {
        toKill.kill();
        toKill.loosePoints(EAT_HUNTER_SCORE);
        this.addPoints(EAT_HUNTER_SCORE);
    }

    /**
     * Make the hunter loose points given as parameter.
     * If the number of points to loose is greater than the number
     * points it has, set its score to 0.
     * @param points
     *          The number of points to loose.
     */
    public void loosePoints(int points){
        if(this.getScore() >= points)
            this.addPoints(-points);
        else
            this.addPoints(-this.getScore());
    }

    /**
     * Event telling the hunter need to respawn.
     * @author Julien Delplanque
     */
    public class NeedRespawnEvent {
        /**
         * The hunter needing to respawn.
         */
        private final Hunter hunter;

        /**
         * Constructor of the event for the hunter in parameter
         * @param hunter
         *          The hunter who needs to respawn.
         */
        public NeedRespawnEvent(Hunter hunter){
            this.hunter = hunter;
        }

        /**
         * Accessor for the hunter who need to respawn.
         * @return The hunter.
         */
        public Hunter getHunter() {
            return hunter;
        }
    }
}
