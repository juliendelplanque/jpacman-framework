package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Square;

/**
 * Interface common to all Hunters.
 * @author Julien Delplanque
 */
public interface Hunter {
    /**
     * Returns true if the hunterPlayer is hunting, else return false.
     * @return true if the hunterPlayer is hunting, else return false.
     */
    boolean isHunting();

    /**
     * Make the hunterPlayer hunt or not.
     * @param hunting
     *          Make the hunterPlayer hunt if true, else the hunterPlayer can
     *          be killed.
     */
    void setHunting(boolean hunting);

    /**
     * Add a RespawnListener to the RespawnListeners list.
     * @param respawnListener
     *          The object implementing {@link RespawnListener} to add.
     */
    void addRespawnListener(RespawnListener respawnListener);

    /**
     * Inform RespawnListeners that this HunterPlayer need to respawn.
     */
    void informRespawnListeners();

    /**
     * Respawn myself at the initial position.
     */
    void respawn();

    /**
     * Kills this hunterPlayer and tell RespawnListeners.
     */
    void kill();

    /**
     * Kill the other HunterPlayer in parameter.
     * @param toKill
     *          The hunterPlayer to kill.
     */
    void kill(Hunter toKill);

    /**
     * Make the hunterPlayer loose points given as parameter.
     * If the number of points to loose is greater than the number
     * points it has, set its score to 0.
     * @param points
     *          The number of points to loose.
     */
    void loosePoints(int points);

    /**
     * Determinate if the hunter is alive or not.
     * @return true if it is alive else false.
     */
    boolean isAlive();

    /**
     * Returns the initial position of the hunter as set in the map.
     * @return A square that is the initial position of the ghost.
     */
    Square getInitialPosition();
}
