package nl.tudelft.jpacman;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactorySuperPellet;
import nl.tudelft.jpacman.level.MapParserSuperPellet;
import nl.tudelft.jpacman.npc.ghost.EatableGhostFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Creates and launches the JPacMan UI.
 *
 * @author Maximilien Charlier (maximilien.charlier@student.umons.ac.be).
 */
public class LauncherSuperPellet extends Launcher {

    /**
     * Main execution method for the Launcher.
     *
     * @param args
     *            The command line arguments - which are ignored.
     * @throws IOException
     *             When a resource could not be read.
     */
    public static void main(String[] args) throws IOException {
        new LauncherSuperPellet().launch();
    }

    /**
     * Creates a new level. By default this method will use the map parser to
     * parse the default board stored in the <code>boardSuperPellet.txt</code> resource.
     *
     * @return A new level.
     */
    @Override
    public Level makeLevel() {
        MapParserSuperPellet parser = getMapParserSuperPellet();
        try (InputStream boardStream = Launcher.class
                .getResourceAsStream("/boardSuperPellet.txt")) {
            return parser.parseMap(boardStream);
        } catch (IOException e) {
            throw new PacmanConfigurationException("Unable to create level.", e);
        }
    }

    /**
     * @return A new factory using the sprites from {@link #getSpriteStore()}
     *         and the ghosts from {@link #getGhostFactory()}.
     */
    protected LevelFactorySuperPellet getLevelFactory() {
        return new LevelFactorySuperPellet(getSpriteStore(), getGhostFactory());
    }

    /**
     * @return A new factory using the sprites from {@link #getSpriteStore()}.
     */
    @Override
    protected GhostFactory getGhostFactory() {
        return new EatableGhostFactory(getSpriteStore());
    }
}
