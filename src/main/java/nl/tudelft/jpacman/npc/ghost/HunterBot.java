package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Hunter;
import nl.tudelft.jpacman.level.HunterPlayer;
import nl.tudelft.jpacman.level.RespawnListener;
import nl.tudelft.jpacman.sprite.Sprite;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * A bot that can play a multi-player game.
 * @author Julien Delplanque
 */
public class HunterBot extends Ghost implements Hunter {
    /**
     * The variation in intervals, this makes the ghosts look more dynamic and
     * less predictable.
     */
    private static final int INTERVAL_VARIATION = 50;

    /**
     * The base movement interval.
     */
    private static final int MOVE_INTERVAL = 250;
    private final Map<Direction, Sprite> huntingSpriteMap;

    private boolean isAlive;
    private Square initialPosition;
    private boolean isHunting;
    private ArrayList<RespawnListener> respawnListeners;

    /**
     * Creates a new ghost.
     *
     * @param spriteMap The sprites for every direction.
     */
    protected HunterBot(Map<Direction, Sprite> spriteMap,  Map<Direction, Sprite> huntingSpriteMap) {
        super(spriteMap);
        this.setAlive(true);
        this.setHunting(false);
        this.huntingSpriteMap = huntingSpriteMap;
        this.respawnListeners = new ArrayList<>();
    }

    @Override
    public Sprite getSprite(){
        if (this.isAlive() && this.isHunting()){
            return huntingSpriteMap.get(getDirection());
        }
        return super.getSprite();
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
    public long getInterval() {
        return MOVE_INTERVAL + new Random().nextInt(INTERVAL_VARIATION);
    }

    @Override
    public Direction nextMove() {
        return randomMove();
    }

    @Override
    public boolean isHunting() {
        return this.isHunting;
    }

    @Override
    public void setHunting(boolean hunting) {
        this.isHunting = hunting;
    }

    @Override
    public void addRespawnListener(RespawnListener respawnListener) {
        this.respawnListeners.add(respawnListener);
    }

    @Override
    public void informRespawnListeners() {
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
    public void kill() {
        this.setAlive(false);
        this.informRespawnListeners();
    }

    @Override
    public void kill(Hunter toKill) {
        toKill.kill();
        toKill.loosePoints(HunterPlayer.EAT_HUNTER_SCORE);
    }

    @Override
    public void loosePoints(int points) {
        // A bot does not have points, do nothing.
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Square getInitialPosition(){
        return this.initialPosition;
    }
}
