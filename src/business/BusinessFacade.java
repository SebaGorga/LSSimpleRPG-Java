package business;

import business.entities.Adventure;
import business.entities.Character;
import business.entities.Encounter;
import business.entities.Monster;
import persistence.exceptions.PersistenceException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface that defines how the business layer is separated from upper ones.
 *
 * <p>In particular, it follows the facade design pattern, as it's meant to completely separate the implementation of
 * the business layer's logic from the rest of the application, particularly upper layers such as the presentation one.
 *
 * @author DPOO 20-21 Team
 * @version 1.0
 */
public interface  BusinessFacade {


    boolean checkUniqueNameCharacter(String characterName) throws PersistenceException, IOException;

    /**
     * Method that checks whether the name has any special characters.
     * @param provisionalName
     * @return a boolean that indicates whether it is correct or not.
     */
    boolean correctName(String provisionalName);


    /**
     * Method that gets a name and makes it so that it has the correct casing.
     *
     * @return a string representation of a character's name with the correct casing.
     */
    String correctCasing(String provisionalName);

    /**
     * Method that adds a character to the system.
     *
     * @param characterName a string representation of the character's name
     * @param playerName a string representation of the character's creator
     * @param level an int representation of the character's level which is turned to xp
     * @param stats an array of ints that represents the character's statistics
     * @return whether the character was successfully created or not
     */
    boolean addNewCharacter(String characterName, String playerName, int level, int[] stats);

    /**
     * Method that searches existing characters for those owned by a given player name.
     *
     * @param playerName a string representation of the name of the player whose characters we need to look for
     * @return an array list containing object representations of all characters owned by the given player
     */
    ArrayList<Character> findCharacterByPlayerName(String playerName);

    /**
     * Method that checks whether the character choice by the user is within the bounds of the list
     *
     * @param num    an int representation of the length of the list
     * @param choice an int representation of the decision made by the user
     * @return a boolean representation of whether the choice made by the user is correct or not.
     */
    boolean checkChoiceValid(int num, int choice);

    /**
     * Method that extracts the names of a list of characters.
     *
     * @param characters an array list representation of characters.
     * @return an array containing string representations of all characters' names.
     */
    String[] extractNamesFromCharacters(ArrayList<Character> characters);

    /**
     * Method that checks whether the input of the user matches the name of the character
     *
     * @param decision    a String representation of the input of the user
     * @param character an object representation of the character whose existence is put into debate
     * @return a boolean representation of whether the input of the user matches the name of the character or not.
     */
    boolean decisionEqualsName(String decision, Character character);

    /**
     * Method that deletes a character from the system.
     *
     * @param character an object representation of the character to remove
     * @return whether the character was successfully deleted or not
     */
    boolean removeCharacter(Character character);

    /**
     * Method that translates a character's experience to level
     *
     * @param xp an int representation of the character's experience
     * @return an int representation of the character's level
     */
    int translateExpToLevel(int xp);

    /**
     * Method that translates a character's level to experience
     *
     * @param level an int representation of the character's level
     * @return an int representation of the character's experience
     */
    int translateLevelToExp(int level);

    /**
     * Method that checks whether the name that was input by the user already exists.
     *
     * @param name a string representation of the queried adventure's name
     * @return whether the name is already in use or not
     */
    boolean checkUniqueNameAdventure (String name) throws IOException;

    /**
     * Method that adds an adventure to the system.
     *
     * @param adventureName a string representation of the adventure's name
     * @param encounters an int representation of the number of encounters in an adventure
     */
    boolean addNewAdventure(String adventureName, ArrayList<Encounter> encounters);

    ArrayList<Monster> findMonsters();

    /**
     * Method that checks whether the max number of boss monsters in the encounter has been exceeded.
     *
     * @param encounter the encounter to check
     * @param monster   the monster to check
     * @param quantity
     * @return whether the encounter passes the requirement or not
     */
    boolean bossMonsterCheck(Encounter encounter, Monster monster, int quantity);

    /**
     * Method that adds a monster to the encounter.
     *
     * @param encounter the encounter where the monster should be added
     * @param monster   the monster to add
     * @param quantity the quantity of the monster to add
     * @return NONE
     */
    void addMonsterToEncounter(Encounter encounter, Monster monster, int quantity);

    /**
     * Method that checks whether the encounter is empty.
     * @param encounter the encounter to check
     * @return whether the encounter is empty or not
     */
    boolean checkEncounterEmpty(Encounter encounter);

    /**
     * Method that removes a monster from the encounter.
     *
     * @param encounter       the encounter from which the monster should be removed
     * @param monster         the monster to remove
     * @return NONE
     */
    void removeMonsterFromEncounter(Encounter encounter, String monster);

