package nl.tudelft.jpacman.jannou.score;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * JFrame Hall of Frame
 * Created by Jannou on 2/03/16.
 */


public class ScoreUI extends JFrame{
    //private static JFrame frmScore;
    private JPanel mainContentPane ;
    private HighScorePanel highSP;
    private HandleScore hScore;

    protected ScoreUI(HandleScore hScore){
        initialize(hScore);
    }
    private void initialize(HandleScore hscore) {
        hScore = hscore;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hall of Fame");
        setSize(370,440);
        mainContentPane = new JPanel();
        highSP = new HighScorePanel(hScore);
        mainContentPane.setLayout(new BoxLayout(mainContentPane, BoxLayout.Y_AXIS));
        mainContentPane.add(highSP);//add new HighScorePanel
        mainContentPane.add( new ButtonPan(this));
        setContentPane(mainContentPane);
        setVisible(true);
    }

    private class ButtonPan extends JPanel{
        private JFrame frameScore;
        public ButtonPan(JFrame frame){
            this.frameScore = frame;

            JButton okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameScore.dispose();
                }
            });
            JButton resetButton = new JButton("Reset");
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int n ;
                    Object[] options = { "Yes", "Cancel" };
                    n = JOptionPane.showOptionDialog(frame,
                            "Would you reset Hall of Fame ? all scores will be deleted  ",
                            "WARNING", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if(n==0) {
                        hScore.reset();
                        highSP.reset();
                    }
                }
            });
        add(okButton);
        add(resetButton);
        }
    }
}