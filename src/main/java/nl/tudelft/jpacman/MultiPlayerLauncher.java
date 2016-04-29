package nl.tudelft.jpacman;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.ui.GameChooser;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

/**
 * Launcher for a multi-players game.
 * @author Julien Delplanque
 */
public class MultiPlayerLauncher extends Launcher {
    /**
     * @return A new map parser for multiplayer using the factories from
     *         {@link #getLevelFactory()} and {@link #getBoardFactory()}.
     */
    private MultiPlayerMapParser getMultiPlayerMapParser(){
        return new MultiPlayerMapParser(getLevelFactory(), getBoardFactory());
    }

    /**
     * Creates a new game using the level from {@link #makeMultiPlayerLevel(String path)}.
     * @return A new Game.
     */
    public Game makeMultiPlayerGame(int playerCount) {
        GameFactory gf = getGameFactory();
        assert (playerCount > 4 || playerCount < 2);
        Level level = makeMultiPlayerLevel("/boardmulti"+playerCount+".txt");
        return gf.createMultiPlayerWithoutPacmanGame(playerCount, level);
    }

    /**
     * Creates a new level using resource located at path.
     *
     * @param path - The path to the map.
     * @return A new level built from the resource for multi-player games.
     */
    public Level makeMultiPlayerLevel(String path) {
        MapParser parser = getMultiPlayerMapParser();
        try (InputStream boardStream = Launcher.class
                .getResourceAsStream(path)) {
            return parser.parseMap(boardStream);
        } catch (IOException e) {
            throw new PacmanConfigurationException("Unable to create level.", e);
        }
    }

    /**
     * Initialize the key-mapping for the player given as paramter.
     * @param ghost - The ghost which needs a key-binding.
     * @param builder - The ui builder.
     * @param game - The game.
     * @param keys - The keys to set, first is north, second is south,
     *               third is west and last is east.
     */
    private void initializeKeysForPlayer(final Player ghost, PacManUiBuilder builder, final Game game, int[] keys) {
        Direction[] directions = { Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        for(int i = 0; i<keys.length; i++) {
            final int j = i;
            builder.addKey(keys[j], () -> {if (ghost.isAlive()) game.move(ghost, directions[j]);});
        }
    }

    /**
     * Initialize keys for a multiplayer game.
     * Ghost1: Arrows
     * Ghost2: Z,S,Q,D
     * Ghost3: T,G,F,H
     * Ghost5: I,K,J,L
     * @param builder - The builder needed to set up keys.
     * @param game - The game needed to access players.
     */
    protected void addMultiPlayerKeys(final PacManUiBuilder builder, final Game game){
        // There are at least 2 ghosts
        final Player ghost1 = game.getPlayers().get(0);
        initializeKeysForPlayer(ghost1, builder, game,
                new int[]{KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT});
        final Player ghost2 = game.getPlayers().get(1);
        initializeKeysForPlayer(ghost2, builder, game,
                new int[]{KeyEvent.VK_Z, KeyEvent.VK_S, KeyEvent.VK_Q, KeyEvent.VK_D});

        if(game.getPlayers().size() >= 3){
            final Player ghost3 = game.getPlayers().get(2);
            initializeKeysForPlayer(ghost3, builder, game,
                    new int[]{KeyEvent.VK_T, KeyEvent.VK_G, KeyEvent.VK_F, KeyEvent.VK_H});
        }
        if(game.getPlayers().size() == 4){
            final Player ghost4 = game.getPlayers().get(3);
            initializeKeysForPlayer(ghost4, builder, game,
                    new int[]{KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L});
        }
    }

    /**
     * Creates and starts a Multi-player Wihtout Pacman game.
     */
    public void launchMultiPlayer(int playerCount){
        game = makeMultiPlayerGame(playerCount);
        PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
        addMultiPlayerKeys(builder, game);
        pacManUI = builder.build(game);
        pacManUI.start();
    }
}
