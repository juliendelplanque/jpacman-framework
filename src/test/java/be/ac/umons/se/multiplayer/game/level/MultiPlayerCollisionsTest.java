package be.ac.umons.se.multiplayer.game.level;

import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.npc.ghost.HunterBot;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for MultiPlayerCollisions object.
 */
public class MultiPlayerCollisionsTest {
    private static final PacManSprites SPRITE_STORE = new PacManSprites();
    private MultiPlayerCollisions multiplayerCollisions;
    private HunterPlayer hunter1, hunter2;
    private PlayerFactory playerFactory;
    private GhostFactory ghostFactory;
    private LevelFactory levelFactory;
    private HunterBot hunterBot1, hunterBot2;

    @Before
    public void setUp() {
        this.playerFactory = new PlayerFactory(SPRITE_STORE);
        this.ghostFactory = new GhostFactory(SPRITE_STORE);
        this.levelFactory = new LevelFactory(SPRITE_STORE, this.ghostFactory);
        this.multiplayerCollisions = new MultiPlayerCollisions();
        // We create two hunters with a score of 100
        this.hunter1 = (HunterPlayer)playerFactory.createGhost(GhostColor.ORANGE);
        this.hunter1.addPoints(100);
        this.hunter2 = (HunterPlayer)playerFactory.createGhost(GhostColor.RED);
        this.hunter2.addPoints(100);
        // We create two hunter bots with
        this.hunterBot1 = (HunterBot)ghostFactory.createHunterBot();
        this.hunterBot2 = (HunterBot)ghostFactory.createHunterBot();
    }

    @Test
    public void collideHuntingPlayerVsHunterPlayerTest(){
        this.hunter1.setHunting(true);
        assertTrue(this.hunter1.isHunting());
        assertFalse(this.hunter2.isHunting());
        this.multiplayerCollisions.collide(this.hunter1, this.hunter2);

        assertEquals(this.hunter1.getScore(), 150);
        assertEquals(this.hunter2.getScore(), 50);
        assertTrue(this.hunter1.isAlive());
        assertFalse(this.hunter2.isAlive());

        this.hunter2.setAlive(true);
        this.multiplayerCollisions.collide(this.hunter1, this.hunter2);
        assertEquals(this.hunter1.getScore(), 200);
        assertEquals(this.hunter2.getScore(), 0);
        assertTrue(this.hunter1.isAlive());
        assertFalse(this.hunter2.isAlive());

        this.hunter2.setAlive(true);
        this.multiplayerCollisions.collide(this.hunter1, this.hunter2);
        assertEquals(this.hunter1.getScore(), 250);
        assertEquals(this.hunter2.getScore(), 0);
        assertTrue(this.hunter1.isAlive());
        assertFalse(this.hunter2.isAlive());
    }

    @Test
    public void collideHunterPlayerVsHuntingPlayer(){
        this.hunter1.setHunting(true);
        assertTrue(this.hunter1.isHunting());
        assertFalse(this.hunter2.isHunting());
        this.multiplayerCollisions.collide(this.hunter2, this.hunter1);

        assertEquals(this.hunter1.getScore(), 150);
        assertEquals(this.hunter2.getScore(), 50);
        assertTrue(this.hunter1.isAlive());
        assertFalse(this.hunter2.isAlive());

        this.hunter2.setAlive(true);
        this.multiplayerCollisions.collide(this.hunter2, this.hunter1);
        assertEquals(this.hunter1.getScore(), 200);
        assertEquals(this.hunter2.getScore(), 0);
        assertTrue(this.hunter1.isAlive());
        assertFalse(this.hunter2.isAlive());

        this.hunter2.setAlive(true);
        this.multiplayerCollisions.collide(this.hunter2, this.hunter1);
        assertEquals(this.hunter1.getScore(), 250);
        assertEquals(this.hunter2.getScore(), 0);
        assertTrue(this.hunter1.isAlive());
        assertFalse(this.hunter2.isAlive());
    }

    @Test
    public void collideHuntingBotVsHunterBot(){
        this.hunterBot1.setHunting(true);
        assertTrue(this.hunterBot1.isHunting());
        assertFalse(this.hunterBot2.isHunting());
        this.multiplayerCollisions.collide(this.hunterBot2, this.hunterBot1);
        assertTrue(this.hunterBot1.isAlive());
        assertFalse(this.hunterBot2.isAlive());
    }

