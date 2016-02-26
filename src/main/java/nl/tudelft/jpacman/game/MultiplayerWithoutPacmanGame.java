package nl.tudelft.jpacman.game;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.level.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

/**
 * A Game specialized to play multi-players without Pacman game.
 * @author Julien Delplanque
 */
public class MultiplayerWithoutPacmanGame extends Game implements RespawnListener {
    /**
     * The total period of time, this is divided between
     * players to be the hunter.
     */
    public static final int PERIOD = 10000;
    /**
     * The level of this game.
     */
    private final Level level;
    private final ArrayList<Player> players;
    private Timer hunterSelectorTimer;
    private boolean isTimerRunning;
    private HunterSelector hunterSelector;

    /**
     * Creates an instance of a game that is played by at least 2 players
     * and at most 4 players.
     * @param players - The players list for the game.
     * @param level - The level to use for the game.
     */
    protected MultiplayerWithoutPacmanGame(ArrayList<Player> players, Level level){
        this.level = level;
        this.players = players;
        for(Player player : this.players){
            level.registerPlayer(player);
            ((Hunter) player).addRespawnListener(this);
        }
        this.isTimerRunning = false;
        this.hunterSelectorTimer = new Timer("Hunter selector");
        this.hunterSelector = new HunterSelector(this.getPlayers()
                .stream()
                .map(p -> (Hunter) p)
                .collect(Collectors.toList()));
    }

    /**
     * Reset the timer and the task and prepare them to be re-run.
     */
    public void resetTimer(){
        this.hunterSelectorTimer.cancel();
        this.hunterSelectorTimer.purge();
        this.hunterSelectorTimer = new Timer("Hunter selector");
        this.hunterSelector.currentHunter().setHunting(false);
        this.hunterSelector = new HunterSelector(this.getPlayers()
                                                        .stream()
                                                        .map(p -> (Hunter) p)
                                                        .collect(Collectors.toList()));
    }

    @Override
    public List<Player> getPlayers() {
        return ImmutableList.copyOf(this.players);
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    /**
     * Override to start the time when the game starts.
     */
    @Override
    public void start() {
        super.start();
        if (!this.isTimerRunning) {
            this.hunterSelectorTimer.scheduleAtFixedRate(this.hunterSelector, 0, PERIOD / this.players.size());
            this.isTimerRunning = true;
        }
    }

    /**
     * Override to stop and reset the timer when the game is paused.
     */
    @Override
    public void stop() {
        this.resetTimer();
        this.isTimerRunning = false;
        super.stop();
    }

    /**
     * Method called by the Hunters I am listening to.
     * I make the hunter concerned respawn at a new random location.
     * @param hunter
     *          The hunter to respawn.
     */
    @Override
    public void hunterNeedRespawn(Hunter hunter) {
        hunter.respawn();
    }
}
