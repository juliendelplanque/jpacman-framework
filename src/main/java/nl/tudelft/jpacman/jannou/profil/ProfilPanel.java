package nl.tudelft.jpacman.jannou.profil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * JPanel pour afficher les profils existants.
 * Created by Jannou on 5/03/16.
 */
public class ProfilPanel extends JPanel {
    protected JPanel dismain;
    protected ArrayList<JLabel> sLab ;
    private JList list;
    private HandleProfil hProfil;
    private DefaultListModel<Profil> model;

    /**
     * Constructe ProfilPanel
     * @param _hProfil instance de HandleProfil
     */
    public ProfilPanel(final HandleProfil _hProfil){
        hProfil = _hProfil;
        setLayout(new BorderLayout(0,0));
        // haut,gauche,bas,droit
        setBorder(new EmptyBorder(new Insets(40, 60, 20, 60)));
        dismain = new JPanel();
        dismain.setLayout(new GridLayout(1,1));
        model = new DefaultListModel<>();
        list = new JList(model);
        updateList();
        JScrollPane pane = new JScrollPane(list);
        dismain.add(pane);
        add(dismain,BorderLayout.CENTER);

    }

    /**
     * Ajout ou supprime un profil de l affichage
     * @param add true si on a ajoute un profil, false si on a supprime
     * @param name le nom du profil supprime
     */
    protected void update(boolean add, String name){
        if(add){
            updateList();
        }
        else if(!name.equals("")){
            removeprofil(name);
        }
    }

    /**
     * Met a jour la liste des profils a l affichage
     */
    protected void updateList(){
        ArrayList<Profil> prof= new ArrayList<>();
        for(String name : hProfil.getProfils()){
            prof.add(hProfil.getProfil(name));
        }
        if(model.size()==0) {
            for (Profil p : prof){
                model.addElement(p);
            }
        }
        else{
            for (Profil p : prof){
                int size = 0;
                boolean found = false;
                while(!found && size<model.size()){
                    found = model.get(size).getName().equals(p.getName());
                    size +=1;
                }
                if (!found){
                    model.add(0,p);
                }
            }
        }
    }

    /**
     * Comportement associe a la suppression d un profil
     * @param name le nom du profil a supprimer
     */
    protected void removeprofil(final String name){
        for(int i =0; i<model.size();i++){
            if (model.get(i).getName().equals(name)) {
                hProfil.removeProfil(name);
                model.remove(i);
            }
        }
    }

    /**
     * Retourne le profil selectionne a l affichage par l utilisateur
     * @return un profil
     */
    protected Profil getSelectedProfil(){
        if(list.getSelectedIndex() != -1)
            return hProfil.getProfil(model.get(list.getSelectedIndex()).getName()+".xml");
        return null;
    }
}