    @Test
    public void collideHunterBotVsHuntingBot(){
        this.hunterBot1.setHunting(true);
        assertTrue(this.hunterBot1.isHunting());
        assertFalse(this.hunterBot2.isHunting());
        this.multiplayerCollisions.collide(this.hunterBot1, this.hunterBot2);
        assertTrue(this.hunterBot1.isAlive());
        assertTrue(this.hunterBot1.isHunting());
        assertFalse(this.hunterBot2.isAlive());
        assertFalse(this.hunterBot2.isHunting());
    }

    @Test
    public void collideHuntingPlayerVsHunterBot(){
        this.hunter1.setHunting(true);
        assertTrue(this.hunter1.isHunting());
        assertFalse(this.hunterBot1.isHunting());
        int oldHunterScore = this.hunter1.getScore();
        this.multiplayerCollisions.collide(this.hunter1, this.hunterBot1);
        assertTrue(this.hunter1.isAlive());
        assertTrue(this.hunter1.isHunting());
        assertEquals(this.hunter1.getScore(), oldHunterScore + HunterPlayer.EAT_HUNTER_SCORE);
        assertFalse(this.hunterBot1.isAlive());
        assertFalse(this.hunterBot1.isHunting());
    }

    @Test
    public void collideHunterPlayerVsHuntingBot(){
        this.hunterBot1.setHunting(true);
        assertTrue(this.hunterBot1.isHunting());
        assertTrue(this.hunterBot1.isAlive());
        assertFalse(this.hunter1.isHunting());
        assertTrue(this.hunter1.isAlive());
        this.multiplayerCollisions.collide(this.hunter1, this.hunterBot1);
        assertTrue(this.hunterBot1.isAlive());
        assertTrue(this.hunterBot1.isHunting());
        assertFalse(this.hunter1.isAlive());
        assertFalse(this.hunter1.isHunting());
    }

    @Test
    public void collideHunterPlayerVsPellet(){
        Pellet pellet = this.levelFactory.createPellet();
        assertFalse(this.hunter1.isHunting());
        assertTrue(this.hunter1.isAlive());
        int oldHunterScore = this.hunter1.getScore();
        this.multiplayerCollisions.collide(this.hunter1, pellet);
        assertFalse(this.hunter1.isHunting());
        assertTrue(this.hunter1.isAlive());
        assertEquals(this.hunter1.getScore(), oldHunterScore+pellet.getValue());
        assertNull(pellet.getSquare());
    }

    @Test
    public void collideHuntingPlayerVsPellet(){
        Pellet pellet = this.levelFactory.createPellet();
        this.hunter1.setHunting(true);
        assertTrue(this.hunter1.isHunting());
        assertTrue(this.hunter1.isAlive());
        int oldHunterScore = this.hunter1.getScore();
        this.multiplayerCollisions.collide(this.hunter1, pellet);
        assertTrue(this.hunter1.isAlive());
        assertTrue(this.hunter1.isHunting());
        assertEquals(this.hunter1.getScore(), oldHunterScore);
        assertNull(pellet.getSquare());
    }

    @Test
    public void collideHunterBotVsPellet(){
        Pellet pellet = this.levelFactory.createPellet();
        assertFalse(this.hunterBot1.isHunting());
        assertTrue(this.hunterBot1.isAlive());
        this.multiplayerCollisions.collide(this.hunterBot1, pellet);
        assertFalse(this.hunterBot1.isHunting());
        assertTrue(this.hunterBot1.isAlive());
        assertNull(pellet.getSquare());
    }

    @Test
    public void collideHuntingBotVsPellet(){
        Pellet pellet = this.levelFactory.createPellet();
        this.hunterBot1.setHunting(true);
        assertTrue(this.hunterBot1.isHunting());
        assertTrue(this.hunterBot1.isAlive());
        this.multiplayerCollisions.collide(this.hunterBot1, pellet);
        assertTrue(this.hunterBot1.isHunting());
        assertTrue(this.hunterBot1.isAlive());
        assertNull(pellet.getSquare());
    }
}