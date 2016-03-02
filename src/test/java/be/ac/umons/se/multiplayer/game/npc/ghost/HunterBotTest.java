package be.ac.umons.se.multiplayer.game.npc.ghost;

import nl.tudelft.jpacman.MultiPlayerLauncher;
import nl.tudelft.jpacman.level.HunterPlayer;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.level.RespawnListener;
import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.npc.ghost.HunterBot;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for HunterBot object.
 */
public class HunterBotTest {
    private static final PacManSprites SPRITE_STORE = new PacManSprites();
    private GhostFactory ghostFactory;
    private HunterBot hunterBot;

    @Before
    public void setUp(){
        this.ghostFactory = new GhostFactory(SPRITE_STORE);
        this.hunterBot = (HunterBot)this.ghostFactory.createHunterBot();
    }

    @Test
    public void getInitialPositionTest(){
        MultiPlayerLauncher launcher = new MultiPlayerLauncher();
        Level level = launcher.makeMultiPlayerLevel("/boardmulti2.txt");
        assertEquals(this.hunterBot.getInitialPosition(), null);
        // We set the square to occupy for the first time
        this.hunterBot.occupy(level.getBoard().squareAt(1,1));
        assertEquals(this.hunterBot.getSquare(), level.getBoard().squareAt(1,1));
        assertEquals(this.hunterBot.getInitialPosition(), level.getBoard().squareAt(1,1));

        // We set the square to occupy for the second time, the initial position does not change.
        this.hunterBot.occupy(level.getBoard().squareAt(1,2));
        assertEquals(this.hunterBot.getSquare(), level.getBoard().squareAt(1,2));
        assertEquals(this.hunterBot.getInitialPosition(), level.getBoard().squareAt(1,1));
    }

    @Test
    public void isHuntingSetHuntingTest(){
        assertFalse(this.hunterBot.isHunting());
        this.hunterBot.setHunting(true);
        assertTrue(this.hunterBot.isHunting());
        this.hunterBot.setHunting(false);
        assertFalse(this.hunterBot.isHunting());
    }

    @Test
    public void informRespawnListenersTest(){
        assertFalse(this.hunterBot.isHunting());
        // Make the hunter hunt, so we can verify that this method was called.
        RespawnListener respawnListener = hunter -> hunter.setHunting(true);
        this.hunterBot.addRespawnListener(respawnListener);
        this.hunterBot.informRespawnListeners();
        assertTrue(this.hunterBot.isHunting());
    }

    @Test
    public void respawnTest(){
        MultiPlayerLauncher launcher = new MultiPlayerLauncher();
        Level level = launcher.makeMultiPlayerLevel("/boardmulti2.txt");
        // Set the initial position
        this.hunterBot.occupy(level.getBoard().squareAt(1,1));
        // Move it away from initial position
        this.hunterBot.occupy(level.getBoard().squareAt(1,3));
        assertEquals(this.hunterBot.getSquare(), level.getBoard().squareAt(1,3));
        this.hunterBot.kill();
        this.hunterBot.respawn();
        assertTrue(this.hunterBot.isAlive());
        assertEquals(this.hunterBot.getSquare(), this.hunterBot.getInitialPosition());
    }

    @Test
    public void killTest(){
        assertFalse(this.hunterBot.isHunting());
        // Make the hunter hunt, so we can verify that this method was called.
        RespawnListener respawnListener = hunter -> hunter.setHunting(true);
        this.hunterBot.addRespawnListener(respawnListener);
        assertTrue(this.hunterBot.isAlive());
        this.hunterBot.kill();
        assertTrue(this.hunterBot.isHunting());
        assertFalse(this.hunterBot.isAlive());
    }

    @Test
    public void killOtherTest(){
        HunterBot other = (HunterBot)this.ghostFactory.createHunterBot();
        assertTrue(other.isAlive());
        this.hunterBot.kill(other);
        assertFalse(other.isAlive());
    }
}
