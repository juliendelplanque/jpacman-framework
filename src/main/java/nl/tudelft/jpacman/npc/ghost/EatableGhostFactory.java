package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.sprite.PacManSprites;
/**
 * Factory that creates eatable ghosts.
 *
 * Created by Maximilien Charlier on 10/03/16.
 */
public class EatableGhostFactory extends GhostFactory {

    /**
     * Creates a new ghost factory.
     * Set the Ghost Flee Sprite to the class Ghost.
     *
     * @param spriteStore The sprite provider.
     */
    public EatableGhostFactory(PacManSprites spriteStore) {
        super(spriteStore);
        EatableGhost.setFleeSprite(spriteStore.getGhostFleeSprite());
    }

    /**
     * Creates a new Blinky / Shadow, the red Ghost.
     *
     * @see Blinky
     * @return A new Blinky.
     */
    public EatableGhost createBlinky() {
        return new Blinky(getSprites().getGhostSprite(GhostColor.RED));
    }

    /**
     * Creates a new Pinky / Speedy, the pink Ghost.
     *
     * @see Pinky
     * @return A new Pinky.
     */
    public EatableGhost createPinky() {
        return new Pinky(getSprites().getGhostSprite(GhostColor.PINK));
    }

    /**
     * Creates a new Inky / Bashful, the cyan Ghost.
     *
     * @see Inky
     * @return A new Inky.
     */
    public EatableGhost createInky() {
        return new Inky(getSprites().getGhostSprite(GhostColor.CYAN));
    }

    /**
     * Creates a new Clyde / Pokey, the orange Ghost.
     *
     * @see Clyde
     * @return A new Clyde.
     */
    public EatableGhost createClyde() {
        return new Clyde(getSprites().getGhostSprite(GhostColor.ORANGE));
    }
}
