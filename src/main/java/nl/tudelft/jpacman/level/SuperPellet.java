package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.sprite.Sprite;

/**
 * Created by Maximilien on 3/03/16.
 */
public class SuperPellet extends Pellet {
    /**
     * The default value of a super-pellet.
     */
    private static final int VALUE = 50;

    /**
     * Creates a new super-pellet.
     * @param sprite The sprite of this super-pellet.
     */
    public SuperPellet(Sprite sprite){
        super(VALUE, sprite);
    }

}
