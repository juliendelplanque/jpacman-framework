package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A super pellet, one of the little dots Pac-Man has to collect.
 * This dots gives PacMan super power: pacman can eat ghost.
 * Ghost was scary and flee PacMan.
 *
 * @author Maximilien Charlier (maximilien.charlier@student.umons.ac.be).
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
