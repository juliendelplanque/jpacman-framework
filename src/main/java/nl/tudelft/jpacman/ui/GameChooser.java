package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.LauncherSuperPellet;
import nl.tudelft.jpacman.MultiPlayerLauncher;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;

/**
 * A simple JFrame with radio buttons to choose the game mode.
 */
public class GameChooser extends JFrame {
    public static final int MIN_PLAYERS = 1;
    public static final int MAX_PLAYERS = 4;
    public static final String[] DESCRIPTIONS = getDescriptions();
    private JLabel descriptionField;
    private int numberOfPlayers = 0;
    public GameChooser() {
        super("JPacman");
        // Buttons.
        JPanel buttonPanel = this.createButtonPanel();
        JButton acceptButton = this.createCreateGameButton();
        // Description panel.
        JPanel descriptionPanel = this.createDescriptionPanel();
        // Buttons & description panel.
        JPanel buttonsDescriptionPanel = new JPanel();
        buttonsDescriptionPanel.setLayout(new BoxLayout(buttonsDescriptionPanel, BoxLayout.X_AXIS));
        buttonsDescriptionPanel.add(buttonPanel);
        buttonsDescriptionPanel.add(descriptionPanel);
        // Main panel configuration.
        this.setLayout(new BorderLayout());
        this.add(buttonsDescriptionPanel, BorderLayout.CENTER);
        this.add(acceptButton, BorderLayout.SOUTH);
        // Finalization.
        pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the frame on the screen.
    }

    /**
     * Create the panel holding buttons to select the game mode.
     * @return The panel holding buttons to select the game mode.
     */
    private JPanel createButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JPanel basicGamePanel = new JPanel();
        TitledBorder basicTitle = BorderFactory.createTitledBorder("Basic");
        basicGamePanel.setBorder(basicTitle);
        basicGamePanel.setLayout(new BoxLayout(basicGamePanel, BoxLayout.Y_AXIS));
        ButtonGroup group = new ButtonGroup();
        JRadioButton basicgame = new JRadioButton("Basic game");
        basicgame.addActionListener(e -> {
            this.numberOfPlayers = 0;
            this.descriptionField.setText(DESCRIPTIONS[0]);
        });
        basicgame.setSelected(true);
        group.add(basicgame);
        basicGamePanel.add(basicgame);
        buttonPanel.add(basicGamePanel);

        JPanel extensionPanel = new JPanel();
        TitledBorder extensionTitle = BorderFactory.createTitledBorder("Extensions");
        extensionPanel.setBorder(extensionTitle);
        extensionPanel.setLayout(new BoxLayout(extensionPanel, BoxLayout.Y_AXIS));
        for(int i=MIN_PLAYERS; i<=MAX_PLAYERS; i++){
            final int finalI = i;
            JRadioButton optionPlayers = new JRadioButton(finalI+" players    ");
            optionPlayers.addActionListener(e -> {
                this.numberOfPlayers = finalI;
                this.descriptionField.setText(DESCRIPTIONS[finalI]);
            });
            group.add(optionPlayers);
            extensionPanel.add(optionPlayers);
        }
        buttonPanel.add(extensionPanel);
        return buttonPanel;
    }

    /**
     * Creates the button to create a game.
     * @return The button to create a game.
     */
    private JButton createCreateGameButton(){
        JButton acceptButton = new JButton("Create game");
        acceptButton.addActionListener(e -> {
            this.dispose();
            if(this.numberOfPlayers == 0)
                new Launcher().launch();
            else if(this.numberOfPlayers == 1)
                LauncherSuperPellet.soleInstance().launch();
            else
                new MultiPlayerLauncher().launchMultiPlayer(this.numberOfPlayers); });
        return acceptButton;
    }

    /**
     * Creates the panel holding the description of the game
     * selected and returns it.
     * @return A JPanel holding the description.
     */
    private JPanel createDescriptionPanel(){
        JPanel descriptionPanel = new JPanel();
        TitledBorder title = BorderFactory.createTitledBorder("Description");
        descriptionPanel.setBorder(title);
        this.descriptionField = new JLabel();
        //this.descriptionField.setEditable(false);
        this.descriptionField.setText(DESCRIPTIONS[0]);
        descriptionPanel.add(this.descriptionField);
        return descriptionPanel;
    }

    /**
     * Return the array of description.
     * @return The array of description.
     */
    public static String[] getDescriptions(){
        return new String[]{
                "<html>The basic JPacman implementation.</html>",
                "<html>Super pellet mode with hall of fame.</html>",
                "<html>Multi player mode with 2 humans.<br>" +
                        "Top left: z, q, s & d<br>" +
                        "Bottom right: arrows</html>",
                "<html>Multi player mode with 3 humans.<br>" +
                        "Top left: arrows <br>" +
                        "Bottom right: t, f, g & h<br>" +
                        "Bottom left: z, q, s & d</html>",
                "<html>Multi player mode with 4 humans.<br>"+
                        "Top left: arrows <br>" +
                        "Top right: t, f, g & h<br>" +
                        "Bottom left: z, q, s & d<br>" +
                        "Bottom right: i, j, k & l</html>"
        };
    }

    /**
     * Start the GameChooser.
     * @param args - The command line arguments.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> new GameChooser().setVisible(true));
    }
}