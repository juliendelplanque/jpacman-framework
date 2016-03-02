package nl.tudelft.jpacman.score;

import nl.tudelft.jpacman.game.*;
import nl.tudelft.jpacman.Launcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



/**
 * Tests various aspects of score.
 *
 * Created by Jannou on 2/03/16.
 */
public class HandleScoreTest {
    private Launcher launcher;
    private Game game1;
    private Game game2;


    @Before
    public void setUpTest() {
        launcher = new Launcher();
        launcher.launch();
        game1 = launcher.getGame();
        game2 = launcher.getGame();
    }
    @After
    public void tearDown() {
        launcher.dispose();
        File f = new File("./src/main/resources/scores.txt");
        if(f.exists()){
            boolean succes = f.delete();
        }
    }

    /**
     * test the creation of HandleScore's instance
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void test1(){
        HandleScore handleScore1 = HandleScore.getInstance(game1);
        HandleScore handleScore2 = HandleScore.getInstance(game2);
        assertFalse(!handleScore1.equals(handleScore2));
        assertTrue(handleScore1.equals(HandleScore.getInstance(game1)));
        assertEquals(handleScore1, HandleScore.getInstance(game1));
    }

    /**
     * test for scors.txt
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void test2(){
        assertFalse(HandleScore.historyExist());
        assertTrue(HandleScore.historyExist());
    }

    /**
     * test to add new high score an update scores
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void test3(){
        HandleScore handleScore = HandleScore.getInstance(game1);
        HandleScore.historyExist();
        assertTrue(HandleScore.getLowestScore() == 0);
        handleScore.addHighScore(1, "testplayer");
        handleScore.addHighScore(2, "testplayer1");
        handleScore.addHighScore(3, "testplayer2");
        handleScore.addHighScore(4, "testplayer3");
        handleScore.addHighScore(5, "testplayer4");
        handleScore.addHighScore(6, "testplayer5");
        handleScore.addHighScore(7, "testplayer6");
        handleScore.addHighScore(8, "testplayer7");
        handleScore.addHighScore(9, "testplayer8");
        handleScore.addHighScore(10, "testplayer9");
        assertTrue(HandleScore.getLowestScore() == 1);
        handleScore.addHighScore(11, "testplayer10");
        assertTrue(HandleScore.loadScores().size()==10);
        assertTrue(HandleScore.getLowestScore() == 2);
        handleScore.rest();
        assertTrue(HandleScore.loadScores().size()==0);
    }
}
