package be.ac.umons.se.multiplayer.game.level;

import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Unit tests for HunterSelector object.
 * @author Julien Delplanque
 */
public class HunterSelectorTest {
    private static final PacManSprites SPRITE_STORE = new PacManSprites();
    private HunterSelector hunterSelector;
    private PlayerFactory playerFactory;
    private ArrayList<Hunter> hunters;

    @Before
    public void setUp(){
        this.playerFactory = new PlayerFactory(SPRITE_STORE);
        this.hunters = new ArrayList<>();
        for(int i=0; i<4; i++)
            this.hunters.add((HunterPlayer)playerFactory.createGhost(GhostColor.ORANGE));
        this.hunterSelector = new HunterSelector(this.hunters);
    }

    @Test
    public void nextHunterTest(){
        // the first nextHunter() call will returns hunters.get(1)
        for(int i=0; i<5; i++)
            assertEquals(this.hunterSelector.nextHunter(), this.hunters.get((i+1)%4));
    }

    @Test
    public void currentHunterTest(){
        // the first currentHunter() call will returns hunters.get(0)
        for(int i=0; i<4; i++) {
            assertEquals(this.hunterSelector.currentHunter(), this.hunters.get(i % 4));
            this.hunterSelector.nextHunter();
        }
    }

    @Test
    public void runTest(){
        this.hunters.get(0).setHunting(true);
        assertTrue(this.hunterSelector.currentHunter().isHunting());
        assertEquals(this.hunterSelector.currentHunter(), this.hunters.get(0));
        assertFalse(this.hunters.get(1).isHunting());
        this.hunterSelector.run();
        assertFalse(this.hunters.get(0).isHunting());
        assertEquals(this.hunterSelector.currentHunter(), this.hunters.get(1));
        assertTrue(this.hunterSelector.currentHunter().isHunting());
    }
}
