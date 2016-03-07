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
    private DefaultListModel<String> model;

    public ProfilPanel(HandleProfil _hProfil){
        hProfil = _hProfil;
        setLayout(new BorderLayout(0,0));
        // haut,gauche,bas,droit
        setBorder(new EmptyBorder(new Insets(40, 60, 20, 60)));
        dismain = new JPanel();
        dismain.setLayout(new GridLayout(1,1));
        model = new DefaultListModel<String>();
        list = new JList(model);
        updateList();
        JScrollPane pane = new JScrollPane(list);
        dismain.add(pane);
        add(dismain,BorderLayout.CENTER);

    }
    protected void update(boolean add, String name){
        if(add){
            updateList();
        }
        else if(!name.equals("")){
            removeprofil(name);
        }
    }
    protected void updateList(){
        for(String s : hProfil.getProfils()){
            if(!model.contains(hProfil.getProfil(s).getName())){
                model.addElement(hProfil.getProfil(s).getName());
            }
        }
    }
    protected void removeprofil(String name){
        if(model.contains(name)){
            model.removeElement(name);
        }
    }
    protected String getSelectedProfil(){
        return model.get(list.getSelectedIndex());
    }
}
