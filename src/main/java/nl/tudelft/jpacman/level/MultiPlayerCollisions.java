package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;

/**
 * Manage collisions for a multi-players game.
 * @author Julien Delplanque
 */
public class MultiPlayerCollisions extends PlayerCollisions {
    @Override
    public void collide(Unit mover, Unit collidedOn) {
        super.collide(mover, collidedOn);
        if (mover instanceof Hunter) {
            hunterColliding((Hunter) mover, collidedOn);
        }
    }

    private void hunterColliding(Hunter hunter, Unit collidedOn) {
        if (collidedOn instanceof Hunter) {
            hunterVersusHunter(hunter, (Hunter) collidedOn);
        }
    }

    private void hunterVersusHunter(Hunter hunter1, Hunter hunter2) {
        if(hunter1.isHunting() && hunter2.isAlive())
            hunter1.kill(hunter2);

        if(hunter2.isHunting() && hunter1.isAlive())
            hunter2.kill(hunter1);
    }

    @Override
    public void playerVersusPellet(Player player, Pellet pellet) {
        if(!((Hunter) player).isHunting())
            super.playerVersusPellet(player, pellet);
    }
}
