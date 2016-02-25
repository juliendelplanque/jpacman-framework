package nl.tudelft.jpacman.game;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julien on 25/02/16.
 */
public class MultiplayerWithoutPacmanGame extends Game {
    /**
     * The level of this game.
     */
    private final Level level;
    private final ArrayList<Player> players;

    /**
     * Creates an instance of a game that is played by at least 2 players
     * and at most 4 players.
     * @param players - The players list for the game.
     * @param level - The level to use for the game.
     */
    protected MultiplayerWithoutPacmanGame(ArrayList<Player> players, Level level){
        this.level = level;
        this.players = players;
        for(Player player : this.players)
            level.registerPlayer(player);
    }

    @Override
    public List<Player> getPlayers() {
        return ImmutableList.copyOf(this.players);
    }

    @Override
    public Level getLevel() {
        return this.level;
    }
}
