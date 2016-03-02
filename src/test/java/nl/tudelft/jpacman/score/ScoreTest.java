package nl.tudelft.jpacman.score;

import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.*;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.score.Score;

import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



/**
 * Tests various aspects of score.
 *import static org.mockito.Mockito.mock;
 import static org.mockito.Mockito.times;
 import static org.mockito.Mockito.verify;
 import static org.mockito.Mockito.when;
 * Created by Jannou on 2/03/16.
 */
public class ScoreTest {
   // private final GameFactory gf = new GameFactory(new PlayerFactory(new PacManSprites()));
    //private final Level level = makeLevel();

    private Launcher launcher;

    /**
     * Launch the user interface.
     */
    @Before
    public void setUpPacman() {
        launcher = new Launcher();
        launcher.launch();
    }
    @After
    public void tearDown() {
        launcher.dispose();
    }


    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void test1(){
        Game game = launcher.getGame();
        Score score = Score.getInstance(game);
        assertEquals(score, Score.getInstance(game));

    }
}
