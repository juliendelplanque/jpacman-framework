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

    @Override
    public boolean isHunting() {
        return isHunting;
    }

    @Override
    public void setHunting(boolean hunting) {
        isHunting = hunting;
    }

    @Override
    public void addRespawnListener(RespawnListener respawnListener){
        this.respawnListeners.add(respawnListener);
    }

    @Override
    public void informRespawnListeners(){
        for(RespawnListener listener : this.respawnListeners)
            listener.hunterNeedRespawn(this);
    }

    @Override
    public void respawn() {
        this.leaveSquare();
        this.occupy(this.getInitialPosition());
        this.setAlive(true);
    }

    @Override
    public void kill(){
        this.setAlive(false);
        this.informRespawnListeners();
    }

    @Override
    public void kill(Hunter toKill) {
        toKill.kill();
        toKill.loosePoints(EAT_HUNTER_SCORE);
        this.addPoints(EAT_HUNTER_SCORE);
    }

    public void loosePoints(int points){
        if(this.getScore() >= points)
            this.addPoints(-points);
        else
            this.addPoints(-this.getScore());
    }
}
