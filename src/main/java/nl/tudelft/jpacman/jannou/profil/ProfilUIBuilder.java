package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.game.Game;


/**
 * Created by Jannou on 6/03/16.
 */
public class ProfilUIBuilder {
    private Game game ;
    private ProfilUI instance = null;
    private HandleProfil hProfil;

    public ProfilUIBuilder(final Game game ){
        assert game != null;
        this.game = game;
        hProfil =HandleProfil.getInstance(game);

    }
    public void createScoreUI(){
        if(instance == null){
            instance = new ProfilUI(hProfil);
        }
    }
    public ProfilUI getInstance(){ //maybe use to regroupe all Jframe into once
        return instance;
    }
}
