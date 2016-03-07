package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.PacManUI;


/**
 * Created by Jannou on 6/03/16.
 */
public class ProfilUIBuilder {
    private Game game ;
    private ProfilUI instance = null;
    private HandleProfil hProfil;
    private PacManUI pacManUI;

    public ProfilUIBuilder(final Game game , PacManUI pacManUI){
        assert game != null;
        this.game = game;
        this.pacManUI=pacManUI;
        hProfil =HandleProfil.getInstance(game);

    }
    public void createScoreUI(){
        if(instance == null){
            instance = new ProfilUI(hProfil, pacManUI);
        }
    }
    public ProfilUI getInstance(){ //maybe use to regroupe all Jframe into once
        return instance;
    }
}
