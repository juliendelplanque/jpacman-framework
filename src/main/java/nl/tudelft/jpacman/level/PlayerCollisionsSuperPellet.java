package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * Created by Maximilien Charlier on 3/03/16.
 */
public class PlayerCollisionsSuperPellet extends PlayerCollisions{

    private LevelSuperPellet level;

    @Override
    protected void playerColliding(Player player, Unit collidedOn) {
        if (collidedOn instanceof Ghost) {
            playerVersusGhost(player, (Ghost) collidedOn);
        }

        if (collidedOn instanceof Pellet) {
            playerVersusPellet(player, (Pellet) collidedOn);
        }

        if (collidedOn instanceof SuperPellet) {
            playerVersusSuperPellet(player, (SuperPellet) collidedOn);
        }
    }

    /**
     * Add level l in the player collision to add support of ghost IA and scheduler
     * @param l a level
     */
    public void addLevel(LevelSuperPellet l){
        assert(l != null);
        this.level = l;
    }

    /**
     * Actual case of player consuming a superPellet.
     *
     * @param player The player involved in the collision.
     * @param superPellet The superPellet involved in the collision.
     */
    public void playerVersusSuperPellet(Player player, SuperPellet superPellet) {
        superPellet.leaveSquare();
        player.addPoints(superPellet.getValue());
        //todo changer le mode de jeu
        level.startFlee();
        // todo deux classes une en mode fuite et l'autre en mode normal
    }
}
