package business;

import business.entities.Adventure;
import business.entities.Character;
import business.entities.Encounter;
import business.entities.Monster;
import persistence.exceptions.MonsterFileNotFoundException;
import persistence.exceptions.PersistenceException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that implements the methods described in the {@link BusinessFacade} interface, and will be used as a way to
 * physically separate the business layer from the rest of the application.
 *
 * <p>Its implementation is also separated from lower layers, as it uses managers to handle that communication.
 */
public class BusinessFacadeImpl implements BusinessFacade {

    // Manager for the application's characters
    private CharacterManager characterManager;
    private MonsterManager monsterManager;
    private AdventureManager adventureManager;

    /**
     * Default constructor, which initializes the managers needed to abstract this class from the rest of the layer.
     *
     * @throws PersistenceException if the managers can't be instantiated because of errors in lower layers
     */
    public BusinessFacadeImpl() throws PersistenceException, MonsterFileNotFoundException {
        characterManager = new CharacterManager();
        monsterManager = new MonsterManager();
        adventureManager = new AdventureManager();
    }


    /**
     * Method that checks whether the name has any special characters.
     * @param provisionalName
     * @return a boolean that indicates whether it is correct or not.
     */
    @Override
    public boolean correctName (String provisionalName){
        return characterManager.correctName(provisionalName);
    }

    /**
     * Method that gets a name and makes it so that it has the correct casing.
     * @param provisionalName
     * @return a string representation of a character's name with the correct casing.
     */
    @Override
    public String correctCasing(String provisionalName) {
        return characterManager.correctCasing(provisionalName);
    }


    @Override
    public boolean checkUniqueNameCharacter(String characterName) throws PersistenceException, IOException {
        return characterManager.checkUniqueName(characterName);
    }

    /**
     * Method that adds a character to the system.
     *
     * @param characterName a string representation of the character's name
     * @param playerName a string representation of the character's creator
     * @param level an int representation of the character's level which is turned to xp
     * @param stats an array of ints that represents the character's statistics
     * @return whether the student was successfully created or not
     */
    @Override
    public boolean addNewCharacter(String characterName, String playerName, int level, int[] stats) {
        return characterManager.addNewCharacter(characterName, playerName, level, stats);
    }

    @Override
    /**
     * Method that searches existing characters for those owned by a given player name.
     *
     * @param playerName a string representation of the name of the player whose characters we need to look for
     * @return an array list containing object representations of all characters owned by the given player
     */
    public ArrayList<Character> findCharacterByPlayerName(String playerName){
        return characterManager.findCharacterByPlayerName(playerName);
    }

    @Override
    /**
     * Method that checks whether the character choice by the user is within the bounds of the list
     *
     * @param num    an int representation of the length of the list
     * @param choice
     * @return a boolean representation of whether the choice made by the user is correct or not.
     */
    public boolean checkChoiceValid(int num, int choice){
        return characterManager.checkChoiceValid(num, choice);
    }

    /**
     * Method that extracts the names of a list of characters.
     *
     * @param characters an array list representation of characters.
     * @return an array containing string representations of all characters' names.
     */
    @Override
    public String[] extractNamesFromCharacters(ArrayList<Character> characters) {
        return characterManager.extractNamesFromCharacters(characters);
    }

    @Override
    /**
     * Method that checks whether the input of the user matches the name of the character
     *
     * @param decision    a String representation of the input of the user
     * @param character an object representation of the character whose existence is put into debate
     * @return a boolean representation of whether the input of the user matches the name of the character or not.
     */
    public boolean decisionEqualsName(String decision, Character character){
        return characterManager.decisionEqualsName(decision, character);
    }

    /**
     * Method that deletes a character from the system.
     *
     * @param character an object representation of the character to remove
     * @return whether the character was successfully deleted or not
     */
    public boolean removeCharacter(Character character){
        characterManager.removeCharacter(character);
        return true;
    }

    /**
     * Method that translates a character's experience to level
     *
     * @param xp an int representation of the character's experience
     * @return an int representation of the character's level
     */
    public int translateExpToLevel(int xp){
        return characterManager.translateExpToLevel(xp);
    }

