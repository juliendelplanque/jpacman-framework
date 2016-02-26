package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;

import java.util.Map;

/**
 * Created by julien on 25/02/16.
 */
public class Hunter extends Player {
    private final Map<Direction, Sprite> huntingSpriteMap;
    private boolean isHunting;

    /**
     * Creates a new hunter with a score of 0 points. After instantiation,
     * it is not hunting directly.
     *
     * @param spriteMap
     *            A map containing a sprite for this player for every direction.
     * @param deathAnimation
     *            The sprite to be shown when this player dies.
     */
    Hunter(Map<Direction, Sprite> spriteMap, Map<Direction, Sprite> huntingSpriteMap, AnimatedSprite deathAnimation){
        super(spriteMap, deathAnimation);
        this.isHunting = false;
        this.huntingSpriteMap = huntingSpriteMap;
    }

    @Override
    public Sprite getSprite(){
        if (this.isAlive() && this.isHunting()){
            return huntingSpriteMap.get(getDirection());
        }
        return super.getSprite();
    }

    /**
     * Returns true if the hunter is hunting, else return false.
     * @return true if the hunter is hunting, else return false.
     */
    public boolean isHunting() {
        return isHunting;
    }

    /**
     * Make the hunter hunt or not.
     * @param hunting
     *          Make the hunter hunt if true, else the hunter can
     *          be killed.
     */
    public void setHunting(boolean hunting) {
        isHunting = hunting;
    }
}
