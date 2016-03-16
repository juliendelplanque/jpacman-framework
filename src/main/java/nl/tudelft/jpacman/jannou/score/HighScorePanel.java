package nl.tudelft.jpacman.jannou.score;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Jpanel pour afficher les liste des high scores realise
 * Created by Jannou on 4/03/16.
 */
public class HighScorePanel extends JPanel {
    private static HandleScore hScore;
    protected JPanel dismain;
    protected ArrayList<JLabel> sLab ;

    /**
     * Contructeur HighScorePanel
     * @param _hScore instance de HandleScore
     */
    public HighScorePanel(HandleScore _hScore){
        hScore = _hScore;
        setLayout(new BorderLayout(0,0));
        // haut,gauche,bas,droit
        setBorder(new EmptyBorder(new Insets(40, 60, 20, 60)));
        dismain = new JPanel();
        dismain.setLayout(new GridLayout(10,1));
        sLab = getScores();
        for(JLabel l : sLab){
            dismain.add(l);
        }
        add(dismain,BorderLayout.CENTER);
    }

    /**
     * Returne l ensemble des JLabel pour afficher un high score
     * @return ArrayList<JLabel> ou chaque JLabel represente un high score
     */
    private static ArrayList<JLabel> getScores(){
        ArrayList<JLabel>  retour = new ArrayList<>();
        ArrayList<ScorePlayer>  sp = HandleScore.getScores();
        for (ScorePlayer e : sp){
            retour.add(new JLabel(e.toString()));
        }
        return retour;
    }

    /**
     * Supprime et recharge la liste des high scores a afficher
     */
    protected void reset(){
        for(JLabel l : sLab){
            dismain.remove(l);
        }
        sLab = getScores();
        for(JLabel l : sLab){
            dismain.add(l);
        }
        dismain.updateUI();
        this.updateUI();
    }
}
