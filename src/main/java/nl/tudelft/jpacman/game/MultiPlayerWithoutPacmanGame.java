package nl.tudelft.jpacman.game;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.NPC;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * A Game specialized to play multi-players without Pacman game.
 * @author Julien Delplanque
 */
public class MultiPlayerWithoutPacmanGame extends Game implements RespawnListener {
    /**
     * The total period of time, this is divided between
     * players to be the hunter.
     */
    public static final int PERIOD = 10000;
    /**
     * Time to wait before respawn in millisec.
     */
    private static final long WAIT_BEFORE_RESPAWN = 3000;
    /**
     * The level of this game.
     */
    private final Level level;
    private final ArrayList<Player> players;
    private final ArrayList<Hunter> hunters;
    private Timer hunterSelectorTimer;
    private boolean isTimerRunning;
    private HunterSelector hunterSelector;

    /**
     * Creates an instance of a game that is played by at least 2 players
     * and at most 4 players.
     * @param players - The players list for the game.
     * @param level - The level to use for the game.
     */
    protected MultiPlayerWithoutPacmanGame(ArrayList<Player> players, Level level){
        this.level = level;
        this.players = players;
        this.hunters = new ArrayList<>();
        for(Player player : players){
            Hunter hunter = (Hunter) player;
            level.registerPlayer(player);
            hunter.addRespawnListener(this);
            this.hunters.add(hunter);
        }
        for(NPC npc : this.level.getNpcs()){
            Hunter hunter = (Hunter) npc;
            hunter.addRespawnListener(this);
            this.hunters.add(hunter);
        }
        this.isTimerRunning = false;
        this.hunterSelectorTimer = new Timer("HunterPlayer selector");
        this.hunterSelector = new HunterSelector(this.hunters);
    }

    /**
     * Reset the timer and the task and prepare them to be re-run.
     */
    public void resetTimer(){
        this.hunterSelectorTimer.cancel();
        this.hunterSelectorTimer.purge();
        this.hunterSelectorTimer = new Timer("HunterPlayer selector");
        this.hunterSelector.currentHunter().setHunting(false);
        this.hunterSelector = new HunterSelector(this.hunters);
    }

    /**
     * Returns the list of players in this game.
     * @return The list of players.
     */
    @Override
    public List<Player> getPlayers() {
        return ImmutableList.copyOf(this.players);
    }

    /**
     * Returns the level of this game.
     * @return The level.
     */
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
            this.hunterSelectorTimer.scheduleAtFixedRate(this.hunterSelector, 0, PERIOD / this.hunters.size());
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
     * I make the hunter concerned respawn using its respawn() method.
     * @param hunter
     *          The hunter to respawn.
     */
    @Override
    public void hunterNeedRespawn(Hunter hunter) {
        Timer respawnTimer = new Timer("HunterPlayer respawner");
        respawnTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                hunter.respawn();
            }
        }, WAIT_BEFORE_RESPAWN);
    }
}
