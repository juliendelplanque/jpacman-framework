package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.npc.ghost.HunterBot;

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
        if (collidedOn instanceof Hunter)
            hunterVersusHunter(hunter, (Hunter) collidedOn);
        else if (collidedOn instanceof Pellet)
            hunterVersusPellet(hunter, (Pellet) collidedOn);
    }

    private void hunterVersusHunter(Hunter hunter1, Hunter hunter2) {
        if (hunter1.isHunting() && hunter2.isAlive())
            hunter1.kill(hunter2);
        else if (hunter2.isHunting() && hunter1.isAlive())
            hunter2.kill(hunter1);
    }

    private void hunterVersusPellet(Hunter hunter, Pellet pellet) {
        if(!hunter.isHunting()) {
            pellet.leaveSquare();
            if (hunter instanceof HunterPlayer) {
                ((HunterPlayer) hunter).addPoints(pellet.getValue());
            }
        }
    }

    @Override
    public void playerVersusPellet(Player player, Pellet pellet) {
        // This is managed by hunterVersusPellet()
    }

    @Override
    public void playerVersusGhost(Player player, Ghost ghost) {
        // Do nothing, only HunterPlayers and HunterBots should be managed.
    }
}