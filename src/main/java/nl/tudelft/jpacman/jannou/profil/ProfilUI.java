package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.LauncherJ;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.PacManUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

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

    protected ProfilUI(HandleProfil hProfil,Game game,PacManUI pacManUI){
        initialize(hProfil,game, pacManUI);
    }
    private void initialize(HandleProfil _hProfil,Game _game,PacManUI _pacManUI) {
        hProfil = _hProfil;
        pacManUI=_pacManUI;
        game = _game;
        profilP = new ProfilPanel(hProfil);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Profils");
        setSize(500,440);
        mainContentPane = new JPanel();
        mainContentPane.setLayout(new BoxLayout(mainContentPane, BoxLayout.Y_AXIS));
        mainContentPane.add( profilP);
        mainContentPane.add( new ButtonPan(this));
        setContentPane(mainContentPane);
        setVisible(true);
    }
    public void setPacManUI(PacManUI _pacManUI){
        pacManUI = _pacManUI;
    }
    private class ButtonPan extends JPanel{
        private JFrame profilFrame;
        public ButtonPan(JFrame frame){
            this.profilFrame = frame;
            JButton okButton = new JButton("Play !");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(profilP.getSelectedProfil() != null){

                        game.getPlayers().get(0).setProfil(profilP.getSelectedProfil());
                        try {
                            LauncherJ.main2();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        String prop1 = profilP.getSelectedProfil().proposeFeats().get(new Random().nextInt(profilP.getSelectedProfil().proposeFeats().size())).getDesc();
                        prop1 +="\n"+ profilP.getSelectedProfil().proposeFeats().get(new Random().nextInt(profilP.getSelectedProfil().proposeFeats().size())).getDesc();
                        prop1 +="\n"+ profilP.getSelectedProfil().proposeFeats().get(new Random().nextInt(profilP.getSelectedProfil().proposeFeats().size())).getDesc();
                        JOptionPane.showMessageDialog(frame,prop1,"FEATS", JOptionPane.INFORMATION_MESSAGE);
                        profilFrame.dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(frame,"Please select a profile ","WARNING", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(profilP.getSelectedProfil() != null) {
                        String del = profilP.getSelectedProfil().getName();
                        int n;
                        Object[] options = {"Yes", "No"};
                        n = JOptionPane.showOptionDialog(frame,
                                "Would you delete " + del + " ?",
                                "WARNING", JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[1]);
                        if (n == 0) {
                            profilP.update(false, del);
                        }
                    }
                }
            });
            JButton newPButton = new JButton("New Profil");
            newPButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel panel = new JPanel();
                    JTextField textField = new JTextField(10);
                    panel.add(textField);
                    Object[] options = { "Ok", "Cancel" };
                    int n = JOptionPane.showOptionDialog(null, panel, "Enter your name ",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, options[0]);
                    if(n==0) {
                        if(!hProfil.getProfils().contains(textField.getText()+".xml")){
                            hProfil.addNewProfil(textField.getText());
                            profilP.update(true,"");
                        }
                        else{
                            JOptionPane.showMessageDialog(frame,"This name is already taken,choose another name please","WARNING", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            add(newPButton);
            add(deleteButton);
            add(okButton);
        }
    }
}
