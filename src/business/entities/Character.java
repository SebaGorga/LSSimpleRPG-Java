package business.entities;

import com.google.gson.annotations.SerializedName;

public class Character {

    private String name;
    private String player;
    private int xp;
    private int body;
    private int mind;
    private int spirit;
    private transient int maxHp;
    private transient int currentHp;
    private transient int initiative;
    private transient int combatOrder;
    private transient boolean targeted;

    @SerializedName(value = "class_", alternate = "class")
    private String class_;


    /**
     * Constructor with name, surname and grade parameters, for new students.
     *
     * @param name a string representation of the character's name
     * @param player a string representation of the character's owner
     * @param xp an int representation of the character's experience points
     * @param body an int representation of the character's body stat
     * @param mind an int representation of the character's mind stat
     * @param spirit an int representation of the character's spirit stat
     * @param class_ a string representation of the character's class
     */
    public Character(String name, String player, int xp, int body, int mind, int spirit, String class_) {
        this.name = name;
        this.player = player;
        this.xp = xp;
        this.body = body;
        this.mind = mind;
        this.spirit = spirit;
        this.class_ = class_;
        this.targeted = false;
    }

    public String getName() {
        return name;
    }

    public String getPlayer() {
        return player;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
    public int getBody() {
        return body;
    }

    public int getMind() {
        return mind;
    }

    public int getSpirit() {
        return spirit;
    }

    public String getClass_() {
        return class_;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setMaxHP(int maxHP) {
        this.maxHp = maxHP;}

    public void setCurrentHP(int maxHP) {
        this.currentHp = maxHP;
    }

    public void usePreparationStageAction() {
        switch(class_){
            case "Adventurer":
                this.spirit += 1;
                break;
        }
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getCombatOrder() {
        return combatOrder;
    }

    public void setCombatOrder(int combatOrder) {
        this.combatOrder = combatOrder;
    }

    public boolean isTargeted() {
        return targeted;
    }

    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
    }

    public void setSpirit(int i) {
        this.spirit = i;
    }

}
