package business.entities;

public class Monster {

    private String name;
    private String challenge;
    private int experience;
    private int hitPoints;
    private transient int currentHp;
    private int initiative;
    private String damageDice;
    private String damageType;
    private transient int combatOrder;
    private transient boolean targeted;


    public Monster(String name, String challenge, int experience, int hitPoints, int initiative, String damageDice, String damageType) {
        this.name = name;
        this.challenge = challenge;
        this.experience = experience;
        this.hitPoints = hitPoints;
        this.initiative = initiative;
        this.damageDice = damageDice;
        this.damageType = damageType;
        this.targeted = false;
    }

    public String getName() {
        return name;
    }

    public String getChallenge() {
        return challenge;
    }

    public int getExperience() {
        return experience;
    }

    public int getHitPoints() {
        return hitPoints;
    }


    public int getInitiative() {
        return initiative;
    }

    public int getDamageDice() {
        return Integer.parseInt(damageDice.replaceAll("[^0-9]", ""));
    }

    public String getDamageType() {
        return damageType;
    }

    public void setInitiative(int num) {
        this.initiative = num;
    }

    public int getCombatOrder() {
        return combatOrder;
    }

    public void setCombatOrder(int combatOrder) {
        this.combatOrder = combatOrder;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public boolean isTargeted() {
        return targeted;
    }

    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
    }
}
