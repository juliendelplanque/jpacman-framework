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
public class HunterPlayer extends Player implements Hunter {
    /**
     * The score to add/remove to the player who kill/is killed.
     */
    public static final int EAT_HUNTER_SCORE = 50;
    private final Map<Direction, Sprite> huntingSpriteMap;
    private Square initialPosition;
    private boolean isHunting;
    private ArrayList<RespawnListener> respawnListeners;

    /**
     * Creates a new hunterPlayer with a score of 0 points. After instantiation,
     * it is not hunting directly.
     *
     * @param spriteMap
     *            A map containing a sprite for this player for every direction.
     * @param deathAnimation
     *            The sprite to be shown when this player dies.
     */
    HunterPlayer(Map<Direction, Sprite> spriteMap, Map<Direction, Sprite> huntingSpriteMap, AnimatedSprite deathAnimation){
        super(spriteMap, deathAnimation);
        this.setHunting(false);
        this.huntingSpriteMap = huntingSpriteMap;
        this.respawnListeners = new ArrayList<>();
    }

    /**
     * Override to change the sprites returned when hunting.
     * @return The sprite to use.
     */
    @Override
    public Sprite getSprite(){
        if (this.isAlive() && this.isHunting())
            return huntingSpriteMap.get(getDirection());
        return super.getSprite();
    }

    /**
     * Returns the position set at the creation of the HunterPlayer.
     * @return The initial position as a Square.
     */
    @Override
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
        // If initialPosition is nil, it means occupy() has never been called before.
        if(this.initialPosition == null)
            this.initialPosition = target;
    }

    /**
     * Returns true if hunting, else false.
     * @return True if hunting, else false.
     */
    @Override
    public boolean isHunting() {
        return isHunting;
    }

    /**
     * Set the status of the hunterPlayer, if true it hunts, else it is hunted.
     * @param hunting
     *          Make the hunterPlayer hunt if true, else the hunterPlayer can
     */
    @Override
    public void setHunting(boolean hunting) {
        isHunting = hunting;
    }

    /**
     * Add a RespawnListener. It will be notified when the HunterPlayer has been
     * killed and needs to respawn.
     * @param respawnListener
     */
    @Override
    public void addRespawnListener(RespawnListener respawnListener){
        this.respawnListeners.add(respawnListener);
    }

    /**
     * Inform RespawnListeners that the HunterPlayer needs to respawn.
     */
    @Override
    public void informRespawnListeners(){
        for(RespawnListener listener : this.respawnListeners)
            listener.hunterNeedRespawn(this);
    }

    /**
     * Respawn the HunterPlayer at its initial position.
     */
    @Override
    public void respawn() {
        this.leaveSquare();
        this.occupy(this.getInitialPosition());
        this.setAlive(true);
    }

    /**
     * Kill the HunterPlayer.
     */
    @Override
    public void kill(){
        this.setAlive(false);
        this.informRespawnListeners();
    }

    /**
     * Kill the Hunter given as parameter.
     * @param toKill
     *          The Hunter to kill.
     */
    @Override
    public void kill(Hunter toKill) {
        toKill.kill();
        toKill.loosePoints(EAT_HUNTER_SCORE);
        this.addPoints(EAT_HUNTER_SCORE);
    }

    /**
     * Remove the number of points given as parameter from the score
     * of this HunterPlayer.
     * @param points
     *          The number of points to remove.
     */
    @Override
    public void loosePoints(int points){
        if(this.getScore() >= points)
            this.addPoints(-points);
        else
            this.addPoints(-this.getScore());
    }
}
