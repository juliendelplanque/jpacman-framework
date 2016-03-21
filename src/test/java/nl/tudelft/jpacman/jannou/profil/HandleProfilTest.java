package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.jannou.score.feat.Feat;
import nl.tudelft.jpacman.jannou.score.feat.HandleFeat;
import nl.tudelft.jpacman.npc.ghost.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Tests various aspects of profil and feat  (package tested : jannou/profil and jannou/score/feat )
 * Created by Jannou on 16/03/16.
 */
public class HandleProfilTest {
    private Launcher launcher;
    private Game game;
    private ProfilUIBuilder profilUIB;
    private HandleProfil hProfil;

    @Before
    public void setUpTest() {
        launcher = new Launcher();
        game = new Launcher().makeGame();
        profilUIB = new ProfilUIBuilder(game);
        hProfil = profilUIB.gethProfil();
    }
    @After
    public void tearDown() {
        //launcher.dispose();
        tearDown2();
    }
    public void tearDown2() {
        for(String file : hProfil.getProfils()){
            File f = new File("./src/main/resources/Profils/"+file);
            if(f.exists())
                f.delete();
        }
    }

    /**
     * Test l'initialisation, la creation d un profil et la suppression d un profil
     */
    @Test
    public void test1(){
        assertTrue(hProfil.getProfils().isEmpty());
        hProfil.addNewProfil("Test");
        assertTrue(!hProfil.getProfil("Test.xml").proposeFeats().isEmpty());
        hProfil.removeProfil("Test");
        assertTrue(hProfil.getProfils().isEmpty());
    }

    /**
     * Test l ajout d un exploit realise et les exploit restant
     */
    @Test
    public void test2(){
        hProfil.addNewProfil("Test");
        Profil ptest = hProfil.getProfil("Test.xml");
        int size = ptest.proposeFeats().size();
        Feat test = ptest.proposeFeats().get(0);
        test.setRealised(true);
        ptest.addFeat(test);
        hProfil.updateProfil(ptest);
        assertTrue(hProfil.getProfil("Test.xml").proposeFeats().size() == size-1);
    }
    /**
     * Test l ajout d un exploit realise et les exploit restant
     */
    @Test
    public void test3(){
        hProfil.addNewProfil("Test");
        Profil ptest = hProfil.getProfil("Test.xml");
        int size = ptest.proposeFeats().size();
        Feat test = ptest.proposeFeats().get(0);
        test.setRealised(false);
        ptest.addFeat(test);
        hProfil.updateProfil(ptest);
        assertTrue(hProfil.getProfil("Test.xml").proposeFeats().size() == size);
    }
    /**
     * Test la verification automatique de l avancement des exploits non encore realiser par le jouer
     * Test BeatDev1
     */
    @Test
    public void test4(){
        hProfil.addNewProfil("Test");
        Profil ptest = hProfil.getProfil("Test.xml");
        game.getPlayers().get(0).setProfil(ptest);
        HandleFeat.trigger(1780,null,game.getPlayers().get(0));
        hProfil.updateProfil(ptest);
        assertTrue(hProfil.getProfil("Test.xml").getFeats().get(0).getName().equals("BeatDev1"));
    }

    /**
     * Test Kill3Clyde
     */
    @Test
    public void test5(){
        hProfil.addNewProfil("Test");
        Profil ptest = hProfil.getProfil("Test.xml");
        game.getPlayers().get(0).setProfil(ptest);
        Ghost gtest = new Clyde(null);
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        hProfil.updateProfil(ptest);
        Boolean found = false;
        for(Feat f : hProfil.getProfil("Test.xml").getFeats()){
            if(f.getName().equals("Kill3Clyde") && f.isRealised())
                found = true;
        }
        assertTrue(found);
    }

    /**
     * Test Kill3Inky
     */
    @Test
    public void test6(){
        hProfil.addNewProfil("Test");
        Profil ptest = hProfil.getProfil("Test.xml");
        game.getPlayers().get(0).setProfil(ptest);
        Ghost gtest = new Inky(null);
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        hProfil.updateProfil(ptest);
        Boolean found = false;
        for(Feat f : hProfil.getProfil("Test.xml").getFeats()){
            if(f.getName().equals("Kill3Inky") && f.isRealised())
                found = true;
        }
        assertTrue(found);
    }

    /**
     * Test Kill3Pinky
     */
    @Test
    public void test7(){
        hProfil.addNewProfil("Test");
        Profil ptest = hProfil.getProfil("Test.xml");
        game.getPlayers().get(0).setProfil(ptest);
        Ghost gtest = new Pinky(null);
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        hProfil.updateProfil(ptest);
        Boolean found = false;
        for(Feat f : hProfil.getProfil("Test.xml").getFeats()){
            if(f.getName().equals("Kill3Pinky") && f.isRealised())
                found = true;
        }
        assertTrue(found);
    }

    /**
     * Test Kill3Blinky
     */
    @Test
    public void test8(){
        hProfil.addNewProfil("Test");
        Profil ptest = hProfil.getProfil("Test.xml");
        game.getPlayers().get(0).setProfil(ptest);
        Ghost gtest = new Blinky(null);
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        hProfil.updateProfil(ptest);
        Boolean found = false;
        for(Feat f : hProfil.getProfil("Test.xml").getFeats()){
            if(f.getName().equals("Kill3Blinky") && f.isRealised())
                found = true;
        }
        assertTrue(found);
    }

    /**
     * Test Kill4Ghost
     */
    @Test
    public void test9(){
        hProfil.addNewProfil("Test");
        Profil ptest = hProfil.getProfil("Test.xml");
        game.getPlayers().get(0).setProfil(ptest);
        Ghost gtest = new Blinky(null);
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        HandleFeat.trigger(0,gtest,game.getPlayers().get(0));
        hProfil.updateProfil(ptest);
        Boolean found = false;
        for(Feat f : hProfil.getProfil("Test.xml").getFeats()){
            if(f.getName().equals("Kill4Ghost") && f.isRealised())
                found = true;
        }
        assertTrue(found);
    }

    /**
     * Test Kill4DifGhost
     */
    @Test
    public void test10(){
        hProfil.addNewProfil("Test");
        Profil ptest = hProfil.getProfil("Test.xml");
        game.getPlayers().get(0).setProfil(ptest);
        HandleFeat.trigger(0, new Clyde(null),game.getPlayers().get(0));
        HandleFeat.trigger(0,new Pinky(null),game.getPlayers().get(0));
        HandleFeat.trigger(0, new Inky(null),game.getPlayers().get(0));
        HandleFeat.trigger(0,new Blinky(null),game.getPlayers().get(0));

        hProfil.updateProfil(ptest);
        Boolean found = false;
        for(Feat f : hProfil.getProfil("Test.xml").getFeats()){
            if(f.getName().equals("Kill4DifGhost") && f.isRealised())
                found = true;
        }
        assertTrue(found);
    }

}
