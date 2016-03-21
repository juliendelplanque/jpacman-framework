package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.EatableGhost;
import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * A simple implementation of a collision map for the JPacman player.
 * Whit support of collision with super pellet and eating Ghost.
 *
 * @author Maximilien Charlier (maximilien.charlier@student.umons.ac.be).
 */
public class PlayerCollisionsSuperPellet extends PlayerCollisions{

    private LevelSuperPellet level;

    /**
     * Collision between two unit.
     *
     * @param mover a mover: ghost or player.
     * @param collidedOn a ghost, a pellet, a player or a super pellet.
     */
    @Override
    public void collide(Unit mover, Unit collidedOn) {
        if (mover instanceof Player) {
            playerColliding((Player) mover, collidedOn);
        }
        else if (mover instanceof Ghost) {
            ghostColliding((EatableGhost) mover, collidedOn);
        }
    }

    /**
     * Collision between two unit.
     *
     * @param player a player.
     * @param collidedOn a ghost, a pellet, a player or a super pellet.
     */
    @Override
    protected void playerColliding(Player player, Unit collidedOn) {
        if (collidedOn instanceof Ghost) {
            playerVersusGhost(player, (EatableGhost) collidedOn);
        }

        if (collidedOn instanceof SuperPellet) {
            playerVersusSuperPellet(player, (SuperPellet) collidedOn);
        }
        else if (collidedOn instanceof Pellet) {
            playerVersusPellet(player, (Pellet) collidedOn);
        }
    }

    /**
     * Collision between two unit.
     *
     * @param ghost  a ghost.
     * @param collidedOn a ghost, a pellet, a player or a super pellet.
     */
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
        level.startPacManHunterMode(player);
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
            player.addPoints(player.getGhostHeatedScore());
            player.addHeatedGhost();
            level.addRespawnGhost(ghost);
        }
    }
}