    /**
     * Method that translates a character's level to experience
     *
     * @param level an int representation of the character's level
     * @return an int representation of the character's experience
     */
    public int translateLevelToExp(int level){
        return characterManager.translateLevelToExp(level);
    }

    /**
     * Method that checks whether the name that was input by the user already exists.
     *
     * @param name a string representation of the queried adventure's name
     * @return whether the name is already in use or not
     */
    public boolean checkUniqueNameAdventure (String name) throws IOException {
       return adventureManager.checkUniqueName(name);
    }

    /**
     * Method that adds an adventure to the system.
     *
     * @param adventureName a string representation of the adventure's name
     * @param encounters an int representation of the number of encounters in an adventure
     */
    @Override
    public boolean addNewAdventure(String adventureName, ArrayList<Encounter> encounters) {
        return adventureManager.addNewAdventure(adventureName, encounters);
    }

    public ArrayList<Monster> findMonsters(){
        return monsterManager.findMonsters();
    }

    @Override
    /**
     * Method that checks whether the max number of boss monsters in the encounter has been exceeded.
     *
     * @param encounter the encounter to check
     * @param monster the monster to check
     * @return whether the encounter passes the requirement or not
     */
    public boolean bossMonsterCheck(Encounter encounter, Monster monster, int quantity){
        return adventureManager.bossMonsterCheck(encounter, monster, quantity);
    }

    /**
     * Method that adds a monster to the encounter.
     *
     * @param encounter the encounter where the monster should be added
     * @param monster   the monster to add
     * @param quantity the quantity of the monster to add
     * @return NONE
     */
    public void addMonsterToEncounter(Encounter encounter, Monster monster, int quantity){
        adventureManager.addMonsterToEncounter(encounter, monster, quantity);
    }

    /**
     * Method that checks whether the encounter is empty.
     * @param encounter the encounter to check
     * @return whether the encounter is empty or not
     */
    public boolean checkEncounterEmpty(Encounter encounter){
        return adventureManager.checkEncounterEmpty(encounter);
    }

    /**
     * Method that removes a monster from the encounter.
     *
     * @param encounter       the encounter from which the monster should be removed
     * @param monster         the monster to remove
     * @return NONE
     */
    public void removeMonsterFromEncounter(Encounter encounter, String monster){
        adventureManager.removeMonsterFromEncounter(encounter, monster);
    }

    /**
     * Method that counts the number of a certain monster based on the encounter list.
     *
     * @param encounter the encounter where the monster is
     * @param i  the monster to count
     * @return the total amount of that monster in the encounter
     */
    public int getQuantityOfMonster(Encounter encounter, int i){
        return adventureManager.getQuantityOfMonster(encounter, i);
    }

    /**
     * Method that gets the name of a certain monster based on the encounter list.
     *
     * @param encounter the encounter where the monster is
     * @param i  the monster to find
     * @return the name of the requested monster
     */
    public String getNameOfMonster(Encounter encounter, int i){
        return adventureManager.getNameOfMonster(encounter, i);
    }

    public int checkNumCharacters(){
        return characterManager.checkNumCharacters();
    }

    public ArrayList<Adventure> findAdventures(){
        return adventureManager.findAdventures();
    }

    /**
     * Method that checks whether the name that was input by the user already exists in the party.
     *
     * @param name a string representation of the queried party's name
     * @return whether the character has already been chosen or not
     */
    public boolean checkCharacterInParty(String[] partyNames, String name){
        return adventureManager.checkCharacterInParty(partyNames, name);
    }

    /**
     * Method that creates an ArrayList of characters based on the names given.
     *
     * @param partyNames a string array representation of the party's characters
     * @return the ArrayList containing the characters
     */
    public ArrayList<Character> createAdventureParty(String[] partyNames) throws IOException {
        return characterManager.createAdventureParty(partyNames);
    }

    /**
     * Method that initializes de maxHP and currentHP of the characters in the party.
     *
     * @param character the character to initialize
     * @return NONE
     */
    public void initializeHP(Character character){
        characterManager.initializeHP(character);
    }

