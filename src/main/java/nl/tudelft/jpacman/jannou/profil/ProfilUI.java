package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.game.Game;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.BoxLayout;

/**
 *
 * Created by Jannou on 5/03/16.
 */
public class ProfilUI extends JFrame{
    private Game game;
    private JPanel mainContentPane ;

    public ProfilUI(Game game){
        initialize(game);
    }
    private void initialize(Game game) {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Profils");
        setSize(370,440);
        mainContentPane = new JPanel();
        mainContentPane.setLayout(new BoxLayout(mainContentPane, BoxLayout.Y_AXIS));
        mainContentPane.add( new ButtonPan(this));
        setContentPane(mainContentPane);
        setVisible(true);
    }
    private class ButtonPan extends JPanel{
        private JFrame profilFrame;
        public ButtonPan(JFrame frame){
            this.profilFrame = frame;
            JButton okButton = new JButton("Play !");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("yeahhhhh");
                    profilFrame.dispose();
                }
            });
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("oooooooow");
                    /*int n = 0;
                    Object[] options = { "Yes", "Cancel" };
                    n = JOptionPane.showOptionDialog(frame,
                            "Would you reset Hall of Fame ? all scores will be deleted  ",
                            "WARNING", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if(n==0) {
                        HandleScore.reset();
                        highSP.reset();
                    }*/
                }
            });
            JButton newPButton = new JButton("New Profil");
            newPButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("yeahhhhh :D ");
                    /*int n = 0;
                    Object[] options = { "Yes", "Cancel" };
                    n = JOptionPane.showOptionDialog(frame,
                            "Would you reset Hall of Fame ? all scores will be deleted  ",
                            "WARNING", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if(n==0) {
                        HandleScore.reset();
                        highSP.reset();
                    }*/
                }
            });
            add(newPButton);
            add(deleteButton);
            add(okButton);
        }
    }
}
