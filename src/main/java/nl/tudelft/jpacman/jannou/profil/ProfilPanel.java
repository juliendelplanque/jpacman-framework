package nl.tudelft.jpacman.jannou.profil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * panel to show existing profils
 * Created by Jannou on 5/03/16.
 */
public class ProfilPanel extends JPanel {
    protected JPanel dismain;
    protected ArrayList<JLabel> sLab ;
    private JList list;
    private HandleProfil hProfil;
    DefaultListModel model;

    public ProfilPanel(HandleProfil _hProfil){
        hProfil = _hProfil;
        setLayout(new BorderLayout(0,0));
        // haut,gauche,bas,droit
        setBorder(new EmptyBorder(new Insets(40, 60, 20, 60)));
        dismain = new JPanel();
        dismain.setLayout(new GridLayout(10,1));
        model = new DefaultListModel();
        list = new JList(model);
        for(String s : hProfil.getProfils()){
            model.addElement(hProfil.getProfil(s).getName());
        }
        JScrollPane pane = new JScrollPane(list);
        dismain.add(pane);
        add(dismain,BorderLayout.CENTER);

    }
   /* private ArrayL getProfils(){
        ArrayList<Profil> prof = new ArrayList<Profil>();
        for(String s : hProfil.getProfils()){
            prof.add(hProfil.getProfil(s));
        }
        return new Model(prof);
    }*/
    protected void update(){

    }
}