    /**
     * Method that counts the number of a certain monster based on the encounter list.
     *
     * @param encounter the encounter where the monster is
     * @param i  the monster to count
     * @return the total amount of that monster in the encounter
     */
    int getQuantityOfMonster(Encounter encounter, int i);

    /**
     * Method that gets the name of a certain monster based on the encounter list.
     *
     * @param encounter the encounter where the monster is
     * @param i  the monster to find
     * @return the name of the requested monster
     */
    String getNameOfMonster(Encounter encounter, int i);

    int checkNumCharacters();

    ArrayList<Adventure> findAdventures();

    /**
     * Method that checks whether the name that was input by the user already exists in the party.
     *
     * @param name a string representation of the queried party's name
     * @return whether the character has already been chosen or not
     */
    boolean checkCharacterInParty(String[] partyNames, String name);

    /**
     * Method that creates an ArrayList of characters based on the names given.
     *
     * @param partyNames a string array representation of the party's characters
     * @return the ArrayList containing the characters
     */
    ArrayList<Character> createAdventureParty(String[] partyNames) throws IOException;

    /**
     * Method that initializes de maxHP and currentHP of the characters in the party.
     *
     * @param character the character to initialize
     * @return the ArrayList containing the characters
     */
    void initializeHP(Character character);

    /**
     * Method that makes each character take their respective preparation stage action
     *
     * @param adventureParty an ArrayList representation of the party's characters
     * @return the ArrayList containing the characters
     */
    ArrayList<Character> preparationStage(ArrayList<Character> adventureParty);

    /**
     * Method that modifies the initiative values of the characters in the party and the monsters in the encounter.
     *
     * @param adventureParty an ArrayList representation of the party's characters
     * @param encounter an Encounter representation of the encounter
     * @return NONE
     */
    void setInitiativeValues(ArrayList<Character> adventureParty, Encounter encounter);

    /**
     * Method that sets the combat order of the characters in the party and the monsters in the encounter.
     *
     * @param adventureParty an ArrayList representation of the party's characters
     * @param encounter      an Encounter representation of the encounter
     * @return NONE
     */
    void setCombatOrder(ArrayList<Character> adventureParty, Encounter encounter);

    /**
     * Method that sets the current HP of the monsters in the whole adventure.
     *
     * @param adventureToPlay the adventure to be played
     * @return NONE
     */
    void initializeCurrentHPMonsters(Adventure adventureToPlay);

    /**
     * Method that checks whether the party has any characters conscious.
     *
     * @param adventureParty the characters to check
     * @return whether the party has any characters conscious or not
     */
    boolean checkTPU(ArrayList<Character> adventureParty);

    /**
     * Method that checks whether the encounter has any monsters alive.
     *
     * @param encounter the encounter to check
     * @return whether the encounter has any monsters conscious or not
     */
    boolean checkMonstersDefeated(Encounter encounter);

    /**
     * Method that generates the combat stage actions of the round.
     *
     * @param adventureParty        the characters involved in combat
     * @param encounter             the encounter involved
     * @param i                     the combatant that should take their action
     * @return the diceRoll and the damage
     */
    int[] combatStageActions(ArrayList<Character> adventureParty, Encounter encounter, int i);

    /**
     * Method that updates the characters and monsters after a combat.
     *
     * @param adventureParty the characters involved in combat
     * @param encounter the encounter involved
     * @return NONE
     */
    void updateCombatants(ArrayList<Character> adventureParty, Encounter encounter);

    /**
     * Method that calculates and gives the corresponding experience gained from the encounter.
     *
     * @param character the character involved in combat
     * @param XPGained  the amount of xp gained
     * @return whether the character leveled up or not
     */
    boolean experienceGain(Character character, int XPGained);

    /**
     * Method that calculates the total experience gained from the encounter.
     *
     * @param encounter the encounter involved
     * @return the total experience gained
     */
    int getTotalXP(Encounter encounter);

    /**
     * Method that generates the short rest stage actions after the encounter
     *
     * @param character the character involved in the encounter
     * @param levelUp   whether the character leveled up or not
     * @return int representation of the amount of health the character has recovered
     */
    int shortRestActions(Character character, boolean levelUp);

    /**
     * Method that reverses the effects of any support actions taken during the encounter
     *
     * @param character the character involved in the encounter
     * @return NONE
     */
    void reverseSupportActions(Character character);

    /**
     * Method that updates the character's xp after the adventure
     *
     * @param character the character involved in the adventure
     * @return whether the character was successfully updated or not
     */
    boolean updateCharacter(Character character);
}

