package nl.tudelft.jpacman.jannou.score.feat;

import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * Feat est un exploit qui represente une action particuliere effectue par l utilisateur
 */
public abstract  class Feat {
        private String name = "";
        private String description = "";
        private int value = 0;
        private boolean realised = false;
        private int state = 0;

    /**
     * Constructeur Feat
     */
    protected Feat(){
        }

    /**
     * Retourne le nom de l exploit
     * @return le nome de l exploit
     */
    public String getName(){
                return name;
        }

    /**
     * Retourne la description de l exploit
     * @return la description de l exploit
     */
    public String getDesc(){
                return description;
        }

    /**
     * Retourne la valeur de l exploit
     * @return la valeur de l exploit
     */
    public int getValue(){
                return value;
        }

    /**
     * verifier si l exploit a ete realiser
     * @return true si l exploit a deja ete realiser false sinon
     */
    public boolean isRealised(){
                return realised;
        }

    /**
     * retourne l etat de l exploit
     * @return l etat de l exploit
     */
    public int getState(){
                return state;
        }

    /**
     * Met a jour le nom de l exploit
     * @param _name le nouveau nom de l exploit
     */
    public void setName(String _name){
                name = _name;
        }

    /**
     * Met a jour la description de l exploit
     * @param _desc la nouvelle description de l exploit
     */
    public void setDesc(String _desc){
                description = _desc;
        }

    /**
     * Met a jour la valeur de l exploit
     * @param _value la nouvelle valeur de l exploit
     */
    public void setValue(int _value){
                value = _value;
        }

    /**
     * Met a jour l etat de realisation de l exploit
     * @param _realised le nouveau etat de realisation de l exploit
     */
    public void setRealised(boolean _realised){
                realised = _realised;
        }

    /**
     * Met a jour l etat de l exploit
     * @param i le nouveau ete de l exploit
     */
    public void setState(int i){
                state = i;
        }

    /**
     * toString()
     * @return toString()
     */
    public String toString() {
                return  getName() ;
        }

    /**
     * Retourne un String represantant l exploit
     * @return un String representant l exploit
     */
    public abstract String toMaps();

    /**
     * Verifie si l utilisateur a effectue une action intervenant dans l exploit
     * @param i un integer
     * @param g un ghost
     * @return true si l utilisateur a effectue une action intervenant dans l exploit false sinon
     */
    public abstract boolean condition(int i, Ghost g);

    /**
     * Fonction permettant de personnaliser la mise a jour de l etat
     */
    public abstract void updatestate();
}
