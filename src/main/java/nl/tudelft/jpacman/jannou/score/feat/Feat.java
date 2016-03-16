package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * A feat represents a particular action performed by a player
 * Created by Jannou on 5/03/16.
 */
public abstract  class Feat {
        private String name = "";
        private String description = "";
        private int value = 0;
        private boolean realised = false;
        private int state = 0;

        protected Feat(){
                realised = false;
        }
        public String getName(){
                return name;
        }
        public String getDesc(){
                return description;
        }
        public int getValue(){
                return value;
        }
        public boolean isRealised(){
                return realised;
        }
        public int getState(){
                return state;
        }
        public void setName(String _name){
                name = _name;
        }
        public void setDesc(String _desc){
                description = _desc;
        }
        public void setValue(int _value){
                value = _value;
        }
        public void setRealised(boolean _realised){
                realised = _realised;
        }
        public void setState(int i){
                state = i;
        }
        public String toString() {
                return  getName() ;
        }
        public abstract String toMaps();
        public abstract boolean condition(int i, Ghost g);
        public abstract void updatestate();
}
