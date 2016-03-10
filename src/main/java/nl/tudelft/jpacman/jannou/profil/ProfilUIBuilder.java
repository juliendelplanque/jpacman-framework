package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.PacManUI;


/**
 * build the GUI of hall of fame and build the HandleScore
 * Created by Jannou on 6/03/16.
 */
public class ProfilUIBuilder {
    private Game game ;
    private ProfilUI instance = null;
    private HandleProfil hProfil;
    private PacManUI pacManUI;

    public ProfilUIBuilder(final Game game , PacManUI pacManUI){
        this.hProfil =HandleProfil.getInstance();
        this.game = game;
        this.pacManUI=pacManUI;
    }
    public void createScoreUI(){
        if(instance == null){
            instance = new ProfilUI(hProfil, game, pacManUI);
        }
    }
    public ProfilUI getInstance(){ //maybe use to regroupe all Jframe into once
        return instance;
    }
}
