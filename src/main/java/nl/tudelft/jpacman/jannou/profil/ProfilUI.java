package nl.tudelft.jpacman.jannou.profil;

import nl.tudelft.jpacman.LauncherSuperPellet;
import nl.tudelft.jpacman.game.Game;

import javax.swing.*;
import java.util.Random;

/**
 * JFrame de l affichage des profils et des boutons
 * Created by Jannou on 5/03/16.
 */
public class ProfilUI extends JFrame{
    private Game game;
    private JPanel mainContentPane ;
    private HandleProfil hProfil;
    private ProfilPanel profilP;

    /**
     * Constructeur ProfilUI
     * @param hProfil instance de HandleProfil
     * @param game intance de Game
     */
    protected ProfilUI(final HandleProfil hProfil,final Game game){
        initialize(hProfil,game);
    }

    /**
     * Initialisation de la JFrame
     * @param _hProfil instance de HandleProfil
     * @param _game intance de Game
     */
    private void initialize(final HandleProfil _hProfil,final Game _game) {
        hProfil = _hProfil;
        game = _game;
        profilP = new ProfilPanel(hProfil);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Profils");
        setSize(500,440);
        mainContentPane = new JPanel();
        mainContentPane.setLayout(new BoxLayout(mainContentPane, BoxLayout.Y_AXIS));
        mainContentPane.add( profilP);
        mainContentPane.add( new ButtonPan(this));
        setContentPane(mainContentPane);
        setVisible(true);
    }

    private class ButtonPan extends JPanel{
        private JFrame profilFrame;
        public ButtonPan(final JFrame frame){
            this.profilFrame = frame;
            JButton okButton = new JButton("Play !");
            okButton.addActionListener(e -> {
                if(profilP.getSelectedProfil() != null){

                    game.getPlayers().get(0).setProfil(profilP.getSelectedProfil());
                    LauncherSuperPellet.soleInstance().run();
                    String prop1 = profilP.getSelectedProfil().proposeFeats().get(new Random().nextInt(profilP.getSelectedProfil().proposeFeats().size())).getDesc();
                    prop1 +="\n"+ profilP.getSelectedProfil().proposeFeats().get(new Random().nextInt(profilP.getSelectedProfil().proposeFeats().size())).getDesc();
                    prop1 +="\n"+ profilP.getSelectedProfil().proposeFeats().get(new Random().nextInt(profilP.getSelectedProfil().proposeFeats().size())).getDesc();
                    JOptionPane.showMessageDialog(frame,prop1,"FEATS", JOptionPane.INFORMATION_MESSAGE);
                    profilFrame.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(frame,"Please select a profile ","WARNING", JOptionPane.ERROR_MESSAGE);
                }
            });
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(e -> {
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
            });
            JButton newPButton = new JButton("New Profil");
            newPButton.addActionListener(e -> {
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
            });
            add(newPButton);
            add(deleteButton);
            add(okButton);
        }
    }
}
