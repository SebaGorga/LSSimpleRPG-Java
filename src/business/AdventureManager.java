package business;

import business.entities.Adventure;
import business.entities.Character;
import business.entities.Encounter;
import business.entities.Monster;
import persistence.exceptions.PersistenceException;
import persistence.json.adventure.AdventureDAO;
import persistence.json.adventure.AdventureJsonDAO;

import java.io.IOException;
import java.util.*;

public class AdventureManager {
    private final AdventureDAO daoAdventure;

    /**
     * Default constructor, which uses the JSON Data Access Object for characters with a fixed file.
     *
     * @throws PersistenceException if the manager can't be instantiated because of errors in the persistence layer
     */
    public AdventureManager() throws PersistenceException {
        daoAdventure = new AdventureJsonDAO("data/adventures.json");
    }

    /**
     * Method that checks whether the name that was input by the user already exists.
     *
     * @param name a string representation of the queried adventure's name
     * @return whether the name is already in use or not
     */
    public boolean checkUniqueName (String name) throws IOException {
        boolean unique = true;
        ArrayList<Adventure> adventures = daoAdventure.getAll();
        if (!adventures.isEmpty()) {
            for (int i = 0; i < adventures.size(); i++) {
                if (Objects.equals(name, adventures.get(i).getName())) {
                    unique = false;
                }
            }
        }
        return unique;
    }

