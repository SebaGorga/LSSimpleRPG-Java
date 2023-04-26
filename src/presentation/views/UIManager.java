package presentation.views;

import business.entities.Adventure;
import business.entities.Character;
import business.entities.Encounter;
import business.entities.Monster;

import java.util.ArrayList;

/**
 * Interface that abstracts the UI implementation by providing certain functions to use when interaction with the user is needed.
 *
 * @author Sebastián Félix Gorga & Valèria Ezquerra Rodriguez
 * @version 1.0
 */
public interface UIManager {

    /**
     * Method that shows the program's header to the user.
     *
     * @return NONE
     */
    void showHeader();

    /**
     * Method that shows the program's main menu to the user, asking them to choose an option.
     *
     * @return an item in the {@link MainMenuOptions} enumeration representing the option chosen by the user
     */
    MainMenuOptions showMainMenu(int numCharacters);

    /**
     * Method that shows the program's exit message to the user.
     *
     * @return NONE
     */
    void showExitMessage();

    /**
     * Method that prompts the user to enter a character's name.
     *
     * @return a string representation of a character's name.
     */
    String requestCharacterName();

    /**
     * Method that shows the program's error message when the input name is incorrect.
     *
     * @return NONE
     */
    void showErrorName();

    /**
     * Method that shows the program's error message when the input name already exists in persistence.
     *
     * @return NONE
     */
    void showNotUniqueMessage();

    /**
     * Method that prompts the user to enter a character's player.
     * @param characterName a string representation of the character's name
     * @return a string representation of a character's player.
     */
    String requestPlayerNameToAdd(String characterName);

    /**
     * Method that prompts the user to enter a character's experience.
     *
     * @return an integer representation of a character's experience.
     */
    int requestCharacterLevel();

    /**
     * Method that generates the character's stats and shows them to the user.
     *
     * @return an array of integers representing the character's stats.
     */
    int[] generateStats();

    /**
     * Method that lets the user know that the character has been successfully created.
     * @param characterName string representation of the character's name
     * @return NONE.
     */
    void showCharacterCreationStatus(String characterName);

    /**
     * Method that prompts the user to enter a character's player.
     *
     * @return a string representation of a character's player.
     */
    String requestPlayerNameToList();

    /**
     * Method that shows the user a meesage indicating that some characters are available.
     *
     * @return NONE.
     */
    void showCharacterListMessage();

    /**
     * Method that shows the user the character's that are owned by the requested player.
     * @param names array of strings that represent the names of the characters
     * @return NONE
     */
    void showCharacterList(String[] names);

    /**
     * Method that prompts the user to enter an int representing one of the characters in a list.
     *
     * @return an int representation of the choice of the user.
     */
    int requestCharacterFullInfo(String[] names);

    /**
     * Method that shows the user an error message when they choose an invalid option
     *
     * @return NONE
     */
    void showErrorWrongOptionMessage();

    /**
     * Method that shows the complete information of a character
     * @param character character whose information is shown
     * @param level current level of the character
     * @return NONE
     */
    void showCharacterFullInfo(Character character, int level);

    /**
     * Method that prompts the user to enter a character's name to remove.
     * @param character character whose existence is put to question
     * @return a string representation of a character's name.
     */
    String askToRemove(Character character);

    /**
     * Method that shows the character removed message to the user.
     * @param character character that will leave the guild
     * @return NONE
     */
    void showCharacterRemoved(Character character);

    /**
     * Method that prompts the user to enter an adventure's name.
     *
     * @return a string representation of a adventure's name.
     */
    String requestAdventureName();

    /**
     * Method that prompts the user to enter the adventure's number of encounters.
     *
     * @return an int representation of the adventure's number of emcounters.
     */
    int requestNumberEncounters(String name);

    /**
     * Method that shows the user an error message when they choose a number of encounters outside the range
     *
     * @return NONE
     */
    void showErrorEncounter();

    /**
     * Method that shows the user a message showing how many encounters they have selected
     * @param encounters int representation of the number of encounters selected
     * @return NONE
     */
    void showNumEncountersMessage(int encounters);

    /**
     * Method that shows the program's encounter menu to the user, asking them to choose an option.
     *
     * @return an item in the {@link EncounterMenuOptions} enumeration representing the option chosen by the user
     */
    EncounterMenuOptions showEncounterMenu(int optionEncounters, Encounter encounter, int numEncounters);

    /**
     * Method that prompts the user to enter the monster to add.
     *
     * @return an int representation of the monster to add according to the list.
     */
    int requestMonsterToAdd(int num);

    /**
     * Method that prompts the user to enter the number of that type of monster to add.
     *
     * @return an int representation of the number of monsters to add.
     */
    int requestQuantityMonsters(String name);

    /**
     * Method that shows the user a list of all the monsters
     *
     * @return NONE.
     */
    void showMonstersList(ArrayList<Monster> monsters);

    /**
     * Method that shows the user a message showing they have included more than one boss monster
     *
     * @return NONE
     */
    void showErrorBossMonsterMessage();

