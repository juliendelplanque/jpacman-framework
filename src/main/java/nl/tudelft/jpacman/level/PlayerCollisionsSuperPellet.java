package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.EatableGhost;
import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * A simple implementation of a collision map for the JPacman player.
 * Whit support of collision with super pellet and eating Ghost.
 *
 * Created by Maximilien Charlier.
 */
public class PlayerCollisionsSuperPellet extends PlayerCollisions{

    private LevelSuperPellet level;

    @Override
    public void collide(Unit mover, Unit collidedOn) {

        if (mover instanceof Player) {
            playerColliding((Player) mover, collidedOn);
        }
        else if (mover instanceof Ghost) {
            ghostColliding((EatableGhost) mover, collidedOn);
        }
    }

    @Override
    protected void playerColliding(Player player, Unit collidedOn) {
        if (collidedOn instanceof Ghost) {
            playerVersusGhost(player, (EatableGhost) collidedOn);
        }

        if (collidedOn instanceof Pellet) {
            playerVersusPellet(player, (Pellet) collidedOn);
        }

        if (collidedOn instanceof SuperPellet) {
            playerVersusSuperPellet(player, (SuperPellet) collidedOn);
        }
    }

    private void ghostColliding(EatableGhost ghost, Unit collidedOn) {
        if (collidedOn instanceof Player) {
            playerVersusGhost((Player) collidedOn, ghost);
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
        player.setHunterMode();
        level.startPacmanHunterMode(player);
        //add timer pour quitter le mode flee
    }


    /**
     * Actual case of player bumping into ghost or vice versa.
     *
     * @param player The player involved in the collision.
     * @param ghost The ghost involved in the collision.
     */
    public void playerVersusGhost(Player player, EatableGhost ghost) {
        if(ghost.isModeHunt())
            player.setAlive(false);
        else if(ghost.isModeFlee()){
            ghost.leaveSquare();
            player.addHeatedGhost();
            player.addPoints(player.getGhostHeatedScore());
            ghost.respawnGhost();
            //todo re-aad on the map after 5seconde
        }
    }
}
