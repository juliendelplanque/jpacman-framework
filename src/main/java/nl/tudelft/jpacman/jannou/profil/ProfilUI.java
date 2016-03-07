package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.PacManUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Created by Jannou on 5/03/16.
 */
public class ProfilUI extends JFrame{
    private Game game;
    private JPanel mainContentPane ;
    private HandleProfil hProfil;
    private ProfilPanel profilP;
    private PacManUI pacManUI;

    protected ProfilUI(HandleProfil hProfil,PacManUI pacManUI){
        initialize(hProfil, pacManUI);
    }
    private void initialize(HandleProfil _hProfil,PacManUI _pacManUI) {
        hProfil = _hProfil;
        pacManUI=_pacManUI;
        profilP = new ProfilPanel(hProfil);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Profils");
        setSize(370,440);
        mainContentPane = new JPanel();
        mainContentPane.setLayout(new BoxLayout(mainContentPane, BoxLayout.Y_AXIS));
        mainContentPane.add( profilP);
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
                    System.out.println(profilP.getSelectedProfil());
                    pacManUI.start();
                    profilFrame.dispose();
                }
            });
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String del = profilP.getSelectedProfil();
                    int n = 0;
                    Object[] options = { "Yes", "No" };
                    n = JOptionPane.showOptionDialog(frame,
                            "Would you delete "+del+" ?",
                            "WARNING", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if(n==0) {
                        System.out.println("oooooooow");
                        System.out.println(hProfil.removeProfil(del));
                        profilP.update(false,del);
                    }
                }
            });
            JButton newPButton = new JButton("New Profil");
            newPButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("yeahhhhh :D ");
                    hProfil.addNewProfil("TEST3");
                    profilP.update(true,"");
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
