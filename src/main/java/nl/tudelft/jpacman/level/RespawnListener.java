package nl.tudelft.jpacman.level;

/**
 * Interface for object who wants to liten to {@link Hunter.NeedRespawnEvent}.
 */
public interface RespawnListener {
    /**
     * Method called when a hunter needs to respawn.
     * @param hunter
     *          The hunter who needs to respawn.
     */
    void hunterNeedRespawn(Hunter hunter);
}
