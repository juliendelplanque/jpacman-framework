package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.game.Game;


/**
 * Constructeur de la gui et des ellement necessaire a la gestin des profils
 * Created by Jannou on 6/03/16.
 */
public class ProfilUIBuilder {

    private Game game ;
    private ProfilUI instance = null;
    private HandleProfil hProfil;

    /**
     * Constucteur ProfilUIBuilder
     * @param game instance de Game
     */
    public ProfilUIBuilder(final Game game ){
        this.hProfil =HandleProfil.getInstance();
        this.game = game;
    }

    /**
     * creer une instane de ProfilUI
     */
    public void createProfilUI(){
        if(instance == null){
            instance = new ProfilUI(hProfil, game);
        }
    }

    /**
     * Retourne l instance de ProfilUI
     * @return null si instance non initiliser, une instance de ProfilUI sinon
     */
    public ProfilUI getInstance(){ //maybe use to regroupe all Jframe into once
        return instance;
    }
    /**
     * created for test
     * @return instance HandleScore
     */
    public HandleProfil gethProfil(){
        return hProfil;
    }
}
