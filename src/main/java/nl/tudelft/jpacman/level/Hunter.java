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
    private final Map<Direction, Sprite> huntingSpriteMap;
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
    }

    @Override
    public Sprite getSprite(){
        if (this.isAlive() && this.isHunting()){
            return huntingSpriteMap.get(getDirection());
        }
        return super.getSprite();
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
     * Override so it sends {@link NeedRespawnEvent} to RespawnListeners.
     * @param isAlive
     *          The boolean determinating if the Hunter is alive or not.
     */
    @Override
    public void setAlive(boolean isAlive){
        super.setAlive(isAlive);
        if (!isAlive)
            this.informRespawnListeners();
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
     * Respawn myself at the location specified.
     * @param newLocation
     *          The location to respawn on.
     */
    public void respawnAt(Square newLocation) {
        this.leaveSquare();
        this.occupy(newLocation);
        this.setAlive(true);
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
