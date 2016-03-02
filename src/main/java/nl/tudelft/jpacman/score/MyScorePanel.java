package nl.tudelft.jpacman.score;


import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
/**
 * Created by Jannou on 2/03/16.
 */
public class MyScorePanel extends JPanel {

    /**
     * Create the menu panel.
     */
    public MyScorePanel(){
        initialize();
    }


    /**
     * Initialize the panel.
     */
    private void initialize() {

        JPanel cont = new JPanel();
        //cont.setBackground(Color.white);
        //history = new HistoryPanel();
        //display = DisplayPanel.getInstance();
        setLayout(new BorderLayout(0, 0));
        //cont.add(history, BorderLayout.NORTH);
        //this.add(cont);
        this.setBackground(Color.white);
    }
}