    /**
     * Method that shows the user a message showing they still have to add monsters to the encounter
     *
     * @return NONE
     */
    void showErrorEmptyEncounter();

    /**
     * Method that prompts the user to enter the monster to remove.
     *
     * @return an int representation of the monster to remove according to the list.
     */
    int requestMonsterToRemove(int size);

    /**
     * Method that shows the user a message showing they have removed a monster from the encounter
     *
     * @return NONE
     */
    void showMonsterRemoved(String name, int quantity);

    /**
     * Method that shows a message before playing an adventure
     *
     * @return NONE
     */
    void showAdventureMessage();

    /**
     * Method that prompts the user to enter the adventure to play.
     *
     * @return an int representation of the adventure to play.
     */
    int requestAdventureToPlay();

    /**
     * Method that shows the user a list of all the adventures
     *
     * @return NONE.
     */
    void showAdventuresList(ArrayList<Adventure> adventures);

    /**
     * Method that requests the user to enter the number of characters to play the adventure.
     *
     * @return an int representation of the number of characters.
     */
    int requestNumCharacters(String adventure);

    /**
     * Method that shows the user the current condition of the party
     *
     * @return NONE
     */
    void showParty(int i, int party, String[] partyNames);

    /**
     * Method that prompts the user to enter the character to add to the party.
     *
     * @return an int representation of the character to add according to the list.
     */
    int requestCharacterParty(int index);

    /**
     * Method that shows the user a message indicating that they do not have enough characters to play the adventure
     *
     * @return NONE
     */
    void showNotEnoughCharacters();

    /**
     * Method that shows the user a message indicating how many characters will be playing the adventure
     *
     * @return NONE
     */
    void showCharacterChoosingMessage(int party);

    /**
     * Method that shows the user the message "Available characters"
     *
     * @return NONE
     */
    void showAvailableMessage();

    /**
     * Method that shows the user the information about their complete party
     *
     * @return NONE
     */
    void showFullParty(int party, String[] partyNames, String name);

    /**
     * Method that shows the user a message indicating that the character is already in the party
     *
     * @return NONE.
     */
    void showErrorCharacterInParty();

    /**
     * Method that shows the user a message showing the description of the encounter
     *
     * @return NONE.
     */
    void showEncounterDescription(int numEncounters, Encounter encounter);

    /**
     * Method that shows the user a message showing the preparation stage actions of each character.
     *
     * @return NONE.
     */
    void showPreparationStageActions(ArrayList<Character> adventureParty);

    /**
     * Method that shows the user the order in which the characters and monsters will fight in the encounter
     *
     * @return NONE.
     */
    void showCombatOrder(ArrayList<Character> adventureParty, Encounter encounter);

    /**
     * Method that shows the user the message "Combat stage"
     *
     * @return NONE.
     */
    void showCombatStageSign();

    /**
     * Method that shows the user the current round and the HP of each character
     *
     * @return NONE.
     */
    void showRoundAndHP(int counterRound, ArrayList<Character> adventureParty);

    /**
     * Method that shows the user the information about the combat action
     * @param hit int representation of the dice roll, indicating whether the attack was successful or not
     * @param damage int representation of the damage dealt
     * @param adventureParty ArrayList of characters that are playing the adventure
     * @param encounter Encounter that is being played
     * @param i int representation of the combatant that took the action
     * @return NONE.
     */
    void showCombatAction(int hit, int damage, ArrayList<Character> adventureParty, Encounter encounter, int i);

    /**
     * Method that shows the user the message "End of round"
     * @param counterRound int representation of the current round
     * @return NONE.
     */
    void endOfRoundMessage(int counterRound);

    /**
     * Method that shows the user a defeat message
     * @return NONE.
     */
    void showDefeatMessage();

    /**
     * Method that shows the user an encounter victory message
     * @return NONE.
     */
    void showEncounterVictoryMessage();

    /**
     * Method that shows the user a message indicating that the character has gained experience
     * and, if applicable, has leveled up
     *
     * @param character Character that is dead
     * @param xpGained  int representation of the experience gained
     * @param levelUp   boolean indicating whether the character has leveled up or not
     * @param level    int representation of the character's level
     * @return NONE.
     */
    void showExperienceGain(Character character, int xpGained, boolean levelUp, int level);

    /**
     * Method that shows the user a message indicating that the character has died
     *
     * @param character Character that is dead
     * @param healing  int representation of the healing done
     * @return NONE.
     */
    void showShortRestActions(Character character, int healing);

    /**
     * Method that shows the user the short rest header
     * @return NONE.
     */
    void showShortRestHeader();

    /**
     * Method that shows the user the adventure won message
     * @param adventureName String representation of the adventure name
     * @return NONE.
     */
    void showAdventureVictoryMessage(String adventureName);

    /**
     * Method that shows the user the persistence error message.
     * @return NONE.
     */
    void printLoadingScreenError(String message);
}