    /**
     * Method that makes each character take their respective preparation stage action
     *
     * @param adventureParty an ArrayList representation of the party's characters
     * @return the ArrayList containing the characters
     */
    public ArrayList<Character> preparationStage(ArrayList<Character> adventureParty){
        return adventureManager.preparationStage(adventureParty);
    }

    /**
     * Method that modifies the initiative values of the characters in the party and the monsters in the encounter.
     *
     * @param adventureParty an ArrayList representation of the party's characters
     * @param encounter an Encounter representation of the encounter
     * @return NONE
     */
    public void setInitiativeValues(ArrayList<Character> adventureParty, Encounter encounter){
        adventureManager.setInitiativeValues(adventureParty, encounter);
    }

    /**
     * Method that sets the combat order of the characters in the party and the monsters in the encounter.
     *
     * @param adventureParty an ArrayList representation of the party's characters
     * @param encounter      an Encounter representation of the encounter
     * @return String array with names containing the combat order
     */
    public void setCombatOrder(ArrayList<Character> adventureParty, Encounter encounter){
        adventureManager.setCombatOrder(adventureParty, encounter);
    }

    /**
     * Method that sets the current HP of the monsters in the whole adventure.
     *
     * @param adventureToPlay the adventure to be played
     * @return NONE
     */
    public void initializeCurrentHPMonsters(Adventure adventureToPlay){
        adventureManager.initializeCurrentHPMonsters(adventureToPlay);
    }

    /**
     * Method that checks whether the party has any characters conscious.
     *
     * @param adventureParty the characters to check
     * @return whether the party has any characters conscious or not
     */
    public boolean checkTPU(ArrayList<Character> adventureParty){
        return adventureManager.checkTPU(adventureParty);
    }

    /**
     * Method that checks whether the encounter has any monsters alive.
     *
     * @param encounter the encounter to check
     * @return whether the encounter has any monsters conscious or not
     */
    public boolean checkMonstersDefeated(Encounter encounter){
        return adventureManager.checkMonstersDefeated(encounter);
    }

    /**
     * Method that generates the combat stage actions of the round.
     *
     * @param adventureParty        the characters involved in combat
     * @param encounter             the encounter involved
     * @param i                     the combatant that should take their action

     * @return the diceRoll and the damage
     */
    public int[] combatStageActions(ArrayList<Character> adventureParty, Encounter encounter, int i){
        return adventureManager.combatStageActions(adventureParty, encounter, i);
    }

    /**
     * Method that updates the characters and monsters after a combat.
     *
     * @param adventureParty the characters involved in combat
     * @param encounter the encounter involved
     * @return NONE
     */
    public void updateCombatants(ArrayList<Character> adventureParty, Encounter encounter){
        adventureManager.updateCombatants(adventureParty, encounter);
    }

    /**
     * Method that calculates and gives the corresponding experience gained from the encounter.
     *
     * @param character the character involved in combat
     * @param XPGained  the amount of xp gained
     * @return whether the character leveled up or not
     */
    public boolean experienceGain(Character character, int XPGained){
        return characterManager.experienceGain(character, XPGained);
    }

    /**
     * Method that calculates the total experience gained from the encounter.
     *
     * @param encounter the encounter involved
     * @return the total experience gained
     */
    public int getTotalXP(Encounter encounter){
        return adventureManager.getTotalXP(encounter);
    }

    /**
     * Method that generates the short rest stage actions after the encounter
     *
     * @param character the character involved in the encounter
     * @param levelUp   whether the character leveled up or not
     * @return int representation of the amount of health the character has recovered
     */
    public int shortRestActions(Character character, boolean levelUp){
        return characterManager.shortRestActions(character, levelUp);
    }

    /**
     * Method that reverses the effects of any support actions taken during the encounter
     *
     * @param character the character involved in the encounter
     * @return NONE
     */
    public void reverseSupportActions(Character character){
        characterManager.reverseSupportActions(character);
    }

    /**
     * Method that updates the character's xp after the adventure
     *
     * @param character the character involved in the adventure
     * @return whether the character was successfully created or not
     */
    public boolean updateCharacter(Character character){
        return characterManager.updateCharacter(character);
    }


}