package nl.tudelft.jpacman.score;

import nl.tudelft.jpacman.game.*;
import nl.tudelft.jpacman.Launcher;

import nl.tudelft.jpacman.jannou.score.HandleScore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    public void test2()throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException{
        Method hExist = HandleScore.class.getDeclaredMethod("historyExist()", new Class<?>[0]);
        hExist.setAccessible(true);
        HandleScore handleScore = HandleScore.getInstance(game1);
        assertFalse((boolean) hExist.invoke(handleScore, new Object[0]));
        //assertFalse(HandleScore.historyExist());
        //assertTrue(HandleScore.historyExist());
    }

    /**
     * test to add new high score an update scores
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void test3()throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException{
        Method getL = HandleScore.class.getDeclaredMethod("getLowestScore", new Class<?>[0]);
        getL.setAccessible(true);
        Class[] paramTypes = new Class[2];
        paramTypes[0]= int.class;
        paramTypes[1]= String.class;
        Method addH = HandleScore.class.getDeclaredMethod("addHighScore", paramTypes);
        addH.setAccessible(true);
        Method load = HandleScore.class.getDeclaredMethod("loadScore",new Class<?>[0]);
        load.setAccessible(true);
        Method reset = HandleScore.class.getDeclaredMethod("reset", new Class<?>[0]);
        reset.setAccessible(true);
        Method hExist = HandleScore.class.getDeclaredMethod("historyExist()", new Class<?>[0]);
        hExist.setAccessible(true);
        HandleScore handleScore = HandleScore.getInstance(game1);
        hExist.invoke(handleScore, new Object[0]);
        assertEquals((int) getL.invoke(handleScore, new Object[0]),0);
        addH.invoke(handleScore,1, "testplayer");
        addH.invoke(handleScore,2, "testplayer1");
        addH.invoke(handleScore,3, "testplayer2");
        addH.invoke(handleScore,4, "testplayer3");
        addH.invoke(handleScore,5, "testplayer4");
        addH.invoke(handleScore,6, "testplayer5");
        addH.invoke(handleScore,7, "testplayer6");
        addH.invoke(handleScore,8, "testplayer7");
        addH.invoke(handleScore,9, "testplayer8");
        addH.invoke(handleScore,10, "testplayer9");
        assertEquals((int) getL.invoke(handleScore, new Object[0]),1);
        addH.invoke(handleScore,11, "testplayer10");
        assertEquals(((ArrayList) load.invoke(handleScore, new Object[0])).size(),10);
        assertEquals((int) getL.invoke(handleScore, new Object[0]),2);
        addH.invoke(handleScore,1, "testplayer");
        assertEquals((int) getL.invoke(handleScore, new Object[0]),2);
        reset.invoke(handleScore, new Object[0]);
        assertEquals((int) getL.invoke(handleScore, new Object[0]),0);
    }
}
