package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * Created by julien on 25/02/16.
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
        if(hunter1.isHunting())
            hunter2.setAlive(false);

        if(hunter2.isHunting())
            hunter1.setAlive(false);
    }
}
