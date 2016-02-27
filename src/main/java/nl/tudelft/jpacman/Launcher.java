package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.game.MultiplayerWithoutPacmanGame;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.Action;
import nl.tudelft.jpacman.ui.PacManUI;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

/**
 * Creates and launches the JPacMan UI.
 * 
 * @author Jeroen Roosen 
 */
public class Launcher {

	private static final PacManSprites SPRITE_STORE = new PacManSprites();

	private PacManUI pacManUI;
	private Game game;

	/**
	 * @return The game object this launcher will start when {@link #launch()}
	 *         is called.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Creates a new game using the level from {@link #makeLevel()}.
	 * 
	 * @return a new Game.
	 */
	public Game makeGame() {
		GameFactory gf = getGameFactory();
		Level level = makeLevel();
		return gf.createSinglePlayerGame(level);
	}

	/**
	 * Creates a new game using the level from {@link #makeLevel()}.
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
	 * @return A new level built from the resource.
	 */
	public Level makeLevel(String path) {
		MapParser parser = getMapParser();
		try (InputStream boardStream = Launcher.class
				.getResourceAsStream(path)) {
			return parser.parseMap(boardStream);
		} catch (IOException e) {
			throw new PacmanConfigurationException("Unable to create level.", e);
		}
	}

	/**
	 * Creates a new level using resource located at path.
	 *
	 * @param path - The path to the map.
	 * @return A new level built from the resource for multi-player games.
	 */
	public Level makeMultiPlayerLevel(String path) {
		MapParser parser = getMultiplayerMapParser();
		parser.setCollisionMap(new MultiPlayerCollisions());
		try (InputStream boardStream = Launcher.class
				.getResourceAsStream(path)) {
			return parser.parseMap(boardStream);
		} catch (IOException e) {
			throw new PacmanConfigurationException("Unable to create level.", e);
		}
	}

	/**
	 * Creates a new level. By default this method will use the map parser to
	 * parse the default board stored in the <code>board.txt</code> resource.
	 *
	 * @return A new level.
	 */
	public Level makeLevel() {
		return makeLevel("board.txt");
	}

	/**
	 * @return A new map parser object using the factories from
	 *         {@link #getLevelFactory()} and {@link #getBoardFactory()}.
	 */
	protected MapParser getMapParser() {
		return new MapParser(getLevelFactory(), getBoardFactory());
	}

	/**
	 * @return A new map parser for multiplayer using the factories from
	 *         {@link #getLevelFactory()} and {@link #getBoardFactory()}.
	 */
	protected MultiPlayerMapParser getMultiplayerMapParser(){
		return new MultiPlayerMapParser(getLevelFactory(), getBoardFactory());
	}

	/**
	 * @return A new board factory using the sprite store from
	 *         {@link #getSpriteStore()}.
	 */
	protected BoardFactory getBoardFactory() {
		return new BoardFactory(getSpriteStore());
	}

	/**
	 * @return The default {@link PacManSprites}.
	 */
	protected PacManSprites getSpriteStore() {
		return SPRITE_STORE;
	}

	/**
	 * @return A new factory using the sprites from {@link #getSpriteStore()}
	 *         and the ghosts from {@link #getGhostFactory()}.
	 */
	protected LevelFactory getLevelFactory() {
		return new LevelFactory(getSpriteStore(), getGhostFactory());
	}

	/**
	 * @return A new factory using the sprites from {@link #getSpriteStore()}.
	 */
	protected GhostFactory getGhostFactory() {
		return new GhostFactory(getSpriteStore());
	}

	/**
	 * @return A new factory using the players from {@link #getPlayerFactory()}.
	 */
	protected GameFactory getGameFactory() {
		return new GameFactory(getPlayerFactory());
	}

	/**
	 * @return A new factory using the sprites from {@link #getSpriteStore()}.
	 */
	protected PlayerFactory getPlayerFactory() {
		return new PlayerFactory(getSpriteStore());
	}

	/**
	 * Adds key events UP, DOWN, LEFT and RIGHT to a game.
	 * 
	 * @param builder
	 *            The {@link PacManUiBuilder} that will provide the UI.
	 * @param game
	 *            The game that will process the events.
	 */
	protected void addSinglePlayerKeys(final PacManUiBuilder builder,
			final Game game) {
		final Player p1 = getSinglePlayer(game);

		builder.addKey(KeyEvent.VK_UP, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.NORTH);
			}
		}).addKey(KeyEvent.VK_DOWN, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.SOUTH);
			}
		}).addKey(KeyEvent.VK_LEFT, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.WEST);
			}
		}).addKey(KeyEvent.VK_RIGHT, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.EAST);
			}
		});

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
			builder.addKey(keys[j], () -> game.move(ghost, directions[j]));
		}
	}

	private Player getSinglePlayer(final Game game) {
		List<Player> players = game.getPlayers();
		if (players.isEmpty()) {
			throw new IllegalArgumentException("Game has 0 players.");
		}
		final Player p1 = players.get(0);
		return p1;
	}

	/**
	 * Creates and starts a JPac-Man game.
	 */
	public void launch() {
		game = makeGame();
		PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
		addSinglePlayerKeys(builder, game);
		pacManUI = builder.build(game);
		pacManUI.start();
	}

	/**
	 * Creates and starts a Multi-player Wihtout Pacman game.
	 */
	public void launchMultiPlayer(){
		game = makeMultiPlayerGame(2);
		PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
		addMultiPlayerKeys(builder, game);
		pacManUI = builder.build(game);
		pacManUI.start();
	}

	/**
	 * Disposes of the UI. For more information see {@link javax.swing.JFrame#dispose()}.
	 */
	public void dispose() {
		pacManUI.dispose();
	}

	/**
	 * Main execution method for the Launcher.
	 * 
	 * @param args
	 *            The command line arguments - which are ignored.
	 * @throws IOException
	 *             When a resource could not be read.
	 */
	public static void main(String[] args) throws IOException {
		new Launcher().launchMultiPlayer();//.launch();
	}
}