    /**
     * Method that adds an adventure to the system.
     *
     * @param name a string representation of the adventure's name
     * @param encounters an int representation of the number of encounters in an adventure
     */
    public boolean addNewAdventure(String name, ArrayList<Encounter> encounters) {

        Adventure adventure = new Adventure(name, encounters);
        try {
            daoAdventure.save(adventure);
        } catch (PersistenceException | IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Method that checks whether the max number of boss monsters in the encounter has been exceeded.
     *
     * @param encounter the encounter to check
     * @param monster   the monster to check
     * @param quantity
     * @return whether the encounter passes the requirement or not
     */
    public boolean bossMonsterCheck(Encounter encounter, Monster monster, int quantity){
        boolean hasBoss = false;
        boolean correct = true;
        for (Monster m : encounter.getMonsters()) {
            if (Objects.equals(m.getChallenge(), "Boss")) {
                hasBoss = true;
            }
        }
        if (Objects.equals(monster.getChallenge(), "Boss") && hasBoss) {
            correct = false;
        }
        if (Objects.equals(monster.getChallenge(), "Boss") && quantity > 1) {
            correct = false;
        }
        return correct;
    }

    /**
     * Method that adds a monster to the encounter.
     *
     * @param encounter the encounter where the monster should be added
     * @param monster   the monster to add
     * @param quantity the quantity of the monster to add
     * @return NONE
     */
    public void addMonsterToEncounter(Encounter encounter, Monster monster, int quantity) {
        for (int i = 0; i < quantity; i++) {
            encounter.getMonsters().add(monster);
        }
        encounter.getMonsters().sort(Comparator.comparing(Monster::getName));
    }

    /**
     * Method that checks whether the encounter is empty.
     * @param encounter the encounter to check
     * @return whether the encounter is empty or not
     */
    public boolean checkEncounterEmpty(Encounter encounter){
        return encounter.getMonsters().isEmpty();
    }

    /**
     * Method that removes a monster from the encounter.
     *
     * @param encounter the encounter from which the monster should be removed
     * @param monster   the monster to remove
     * @return NONE
     */
    public void removeMonsterFromEncounter(Encounter encounter, String monster){
        int i = 0;
        while (i < encounter.getMonsters().size()) {
            if (Objects.equals(encounter.getMonsters().get(i).getName(), monster)) {
                encounter.getMonsters().remove(i);
            } else {
                i++;
            }
        }
    }

    /**
     * Method that counts the number of a certain monster based on the encounter list.
     *
     * @param encounter the encounter where the monster is
     * @param i  the monster to count
     * @return the total amount of that monster in the encounter
     */
    public int getQuantityOfMonster(Encounter encounter, int i){
        ArrayList<Monster> monstersSummarized = new ArrayList<>();
        int quantity = 0;
        for (Monster m : encounter.getMonsters()) {
            if (!monstersSummarized.contains(m)) {
                monstersSummarized.add(m);
            }
        }
        for (int j = 0; j < encounter.getMonsters().size(); j++) {
            if(Objects.equals(encounter.getMonsters().get(j).getName(), monstersSummarized.get(i).getName()))
                quantity++;
        }
        return quantity;
    }

    /**
     * Method that gets the name of a certain monster based on the encounter list.
     *
     * @param encounter the encounter where the monster is
     * @param i  the monster to find
     * @return the name of the requested monster
     */
    public String getNameOfMonster(Encounter encounter, int i){
        ArrayList<Monster> monstersSummarized = new ArrayList<>();
        for (Monster m : encounter.getMonsters()) {
            if (!monstersSummarized.contains(m)) {
                monstersSummarized.add(m);
            }
        }
        return monstersSummarized.get(i).getName();
    }

    /**
     * Method that searches for existing adventures
     *
     * @return an array list containing object representations of all adventures
     */
    public ArrayList<Adventure> findAdventures() {
        try {
            ArrayList<Adventure> adventures;

            adventures = daoAdventure.getAll();

            return adventures;
        } catch (IOException ignored) {
            return new ArrayList<Adventure>();
        }
    }

    /**
     * Method that checks whether the name that was input by the user already exists in the party.
     *
     * @param name a string representation of the queried party's name
     * @return whether the character has already been chosen or not
     */
    public boolean checkCharacterInParty(String[] partyNames, String name) {
        boolean inParty = false;
        for (String partyName : partyNames) {
            if (Objects.equals(name, partyName)) {
                inParty = true;
            }
        }
        return inParty;
    }

    /**
     * Method that sets the current HP of the monsters in the whole adventure.
     *
     * @param adventureToPlay the adventure to be played
     * @return NONE
     */
    public void initializeCurrentHPMonsters(Adventure adventureToPlay){
        for (Encounter encounter : adventureToPlay.getEncounters()) {
            for (Monster monster : encounter.getMonsters()) {
                monster.setCurrentHp(monster.getHitPoints());
            }
        }
    }


    /**
     * Method that makes each character take their respective preparation stage action
     *
     * @param adventureParty an ArrayList representation of the party's characters
     * @return the ArrayList containing the characters
     */
    public ArrayList<Character> preparationStage(ArrayList<Character> adventureParty){
        for (Character character : adventureParty) {
            character.usePreparationStageAction();
        }
        return adventureParty;
    }

    /**
     * Method that modifies the initiative values of the characters in the party and the monsters in the encounter.
     *
     * @param adventureParty an ArrayList representation of the party's characters
     * @param encounter an Encounter representation of the encounter
     * @return NONE
     */
    public void setInitiativeValues(ArrayList<Character> adventureParty, Encounter encounter){
        String class_;
        Random dice = new Random();
        int initiative;
        for (Character character : adventureParty) {
            class_ = character.getClass_();
            switch(class_){
                case "Adventurer":
                    initiative = dice.nextInt(12) + 1 + character.getSpirit();
                    character.setInitiative(initiative);
                    break;
            }
        }
        for (Monster monster : encounter.getMonsters()) {
            initiative = dice.nextInt(12) + 1 + monster.getInitiative();
            monster.setInitiative(initiative);
        }
    }

    /**
     * Method that sets the combat order of the characters in the party and the monsters in the encounter.
     *
     * @param adventureParty an ArrayList representation of the party's characters
     * @param encounter an Encounter representation of the encounter
     * @return String array with names containing the combat order
     */
    public void setCombatOrder(ArrayList<Character> adventureParty, Encounter encounter){
        int monsterCounter = 0;
        int characterCounter = 0;
        int i = 0;
        int totalCombatants = adventureParty.size() + encounter.getMonsters().size();
        encounter.getMonsters().sort(Comparator.comparing(Monster::getInitiative));
        Collections.reverse(encounter.getMonsters());
        adventureParty.sort(Comparator.comparing(Character::getInitiative));
        Collections.reverse(adventureParty);
        while (i < totalCombatants) {
            if (monsterCounter < encounter.getMonsters().size() && characterCounter < adventureParty.size()) {
                if (adventureParty.get(characterCounter).getInitiative() >
                        encounter.getMonsters().get(monsterCounter).getInitiative()) {
                    adventureParty.get(characterCounter).setCombatOrder(i);
                    characterCounter++;
                    i++;
                } else {
                    encounter.getMonsters().get(monsterCounter).setCombatOrder(i);
                    monsterCounter++;
                    i++;
                }
            } else {
                if (characterCounter == adventureParty.size()) {
                    encounter.getMonsters().get(monsterCounter).setCombatOrder(i);
                    monsterCounter++;
                    i++;
                } else {
                    adventureParty.get(characterCounter).setCombatOrder(i);
                    characterCounter++;
                    i++;
                }

            }

        }
    }

    /**
     * Method that checks whether the party has any characters conscious.
     *
     * @param adventureParty the characters to check
     * @return whether the party has any characters conscious or not
     */
    public boolean checkTPU(ArrayList<Character> adventureParty) {
        boolean tpu = true;
        for (Character character : adventureParty) {
            if (character.getCurrentHp() > 0) {
                tpu = false;
            }
        }
        return tpu;
    }

    /**
     * Method that checks whether the encounter has any monsters alive.
     *
     * @param encounter the encounter to check
     * @return whether the encounter has any monsters conscious or not
     */
    public boolean checkMonstersDefeated(Encounter encounter) {
        boolean monstersDefeated = true;
        for (Monster monster : encounter.getMonsters()) {
            if (monster.getCurrentHp() > 0) {
                monstersDefeated = false;
            }
        }
        return monstersDefeated;
    }
    /**
     * Method that generates the combat stage actions of the round.
     *
     * @param adventureParty the characters involved in combat
     * @param encounter the encounter involved
     * @param i the combatant that should take their action
     * @return the diceRoll and the damage
     */
    public int[] combatStageActions(ArrayList<Character> adventureParty, Encounter encounter, int i){
        int[] combatOutputs = new int[2];
        int targetHP = Integer.MAX_VALUE;
        int index_monster = 0;
        Random dice = new Random();
        int hit = dice.nextInt(10) + 1;
        int damage = 0;
        boolean correctTarget = false;
            //search if the next action should be taken by a character
            for (Character character : adventureParty) {
                if (character.getCombatOrder() == i && character.getCurrentHp() > 0) {

                    switch (character.getClass_()) {
                        //if the character is an adventurer, they will attack a monster
                        case "Adventurer":
                            damage = dice.nextInt(6) + 1 + character.getBody();
                            if (hit == 10) {
                                damage = damage * 2;
                            }
                            if (hit == 1) {
                                damage = 0;
                            }
                            //find the monster with the lowest HP
                            for (int j = 0; j < encounter.getMonsters().size(); j++) {
                                if (encounter.getMonsters().get(j).getCurrentHp() < targetHP) {
                                    targetHP = encounter.getMonsters().get(j).getCurrentHp();
                                    index_monster = j;
                                }
                            }
                            //attack the monster with the lowest HP
                            encounter.getMonsters().get(index_monster).
                                    setCurrentHp(encounter.getMonsters().get(index_monster).getCurrentHp() - damage);
                            //mark it as targeted for future showing of the action
                            encounter.getMonsters().get(index_monster).setTargeted(true);
                            break;
                    }


                }
            }

            //search if the action should be taken by a monster
            for (int j = 0; j < encounter.getMonsters().size(); j++) {
                if (encounter.getMonsters().get(j).getCombatOrder() == i && encounter.getMonsters().get(j).getCurrentHp() > 0) {
                    //get its dice roll and damage
                    int maxDice = encounter.getMonsters().get(j).getDamageDice();
                    damage = dice.nextInt(maxDice) + 1;
                    if (hit == 10) {
                        damage = damage * 2;
                    }
                    if (hit == 1) {
                        damage = 0;
                    }

                    //if the monster is not a boss, it will attack a random character
                    if(encounter.getMonsters().get(j).getChallenge() != "Boss") {
                        //randomly target a character
                        while (!correctTarget) {
                            int index_character = dice.nextInt(adventureParty.size());
                            //only target a character if they are conscious
                            if (adventureParty.get(index_character).getCurrentHp() > 0) {
                                adventureParty.get(index_character).setCurrentHP
                                        (adventureParty.get(index_character).getCurrentHp() - damage);
                                //if their HP is less than 0, set it to 0
                                if (adventureParty.get(index_character).getCurrentHp() < 0) {
                                    adventureParty.get(index_character).setCurrentHP(0);
                                }
                                //mark the character as targeted for future showing of the action
                                correctTarget = true;
                                adventureParty.get(index_character).setTargeted(true);
                            }
                        }
                    }
                    //if the monster is a boss it will attack all conscious characters
                    else {
                        for (int k = 0; k < adventureParty.size(); k++) {
                            if (adventureParty.get(k).getCurrentHp() > 0){
                                adventureParty.get(k).setCurrentHP(adventureParty.get(k).getCurrentHp() - damage);
                                adventureParty.get(k).setTargeted(true);
                            }
                        }
                    }

                }
            }

        combatOutputs[0] = hit;
        combatOutputs[1] = damage;
        
        return combatOutputs;

    }

    /**
     * Method that updates the characters and monsters after a combat.
     *
     * @param adventureParty the characters involved in combat
     * @param encounter the encounter involved
     * @return NONE
     */
    public void updateCombatants(ArrayList<Character> adventureParty, Encounter encounter) {
        for (Character character : adventureParty) {
            if (character.isTargeted()) {
                character.setTargeted(false);
            }
        }
        for (int i = 0; i < encounter.getMonsters().size(); i++) {
            if(encounter.getMonsters().get(i).isTargeted()){
                encounter.getMonsters().get(i).setTargeted(false);
            }
            if (encounter.getMonsters().get(i).getCurrentHp() <= 0) {
                encounter.getMonsters().remove(i);
                i--;
            }
        }
    }

    /**
     * Method that calculates the total experience gained from the encounter.
     *
     * @param encounter the encounter involved
     * @return the total experience gained
     */
    public int getTotalXP(Encounter encounter) {
        int totalXP = 0;
        for (Monster monster : encounter.getMonsters()) {
            totalXP = totalXP + monster.getExperience();
        }
        return totalXP;
    }
}
