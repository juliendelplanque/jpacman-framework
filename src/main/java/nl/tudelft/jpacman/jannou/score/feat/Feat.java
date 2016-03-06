package nl.tudelft.jpacman.jannou.score.feat;

/**
 * A feat represents a particular action performed by a player
 * Created by Jannou on 5/03/16.
 */
public abstract  class Feat {
        private int value;
        private String name;
        private String description;
        private boolean realised;

        protected Feat(){
                realised = false;
        }
        public int getValue(){
                return value;
        }
        public String getName(){
                return name;
        }
        public String getDesc(){
                return description;
        }
        public boolean isRealised(){
                return realised;
        }
        protected void setName(String _name){
                name = _name;
        }

        public String toString() {
                return  getName() ;
        }
}
