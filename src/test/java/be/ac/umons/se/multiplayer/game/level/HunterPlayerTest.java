package be.ac.umons.se.multiplayer.game.level;

import nl.tudelft.jpacman.MultiPlayerLauncher;
import nl.tudelft.jpacman.level.HunterPlayer;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.level.RespawnListener;
import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for HunterPlayer object.
 */
public class HunterPlayerTest {
    private static final PacManSprites SPRITE_STORE = new PacManSprites();
    private PlayerFactory playerFactory;
    private HunterPlayer hunterPlayer;

    @Before
    public void setUp(){
        this.playerFactory = new PlayerFactory(SPRITE_STORE);
        this.hunterPlayer = (HunterPlayer)this.playerFactory.createGhost(GhostColor.ORANGE);
    }

    @Test
    public void getInitialPositionTest(){
        MultiPlayerLauncher launcher = new MultiPlayerLauncher();
        Level level = launcher.makeMultiPlayerLevel("/boardmulti2.txt");
        assertEquals(this.hunterPlayer.getInitialPosition(), null);
        // We set the square to occupy for the first time
        this.hunterPlayer.occupy(level.getBoard().squareAt(1,1));
        assertEquals(this.hunterPlayer.getSquare(), level.getBoard().squareAt(1,1));
        assertEquals(this.hunterPlayer.getInitialPosition(), level.getBoard().squareAt(1,1));

        // We set the square to occupy for the second time, the initial position does not change.
        this.hunterPlayer.occupy(level.getBoard().squareAt(1,2));
        assertEquals(this.hunterPlayer.getSquare(), level.getBoard().squareAt(1,2));
        assertEquals(this.hunterPlayer.getInitialPosition(), level.getBoard().squareAt(1,1));
    }

    @Test
    public void isHuntingSetHuntingTest(){
        assertFalse(this.hunterPlayer.isHunting());
        this.hunterPlayer.setHunting(true);
        assertTrue(this.hunterPlayer.isHunting());
        this.hunterPlayer.setHunting(false);
        assertFalse(this.hunterPlayer.isHunting());
    }

    @Test
    public void informRespawnListenersTest(){
        assertFalse(this.hunterPlayer.isHunting());
        // Make the hunter hunt, so we can verify that this method was called.
        RespawnListener respawnListener = hunter -> hunter.setHunting(true);
        this.hunterPlayer.addRespawnListener(respawnListener);
        this.hunterPlayer.informRespawnListeners();
        assertTrue(this.hunterPlayer.isHunting());
    }

    @Test
    public void respawnTest(){
        MultiPlayerLauncher launcher = new MultiPlayerLauncher();
        Level level = launcher.makeMultiPlayerLevel("/boardmulti2.txt");
        // Set the initial position
        this.hunterPlayer.occupy(level.getBoard().squareAt(1,1));
        // Move it away from initial position
        this.hunterPlayer.occupy(level.getBoard().squareAt(1,3));
        assertEquals(this.hunterPlayer.getSquare(), level.getBoard().squareAt(1,3));
        this.hunterPlayer.kill();
        this.hunterPlayer.respawn();
        assertTrue(this.hunterPlayer.isAlive());
        assertEquals(this.hunterPlayer.getSquare(), this.hunterPlayer.getInitialPosition());
    }

    @Test
    public void killTest(){
        assertFalse(this.hunterPlayer.isHunting());
        // Make the hunter hunt, so we can verify that this method was called.
        RespawnListener respawnListener = hunter -> hunter.setHunting(true);
        this.hunterPlayer.addRespawnListener(respawnListener);
        assertTrue(this.hunterPlayer.isAlive());
        this.hunterPlayer.kill();
        assertTrue(this.hunterPlayer.isHunting());
        assertFalse(this.hunterPlayer.isAlive());
    }

    @Test
    public void killOtherTest(){
        HunterPlayer other = (HunterPlayer)this.playerFactory.createGhost(GhostColor.PINK);
        assertEquals(other.getScore(), 0);
        assertTrue(other.isAlive());
        other.addPoints(500);
        this.hunterPlayer.kill(other);
        assertEquals(other.getScore(), 500-HunterPlayer.EAT_HUNTER_SCORE);
        assertFalse(other.isAlive());
        assertEquals(this.hunterPlayer.getScore(), HunterPlayer.EAT_HUNTER_SCORE);
    }

    @Test
    public void loosePointsTest(){
        assertEquals(this.hunterPlayer.getScore(), 0);
        this.hunterPlayer.addPoints(500);
        this.hunterPlayer.loosePoints(100);
        assertEquals(this.hunterPlayer.getScore(), 400);
    }
}
