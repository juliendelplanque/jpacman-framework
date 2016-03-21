package nl.tudelft.jpacman;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.EatableGhost;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Smoke test launching the full game,
 * and attempting to make a number of typical moves.
 *
 * This is <strong>not</strong> a <em>unit</em> test -- it is an end-to-end test
 * trying to execute a large portion of the system's behavior directly from the
 * user interface. It uses the actual sprites and monster AI, and hence
 * has little control over what is happening in the game.
 *
 * Because it is an end-to-end test, it is somewhat longer
 * and has more assert statements than what would be good
 * for a small and focused <em>unit</em> test.
 *
 * @author Maximilien Charlier, March 2016.
 */
@SuppressWarnings("magicnumber")
public class LauncherSmokeSuperPelletTest extends LauncherSmokeTest{
    private LauncherSuperPellet launcher;
    private Game game;
    private Player player;
    /**
     * Launch the user interface.
     */
    @Before
    @Override
    public void setUpPacman() {
        launcher = new LauncherSuperPellet();
        launcher.launch();
    }

    /**
     * Quit the user interface when we're done.
     */
    @After
    @Override
    public void tearDown() {
        //launcher.dispose();
    }

    /**
     * Launch the game, and imitate what would happen in a typical game.
     * The test is only a smoke test, and not a focused small test.
     * Therefore it is OK that the method is a bit too long.
     * Test: score when you eating pellet, super pellet and ghost
     * Test ghost mode
     * Test eating ghost
     *
     * @throws InterruptedException Since we're sleeping in this test.
     */
    @SuppressWarnings("methodlength")
    @Test
    public void smokeTest() throws InterruptedException {
        game = launcher.getGame();
        player = game.getPlayers().get(0);
        int score = 0;

        // start cleanly.
        assertFalse(game.isInProgress());
        game.start();
        assertTrue(game.isInProgress());
        assertEquals(score, player.getScore());

        /**
         * Test score added by eating pellet.
         **/
        // get points
        score +=10;
        game.move(player, Direction.EAST);
        assertEquals(score, player.getScore());

        // now moving back does not change the score
        game.move(player, Direction.WEST);
        assertEquals(score, player.getScore());

        // Move until the superPellet
        move(game, Direction.EAST, 7);
        score += 50;
        assertEquals(score, player.getScore());
        move(game, Direction.SOUTH, 6);
        score += 20;
        assertEquals(score, player.getScore());
        move(game, Direction.EAST, 2);
        score += 20;
        assertEquals(score, player.getScore());
        move(game, Direction.NORTH, 3);
        score += 20;
        assertEquals(score, player.getScore());

        /**
         * Test mode of ghost before and after eating superPellet
         * test Score superPellet
         **/
        for (Map.Entry<NPC, ScheduledExecutorService> e : game.getLevel().getNpcs().entrySet()) {
            assertTrue(((EatableGhost) e.getKey()).isModeHunt());
            assertFalse(((EatableGhost) e.getKey()).isModeFlee());
        }
        //eat the superPellet
        game.move(player, Direction.EAST);
        score += 50;
        assertEquals(score, player.getScore());

        for (Map.Entry<NPC, ScheduledExecutorService> e : game.getLevel().getNpcs().entrySet()) {
            assertFalse(((EatableGhost) e.getKey()).isModeHunt());
            assertTrue(((EatableGhost) e.getKey()).isModeFlee());
        }
        /**
         * PacMan hunt ghost and eating ghost > test score and eating mode
         */

        game.move(player, Direction.EAST);
        move(game, Direction.NORTH, 3);
        move(game, Direction.WEST, 6);
        move(game, Direction.NORTH, 6);
        score += 90;
        assertEquals(score, player.getScore());
        move(game, Direction.WEST, 4);
        //eating first ghost
        score += 200;
        assertEquals(score, player.getScore());

        //eating second ghost
        move(game, Direction.SOUTH, 2);
        score+= 400;
        assertEquals(score, player.getScore());

        //eating third ghost
        move(game, Direction.WEST, 2);
        score+= 800;
        assertEquals(score, player.getScore());

        //eating fourth ghost
        move(game, Direction.EAST, 5);
        score+= 1600;
        assertEquals(score, player.getScore());
    }


    /**
     * Launch the game, and imitate what would happen in a typical game.
     * The test is only a smoke test, and not a focused small test.
     * Therefore it is OK that the method is a bit too long.
     *
     * Test superpellet effect, ghost eated respawn, ghost sprite, ghost ia.
     * @throws InterruptedException Since we're sleeping in this test.
     */
    @SuppressWarnings("methodlength")
    @Test
    public void smokeTestTimer() throws InterruptedException {
        game = launcher.getGame();
        player = game.getPlayers().get(0);
        int score = 0;

        // start cleanly.
        assertFalse(game.isInProgress());
        game.start();
        assertTrue(game.isInProgress());
        assertEquals(score, player.getScore());

        game.move(player, Direction.EAST);
        game.move(player, Direction.WEST);

        // Move until the superPellet
        move(game, Direction.EAST, 7);
        move(game, Direction.SOUTH, 6);
        move(game, Direction.EAST, 2);
        move(game, Direction.NORTH, 3);
        game.move(player, Direction.EAST); //you have eating a super pellet

        // Move to a ghost : Inky and eat him
        game.move(player, Direction.EAST);
        move(game, Direction.NORTH, 3);
        move(game, Direction.WEST, 6);
        move(game, Direction.NORTH, 6);
        move(game, Direction.WEST, 4);

        //go to corner at the bottom left
        move(game, Direction.WEST, 6);
        move(game, Direction.SOUTH, 6);
        move(game, Direction.WEST, 2);
        move(game, Direction.SOUTH, 4);
        move(game, Direction.WEST, 5);
        move(game, Direction.SOUTH, 2);
        // Sleeping in tests is generally a bad idea.
        // Here we do it just to let the monsters move.
        Thread.sleep(5000L);
        game.stop(); //inki is respawn
        Thread.sleep(4000L); //test timer pausable
        game.start();
        Thread.sleep(2000L); //hunter mode expiration.
        game.stop();
        Thread.sleep(2000L); //check if ghost is in hunter mode
        game.start();
        Thread.sleep(2000L); //test timer pausable
    }

}