package nl.tudelft.jpacman.jannou.score;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;



/**
 * Tests various aspects of score (class tested : ScorePlayer, HandleScore, ScoreUIBuilder)
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
        tearDown2();
    }
    public void tearDown2() {
        File f = new File("./src/main/resources/scores.txt");
        if(f.exists()){
            f.delete();
        }
    }

    /**
     * test the creation of HandleScore's instance ( once created by SCreUIBuilder and the second one created manualy
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void test1(){ // test si on peut avoir une instance de HandleScore par game
        HandleScore handleScore1 = new ScoreUIBuilder(game1).gethScore();
        HandleScore handleScore2 = HandleScore.getInstance(game2);
        assertTrue(handleScore1.equals(handleScore2)); // test si instance son les même
        assertTrue(handleScore1.equals(HandleScore.getInstance(game1))); // test si on peut avoir 2 instance de HAndleScore pour un meme game
        assertEquals(handleScore2, HandleScore.getInstance(game1));
        tearDown2(); // supprime sore.txt
    }

    /**
     * test for scors.txt
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void test2()throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException{
        // test creation de HandleScore verifier si score.txt existe ou le creer sinon (
        // initialisatin du fichier score.Txt)
        Method hExist = HandleScore.class.getDeclaredMethod("historyExist");
        hExist.setAccessible(true);
        HandleScore handleScore = HandleScore.getInstance(game1);
        assertTrue((boolean) hExist.invoke(handleScore));
        tearDown2(); // supprime fichier
    }

    /**
     * test to add new high score an update scores
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void test3()throws NoSuchMethodException, SecurityException, // test comportement ajjouter score
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException{
        Method getL = HandleScore.class.getDeclaredMethod("getLowestScore");
        getL.setAccessible(true);
        Method load = HandleScore.class.getDeclaredMethod("getScores");
        load.setAccessible(true);
        Method reset = HandleScore.class.getDeclaredMethod("reset");
        reset.setAccessible(true);
        Method hExist = HandleScore.class.getDeclaredMethod("historyExist");
        hExist.setAccessible(true);
        HandleScore handleScore = HandleScore.getInstance(game1);
        hExist.invoke(handleScore);
        assertEquals((int) getL.invoke(handleScore),1780); // score dev ecris dans score.txt lors de l'init de handleScore
        HandleScore.addHighScores(1, "testplayer");
        HandleScore.addHighScores(2, "testplayer1");
        HandleScore.addHighScores(3, "testplayer2");
        HandleScore.addHighScores(4, "testplayer3");
        HandleScore.addHighScores(5, "testplayer4");
        HandleScore.addHighScores(6, "testplayer5");
        HandleScore.addHighScores(7, "testplayer6");
        HandleScore.addHighScores(8, "testplayer7");
        HandleScore.addHighScores(9, "testplayer8");
        assertEquals((int) getL.invoke(handleScore),1); // test si les score on été ajouter
        HandleScore.addHighScores(11, "testplayer10");
        assertEquals(((ArrayList) load.invoke(handleScore)).size(),10); // test si on ne garde que 10 scores
        assertEquals((int) getL.invoke(handleScore),2); // test si on ne garde que les 10 meilleurs
        HandleScore.addHighScores(1, "testplayer");
        assertEquals((int) getL.invoke(handleScore),2);// test si on ne rajoute que si on a mieux
        reset.invoke(handleScore);
        assertEquals((int) getL.invoke(handleScore),1780);// test si on a bien remis a zero ( en inscivant les score des dev )
    }
}
