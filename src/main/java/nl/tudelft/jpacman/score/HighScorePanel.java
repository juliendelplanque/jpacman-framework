package nl.tudelft.jpacman.score;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.ArrayList;
import nl.tudelft.jpacman.score.HandleScore;

/**
 * HighScorePanel : load high scores and create a panel to show high scores
 * Created by Jannou on 4/03/16.
 */
public class HighScorePanel extends JPanel {
    private static HandleScore hScore;

    public HighScorePanel(HandleScore _hScore){
        hScore = _hScore;
        setLayout(new BorderLayout(0,0));
        // haut,gauche,bas,droit
        setBorder(new EmptyBorder(new Insets(40, 60, 20, 60)));
        JPanel dismain = new JPanel();
        dismain.setLayout(new GridLayout(10,1));
        ArrayList<JLabel> scores = getScores();
        for(JLabel l : scores){
            dismain.add(l);
        }
        add(dismain,BorderLayout.CENTER);
    }
    private static ArrayList<JLabel> getScores(){
        ArrayList<JLabel>  retour = new ArrayList<JLabel>();
        ArrayList<ScorePlayer>  sp = hScore.loadScores();
        for (ScorePlayer e : sp){
            retour.add(new JLabel(e.toString()));
        }
        return retour;
    }
}
