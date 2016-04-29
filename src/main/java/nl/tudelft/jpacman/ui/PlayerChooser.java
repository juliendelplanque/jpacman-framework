package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.LauncherSuperPellet;
import nl.tudelft.jpacman.MultiPlayerLauncher;

import javax.swing.*;
import java.awt.*;

/**
 * A simple JFrame with radio buttons to select the number
 * of player in a multi-players game.
 */
public class PlayerChooser extends JFrame {
    public static final int MIN_PLAYERS = 1;
    public static final int MAX_PLAYERS = 4;
    private int numberOfPlayers = MIN_PLAYERS;
    public PlayerChooser() {
        super("JPac-Man - Select the number of players");
        ButtonGroup group = new ButtonGroup();
        for(int i=MIN_PLAYERS; i<=MAX_PLAYERS; i++){
            final int finalI = i;
            JRadioButton optionPlayers = new JRadioButton(finalI+" players");
            if (i == MIN_PLAYERS)
                optionPlayers.setSelected(true);
            optionPlayers.addActionListener(e -> this.numberOfPlayers = finalI);
            group.add(optionPlayers);
            add(optionPlayers);
        }

        JButton acceptButton = new JButton("Create game");
        acceptButton.addActionListener(e -> {
            this.dispose();
            if(this.numberOfPlayers == 1)
                new LauncherSuperPellet().launch();
            else
                new MultiPlayerLauncher().launchMultiPlayer(this.numberOfPlayers); });

        setLayout(new FlowLayout());
        add(acceptButton);
        pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the frame on the screen.
    }
}