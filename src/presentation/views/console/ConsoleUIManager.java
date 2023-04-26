package presentation.views.console;

import business.entities.Adventure;
import business.entities.Character;
import business.entities.Encounter;
import business.entities.Monster;
import presentation.views.EncounterMenuOptions;
import presentation.views.MainMenuOptions;
import presentation.views.UIManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implementation of the {@link ConsoleUIManager} interface that provides interaction with the user.
 *
 * <p>In particular, it provides a Console-based User Interface implementation.
 *
 * @author Sebastián Félix Gorga & Valèria Ezquerra Rodriguez
 * @version 1.0
 */
public class ConsoleUIManager implements UIManager {

    // Scanner to interact with the user in the Console
    private final Scanner scanner;

    /**
     * Default constructor (without parameters) that initializes the manager
     */
    public ConsoleUIManager() {
        scanner = new Scanner(System.in);
    }

    // Constants used in the next method
    private static final String HEADER =    "   ____ _               __       __    ____ ___   ___   _____\n" +
                                            "  / __/(_)__ _   ___   / /___   / /   / __// _ \\ / _ \\ / ___/\n" +
                                            " _\\ \\ / //  ' \\ / _ \\ / // -_) / /__ _\\ \\ / , _// ___// (_ /\n" +
                                            "/___//_//_/_/_// .__//_/ \\__/ /____//___//_/|_|/_/    \\___/\n" +
                                            "              /_/";
    private static final String WELCOME_MESSAGE = "Welcome to Simple LSRPG.\n\n" +
                                                    "Loading data...\n";
    /**
     * Method that shows the program's header and welcomes the user.
     *
     * @return NONE
     */
    @Override
    public void showHeader() {

        System.out.println(HEADER);
        System.out.println(WELCOME_MESSAGE);
    }

    // Constants used in the next method
    private static final String ENTER_OPTION = "\nYour answer: ";
    private static final String MAIN_MENU_MESSAGE =
            "The tavern keeper looks at you and says:\n" +
                    "“Welcome adventurer! How can I help you?”\n\n" +
                    "    1) Character creation\n" +
                    "    2) List characters\n" +
                    "    3) Create an adventure";

    private static final String DISABLED_ADVENTURE =    "    4) Start an adventure (disabled: create 3 characters first) \n" +
                                                        "    5) Exit";
    private static final String AVAILABLE_ADVENTURE =   "    4) Start an adventure\n" +
                                                        "    5) Exit";
    private static final String ERROR_WRONG_OPTION = "\nError, the entered option is not a valid option.\n";

    /**
     * Method that shows the program's main menu to the user, asking them to choose an option.
     *
     * @return an item in the {@link MainMenuOptions} enumeration representing the option chosen by the user
     */
    @Override
    public MainMenuOptions showMainMenu(int numCharacters) {
        do {
            System.out.println(MAIN_MENU_MESSAGE);
            if(numCharacters < 3){
                System.out.println(DISABLED_ADVENTURE);
            }
            else{
                System.out.println(AVAILABLE_ADVENTURE);
            }
            System.out.print(ENTER_OPTION);

            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1: return MainMenuOptions.CHARACTER_CREATION;
                    case 2: return MainMenuOptions.LIST_CHARACTERS;
                    case 3: return MainMenuOptions.CREATE_ADVENTURE;
                    case 4: return MainMenuOptions.START_ADVENTURE;
                    case 5: return MainMenuOptions.EXIT;
                    default: throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(ERROR_WRONG_OPTION);
            }
        } while(true);
    }

    // Constant used in the next method
    private static final String EXIT_MESSAGE = "\nTavern keeper: “Are you leaving already? See you soon, adventurer.”";

    /**
     * Method that shows the user a goodbye message
     *
     * @return NONE
     */
    @Override
    public void showExitMessage() {
        System.out.println(EXIT_MESSAGE);
    }

    // Constant used in the next method
    private static final String ENTER_CHARACTER_NAME = "\nTavern keeper: “Oh, so you are new to this land.”\n" +
                                                        "“What’s your name?”\n\n" +
                                                        "-> Enter your name: ";

    /**
     * Method that prompts the user to enter the character name.
     *
     * @return a string representation of the character name.
     */
    @Override
    public String requestCharacterName() {
        System.out.print(ENTER_CHARACTER_NAME);
        return scanner.nextLine();
    }

    // Constant used in the next method
    private static final String ERROR_WRONG_NAME = "\nError, the entered name contains special characters or numbers.\n";

    /**
     * Method that shows the program's error message when the input name is not valid.
     *
     * @return NONE
     */
    @Override
    public void showErrorName(){
        System.out.println(ERROR_WRONG_NAME);
    }

    // Constant used in the next method
    private static final String ERROR_NOT_UNIQUE_NAME = "\nError, the entered name already exists.\n";

    /**
     * Method that shows the program's error message when the input name already exists in persistence.
     *
     * @return NONE
     */
    public void showNotUniqueMessage(){
        System.out.println(ERROR_NOT_UNIQUE_NAME);
    }

    // Constant used in the next method
    private static final String ENTER_PLAYER_NAME = "And now, if I may break the fourth wall, who is your Player?\n\n"
                                                    + "-> Enter the player’s name: ";
    /**
     * Method that prompts the user to enter the player's name.
     * @param characterName a string representation of the character's name
     * @return a string representation of the character player.
     */
    @Override
    public String requestPlayerNameToAdd(String characterName) {
        System.out.println("\nTavern keeper: “Hello, " + characterName + ", be welcome.”");
        System.out.print(ENTER_PLAYER_NAME);
        return scanner.nextLine();
    }

    // Constant used in the next method
    private static final String ENTER_CHARACTER_XP = "\nTavern keeper: “I see, I see...\n" +
                                                    "Now, are you an experienced adventurer?\n"+
                                                    "\n-> Enter the character’s level [1..10]: ";
    /**
     * Method that prompts the user to enter the experience level.
     *
     * @return an integer representation of the character's experience.
     */
    @Override
    public int requestCharacterLevel() {

        do{
            System.out.print(ENTER_CHARACTER_XP);

            try{
                int xp = Integer.parseInt(scanner.nextLine());
                if(xp > 0 && xp < 11){
                    return xp;
                }
                else{
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(ERROR_WRONG_OPTION);
            }

        }while(true);
    }

    // Constant used in the next method
    private static final String PRINT_STATS = "\nGenerating your stats...\n";

    /**
     * Method that generates the character's stats and shows them to the user.
     *
     * @return an array of integers representing the character's stats.
     */
    @Override
    public int[] generateStats(){
        int[] stats = new int[3];

        int[] results = new int[2];

        System.out.println(PRINT_STATS);

        for (int i = 0; i < stats.length; i++) {

            results[0] = (int) (Math.random() * 6 + 1);
            results[1] = (int) (Math.random() * 6 + 1);

            int sum = results[0] + results[1];

            switch (sum) {
                case 2:
                    stats[i] = -1;
                    break;
                case 3:
                case 4:
                case 5:
                    stats[i] = 0;
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                    stats[i] = 1;
                    break;
                case 10:
                case 11:
                    stats[i] = 2;
                    break;
                case 12:
                    stats[i] = 3;
                    break;
            }
            switch (i) {
                case 0:
                    System.out.println("Body:   You rolled " + sum + " (" + results[0] + " and " + results[1] + ").");
                    break;
                case 1:
                    System.out.println("Mind:   You rolled " + sum + " (" + results[0] + " and " + results[1] + ").");
                    break;
                case 2:
                    System.out.println("Spirit:   You rolled " + sum + " (" + results[0] + " and " + results[1] + ").");
                    break;
            }
        }

        System.out.println("\nYour stats are:");
        System.out.println(" - Body: " + stats[0]);
        System.out.println(" - Mind: " + stats[1]);
        System.out.println(" - Spirit: " + stats[2]);

        return stats;
    }

    /**
     * Method that lets the user know that the character has been successfully created.
     *
     * @return NONE.
     */
    @Override
    public void showCharacterCreationStatus(String name) {

        System.out.println("\nThe new character " + name + " has been created.\n");

    }

    // Constant used in the next method
    private static final String REQUEST_PLAYER_TO_LIST = "\nTavern keeper: “Lads! They want to see you!”\n" +
            "“Who piques your interest?”\n\n" +
            "-> Enter the name of the Player to filter: ";

    /**
     * Method that prompts the user to enter a character's player.
     *
     * @return a string representation of a character's player.
     */
    @Override
    public String requestPlayerNameToList(){

        System.out.print(REQUEST_PLAYER_TO_LIST);
        return scanner.nextLine();
    }

    // Constants used in the next method
    private static final String CHARACTER_LIST_MESSAGE = "\nYou watch as some adventurers get up from their chairs and approach you.\n";
    private static final String CHARACTER_LIST_EMPTY_MESSAGE = "\nThere are no characters created by the requested player name.\n";
    private static final String ZERO_OPTION = "\n0. Back\n";

    /**
     * Method that shows the user a meesage indicating that some characters are available.
     *
     * @return NONE.
     */
    public void showCharacterListMessage(){
        System.out.println(CHARACTER_LIST_MESSAGE);
    }

    /**
     * Method that shows the user a list of characters.
     *
     * @param characterNames an array of strings representing the character's names.
     * @return NONE.
     */
    @Override
    public void showCharacterList(String[] characterNames){

        if(characterNames.length == 0){
            System.out.println(CHARACTER_LIST_EMPTY_MESSAGE);
        }
        else {
            for (int i = 0; i < characterNames.length; i++) {
                System.out.println((i + 1) + ". " + characterNames[i]);
            }

        }

    }

    @Override
    /**
     * Method that prompts the user to enter an int representing one of the characters in a list.
     *
     * @return an int representation of the choice of the user.
     */
    public int requestCharacterFullInfo(String[] characterNames){
        System.out.println(ZERO_OPTION);
        System.out.print("Who would you like to meet [0.." + characterNames.length + "]: ");
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    /**
     * Method that shows the user an error message when they choose an invalid option
     *
     * @return NONE
     */
    public void showErrorWrongOptionMessage(){
        System.out.println(ERROR_WRONG_OPTION);
    }

    @Override
    /**
     * Method that shows the complete information of a character.
     * @param character the character whose info we want to show
     * @return NONE
     */
    public void showCharacterFullInfo(Character character, int level){
        System.out.println("\nTavern keeper: “Hey " + character.getName() + " get here; the boss wants to see you!”\n");
        System.out.println("* Name: " + character.getName() +
                "\n* Player: " + character.getPlayer() +
                "\n* Class: " + character.getClass_() +
                "\n* Level: " + level +
                "\n* XP: " + character.getXp() +
                "\n* Body: " + character.getBody() +
                "\n* Mind: " + character.getMind() +
                "\n* Spirit: " + character.getSpirit() + "\n");
    }

    // Constant used in the next method
    private static final String REQUEST_CHARACTER_TO_REMOVE = "[Enter name to delete, or press enter to cancel]";
    @Override
    /**
     * Method that prompts the user to enter a character's name to remove.
     *
     * @return a string representation of a character's name.
     */
    public String askToRemove(Character character){
        System.out.println(REQUEST_CHARACTER_TO_REMOVE);
        System.out.print("Do you want to delete " + character.getName() + "? ");
        return scanner.nextLine();
    }
    // Constant used in the next method
    private static final String REMOVE_CHARACTER_MESSAGE = "\nTavern keeper: “I’m sorry kiddo, but you have to leave.”\n";
    @Override
    /**
     * Method that shows the character removed message to the user.
     *
     * @return NONE
     */
    public void showCharacterRemoved(Character character){
        System.out.println(REMOVE_CHARACTER_MESSAGE);
        System.out.println("Character " + character.getName() + " left the guild.\n");
    }

    // Constant used in the next method
    private static final String ENTER_ADVENTURE_NAME = "\nTavern keeper: “Planning an adventure? Good luck with that!”\n" +
            "-> Name your adventure: ";
    @Override
    /**
     * Method that prompts the user to enter an adventure's name.
     *
     * @return a string representation of a adventure's name.
     */
    public String requestAdventureName(){
        System.out.print(ENTER_ADVENTURE_NAME);
        return scanner.nextLine();
    }


    /**
     * Method that prompts the user to enter the adventure's number of encounters.
     *
     * @return an int representation of the adventure's number of emcounters.
     */
    @Override
    public int requestNumberEncounters(String name){
        System.out.println("\nYou plan to undertake " + name + ", really?");
        System.out.println("How long will that take?\n");
        System.out.print("-> How many encounters do you want [1..4]: ");
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    /**
     * Method that shows the user an error message when they choose a number of encounters outside the range
     *
     * @return NONE
     */
    public void showErrorEncounter(){
        System.out.print(ERROR_WRONG_OPTION);
    }

    @Override
    /**
     * Method that shows the user a message showing how many encounters they have selected
     * @param encounters int representation of the number of encounters selected
     * @return NONE
     */
    public void showNumEncountersMessage(int encounters){
        System.out.println("\nTavern keeper: " + encounters + " encounters? Be ready for the challenge.\n");
    }

    // Constant used in the next method
    private static final String ENCOUNTER_MENU =    "\n1. Add monster\n"+
                                                    "2. Remove monster\n"+
                                                    "3. Continue\n";
    private static final String ENCOUNTER_MENU_OPTION = "-> Enter an option [1..3]: ";

    /**
     * Method that shows the encounters menu to the user, asking them to choose an option.
     * @param optionEncounters int representation of the encounter currently being displayed
     * @param encounter the encounter currently being displayed
     * @param numEncounters int representation of the number of encounters in the adventure
     * @return an item in the {@link EncounterMenuOptions} enumeration representing the option chosen by the user
     */
    @Override
    public EncounterMenuOptions showEncounterMenu(int optionEncounters, Encounter encounter, int numEncounters){

        do{
            System.out.println("\n* Encounter " + (optionEncounters + 1) + " / " + numEncounters);
            System.out.println("* Monsters in encounter: ");

            if(encounter.getMonsters().isEmpty()){
                System.out.println(" # Empty");
            }
            else{
                int monstersShown = 0;
                int i = 1;
                while(monstersShown < encounter.getMonsters().size()){
                    System.out.println(i + ". " + encounter.getMonsters().get(monstersShown).getName() +
                            " (x" + encounter.getNumberOfMonster(encounter.getMonsters().get(monstersShown).getName()) + ")");
                    monstersShown = monstersShown + encounter.getNumberOfMonster(encounter.getMonsters().get(monstersShown).getName());
                    i++;
                }
            }

            System.out.println(ENCOUNTER_MENU);
            System.out.print(ENCOUNTER_MENU_OPTION);

            try{
                int option = Integer.parseInt(scanner.nextLine());
                switch(option){
                    case 1: return EncounterMenuOptions.ADD_MONSTER;
                    case 2: return EncounterMenuOptions.REMOVE_MONSTER;
                    case 3: return EncounterMenuOptions.CONTINUE;
                    default: throw new NumberFormatException();
                }
            }catch (NumberFormatException e){
                System.out.println(ERROR_WRONG_OPTION);
            }
        } while (true);

    }
    @Override
    /**
     * Method that prompts the user to enter the monster to add.
     *
     * @return an int representation of the monster to add according to the list.
     */
    public int requestMonsterToAdd(int top){
        System.out.print("\n-> Choose a monster to add [1.." + top + "]: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Method that prompts the user to enter the number of that type of monster to add.
     *
     * @return an int representation of the number of monsters to add.
     */
    public int requestQuantityMonsters(String name){
        System.out.print("-> How many " + name + "(s) do you want to add: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Method that shows the user a list of all the monsters
     *
     * @return NONE.
     */
    public void showMonstersList(ArrayList<Monster> monsters){

        int i = 0;

        while(i != monsters.size()){
            System.out.println(i+1 + ". " + monsters.get(i).getName() + " (" + monsters.get(i).getChallenge() + ")");
            i++;
        }
    }

    // Constant used in the next method
    private static final String ERROR_BOSS_MONSTER = "\nOnly one boss monster is allowed in each encounter.";
    /**
     * Method that shows the user a message showing they have included more than one boss monster
     *
     * @return NONE
     */
    public void showErrorBossMonsterMessage(){
        System.out.println(ERROR_BOSS_MONSTER);
    }

    /**
     * Method that shows the user a message showing they still have to add monsters to the encounter
     *
     * @return NONE
     */
    public void showErrorEmptyEncounter(){
        System.out.println("\nYou have to add at least one monster to the encounter.");
    }

    /**
     * Method that prompts the user to enter the monster to remove.
     *
     * @return an int representation of the monster to remove according to the list.
     */
    public int requestMonsterToRemove(int size){
        System.out.print("\n-> Which monster do you want to delete [1.." + size + "]: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Method that shows the user a message showing they have removed a monster from the encounter
     *
     * @return NONE
     */
    public void showMonsterRemoved(String name, int quantity){
        System.out.println("\n" + quantity + " " + name + " were removed from the encounter.");
    }

    // Constants used in the next method
    private static final String ADVENTURE_HEADER = "\nTavern keeper: “So, you are looking to go on an adventure?”\n" +
                                                    "“Where do you fancy going?”\n";
    private static final String ADVENTURE_CHOICE = "\n-> Choose an adventure: ";

    /**
     * Method that shows a message before playing an adventure
     *
     * @return NONE
     */
    public void showAdventureMessage(){
        System.out.println(ADVENTURE_HEADER);
    }

    /**
     * Method that prompts the user to enter the adventure to play.
     *
     * @return an int representation of the adventure to play.
     */
    public int requestAdventureToPlay(){

        do{
            System.out.print(ADVENTURE_CHOICE);
            try{
                int option = Integer.parseInt(scanner.nextLine());

                return option;
            }catch (NumberFormatException e){
                System.out.println(ERROR_WRONG_OPTION);
            }
        } while (true);
    }

    /**
     * Method that shows the user a list of all the adventures
     *
     * @return NONE.
     */
    public void showAdventuresList(ArrayList<Adventure> adventures){

        int i = 0;
        System.out.println("Available adventures:");
        while(i != adventures.size()){
            System.out.println(i+1 + ". " + adventures.get(i).getName());
            i++;
        }
    }

    // Constant used in the next method
    private static final String REQUEST_ADVENTURE_CHARACTERS = "\n-> Choose a number of characters [3..5]: ";

    /**
     * Method that requests the user to enter the number of characters to play the adventure.
     *
     * @return NONE.
     */
    public int requestNumCharacters(String adventure){

        int num = 0;

        System.out.println("\nTavern keeper: “" + adventure + " it is!”\n" +
                            "“And how many people shall join you?”");
        System.out.print(REQUEST_ADVENTURE_CHARACTERS);

        num = Integer.parseInt(scanner.nextLine());

        return num;

    }

    // Constant used in the next method

    private static final String HYPHEN = "------------------------------";

    /**
     * Method that shows the user the current condition of the party
     *
     * @return NONE
     */
    public void showParty(int i, int num, String[] partyNames){

        System.out.println(HYPHEN);
        System.out.println("Your party (" + i + " / "+ num +"):\n");

        i = 0;
        while(i < num){

            if(partyNames[i] == null){
                System.out.println((i+1) + ". Empty");
            }
            else{
                System.out.println((i+1) + ". " + partyNames[i]);
            }
            i++;
        }
        System.out.println(HYPHEN);
    }

    /**
     * Method that prompts the user to enter the character to add to the party.
     *
     * @return an int representation of the character to add according to the list.
     */
    public int requestCharacterParty(int index){

        System.out.print("-> Choose character "+ index + " in your party: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Method that shows the user a message indicating that they do not have enough characters to play the adventure
     *
     * @return NONE
     */
    @Override
    public void showNotEnoughCharacters() {
        System.out.println("\nThere are not enough characters available.\n");
    }

    /**
     * Method that shows the user a message indicating how many characters will be playing the adventure
     *
     * @return NONE
     */
    public void showCharacterChoosingMessage(int num){
        System.out.println("\nTavern keeper: “Great, " + num + " it is.”\n" +
                "“Who among these lads shall join you?”");
    }

    //Constant used in the next method
    private static final String AVAILABLE_MESSAGE = "Available characters:";
    /**
     * Method that shows the user the message "Available characters"
     *
     * @return NONE
     */
    public void showAvailableMessage(){
        System.out.println(AVAILABLE_MESSAGE);
    }

    /**
     * Method that shows the user a message indicating that the character is already in the party
     *
     * @return NONE.
     */
    public void showErrorCharacterInParty(){
        System.out.println("\nThis character is already in your party.\n");
    }

    //Constant used in the next method
    private static final String GOOD_LUCK_MESSAGE = "\nTavern keeper: “Great, good luck on your adventure lads!”\n";

    /**
     * Method that shows the user the information about their complete party
     *
     * @return NONE
     */
    @Override
    public void showFullParty(int party, String[] partyNames, String name) {
        System.out.println(HYPHEN);
        System.out.println("\nYour party (" + party + " / "+ party +"):\n");

        int i = 0;
        while(i < party){

            if(partyNames[i] == null){
                System.out.println((i+1) + ". Empty");
            }
            else{
                System.out.println((i+1) + ". " + partyNames[i]);
            }
            i++;
        }
        System.out.println(HYPHEN);
        System.out.println(GOOD_LUCK_MESSAGE);
        System.out.println("The " + name + " will start soon...");
    }

    // Constant used in the next method

    private static final String SHORT_HYPHEN = "---------------------";
    /**
     * Method that shows the user a message showing the description of the encounter
     *
     * @return NONE.
     */
    public void showEncounterDescription(int numEncounters, Encounter encounter){
        System.out.println(SHORT_HYPHEN);
        System.out.println("Starting Encounter " + numEncounters + ":");
        int monstersShown = 0;
        while(monstersShown < encounter.getMonsters().size()){
            System.out.println("- " + encounter.getNumberOfMonster(encounter.getMonsters().get(monstersShown).getName())
            + "x " + encounter.getMonsters().get(monstersShown).getName());
            monstersShown = monstersShown + encounter.getNumberOfMonster(encounter.getMonsters().get(monstersShown).getName());
        }
        System.out.println(SHORT_HYPHEN);
    }

    /**
     * Method that shows the user a message showing the preparation stage actions of each character.
     *
     * @return NONE.
     */
    public void showPreparationStageActions(ArrayList<Character> adventureParty){
        String class_;
        System.out.println(HYPHEN);
        System.out.println("   *** Preparation Stage ***");
        System.out.println(HYPHEN);
        for (int i = 0; i < adventureParty.size(); i++) {
            class_ = adventureParty.get(i).getClass_();
            switch (class_){
                case "Adventurer":
                    System.out.println(adventureParty.get(i).getName() + " uses Self-Motivated. " +
                            "Their Spirit increases in +1.");
                    break;
            }
        }

    }
    /**
     * Method that shows the user the order in which the characters and monsters will fight in the encounter
     *
     * @return NONE.
     */
    public void showCombatOrder(ArrayList<Character> adventureParty, Encounter encounter){
        int totalCombatants = adventureParty.size() + encounter.getMonsters().size();
        int i = 0;
        int characterIndex = 0;
        int monsterIndex = 0;

        System.out.println("\nRolling initiative...");
        while(i < totalCombatants){
            if (characterIndex < adventureParty.size()) {
                if (i == adventureParty.get(characterIndex).getCombatOrder()) {
                    System.out.println("- " + adventureParty.get(characterIndex).getInitiative()
                            + "   " + adventureParty.get(characterIndex).getName());
                    characterIndex++;
                }
            }
            if(monsterIndex < encounter.getMonsters().size()) {
                if (i == encounter.getMonsters().get(monsterIndex).getCombatOrder()) {
                    System.out.println("- " + encounter.getMonsters().get(monsterIndex).getInitiative()
                            + "   " + encounter.getMonsters().get(monsterIndex).getName());
                    monsterIndex++;
                }
            }
            i++;
        }
    }
    //Constant used in the next method
    private static final String COMBAT_STAGE_MESSAGE = "   *** Combat Stage ***";
    /**
     * Method that shows the user the message "Combat stage"
     *
     * @return NONE.
     */
    public void showCombatStageSign(){
        System.out.println(HYPHEN);
        System.out.println(COMBAT_STAGE_MESSAGE);
        System.out.println(HYPHEN);
    }

    /**
     * Method that shows the user the current round and the HP of each character
     *
     * @return NONE.
     */
    public void showRoundAndHP(int counterRound, ArrayList<Character> adventureParty){
        System.out.println("\nRound " + counterRound + ":");
        System.out.println("Party: ");
        for (int i = 0; i < adventureParty.size(); i++) {
            System.out.println("- " + adventureParty.get(i).getName() + " " +
                    adventureParty.get(i).getCurrentHp() + " / " + adventureParty.get(i).getMaxHp() + " hit points.");
        }
    }

    /**
     * Method that shows the user the information about the combat action
     * @param hit int representation of the dice roll, indicating whether the attack was successful or not
     * @param damage int representation of the damage dealt
     * @param adventureParty ArrayList of characters that are playing the adventure
     * @param encounter Encounter that is being played
     * @param i int representation of the combatant that took the action
     * @return NONE.
     */
    public void showCombatAction(int hit, int damage, ArrayList<Character> adventureParty, Encounter encounter, int i){
        String nameActionTakerCharacter = new String("none");
        String nameActionTakerMonster = new String("none");
        String classCharacter = new String("none");
        String nameTargetCharacter = new String("none");
        String nameTargetMonster = new String("none");
        String damageType = new String("none");
        boolean deadMonster = false;
        boolean unconsciousCharacter = false;

        //get the combatant that took the action
        for (Character character : adventureParty) {
            if (character.getCombatOrder() == i) {
                nameActionTakerCharacter = character.getName();
                classCharacter = character.getClass_();
                if(character.getClass_().equals("Adventurer")){
                    damageType = "physical";
                }
            }
        }
        for (int j = 0; j < encounter.getMonsters().size(); j++) {
            if(encounter.getMonsters().get(j).getCombatOrder() == i){
                nameActionTakerMonster = encounter.getMonsters().get(j).getName();
                damageType = encounter.getMonsters().get(j).getDamageType();
            }
        }
        //get the target of the action
        for (Character character : adventureParty) {
            if (character.isTargeted()) {
                nameTargetCharacter = character.getName();
                if(character.getCurrentHp() == 0){
                    unconsciousCharacter = true;
                }
            }
        }
        for (int j = 0; j < encounter.getMonsters().size(); j++) {
            if(encounter.getMonsters().get(j).isTargeted()){
                nameTargetMonster = encounter.getMonsters().get(j).getName();
                if(encounter.getMonsters().get(j).getCurrentHp() <= 0){
                    deadMonster = true;
                }
            }
        }

        //show the information about the combat action
        if(nameActionTakerCharacter.equals("none") && nameActionTakerMonster.equals("none") ||
        nameTargetCharacter.equals("none") && nameTargetMonster.equals("none")){
            //nothing should be shown
        }
        else {
            //first line
            if (nameActionTakerCharacter.equals("none")) {
                System.out.println("\n" + nameActionTakerMonster + " attacks " + nameTargetCharacter + ".");
            }
            if (nameActionTakerMonster.equals("none")) {
                System.out.print("\n" + nameActionTakerCharacter + " attacks " + nameTargetMonster + " with ");
                if (classCharacter.equals("Adventurer")) {
                    System.out.println("Sword slash.");
                }
            }

            //second line
            if (hit == 1) {
                System.out.println("Fails and deals 0 " + damageType + " damage.");
            } else if (hit == 10) {
                System.out.println("Critical hit and deals " + damage + " " + damageType + " damage.");
            } else {
                System.out.println("Hits and deals " + damage + " " + damageType + " damage.");
            }

            //third line
            if (deadMonster) {
                System.out.println(nameTargetMonster + " dies.");
            }
            if (unconsciousCharacter) {
                System.out.println(nameTargetCharacter + " falls unconscious.");
            }
        }

    }

    /**
     * Method that shows the user the message "End of round"
     * @param counterRound int representation of the current round
     * @return NONE.
     */
    public void endOfRoundMessage(int counterRound){
        System.out.println("\nEnd of round " + counterRound + ".");
    }

    //Constant used in the next method
    private static final String DEFEAT_MESSAGE = "\nTavern keeper: “Lad, wake up. Yes, your party fell unconscious.”" +
           "\n"  + "“Don’t worry, you are safe back at the Tavern.”\n";

    /**
     * Method that shows the user a defeat message
     * @return NONE.
     */
    public void showDefeatMessage(){
        System.out.println(DEFEAT_MESSAGE);
    }

    //Constant used in the next method
    private static final String ENCOUNTER_VICTORY_MESSAGE = "All enemies are defeated\n";
    /**
     * Method that shows the user an encounter victory message
     * @return NONE.
     */
    public void showEncounterVictoryMessage(){
        System.out.println(ENCOUNTER_VICTORY_MESSAGE);
    }

    /**
     * Method that shows the user a message indicating that the character has gained experience
     * and, if applicable, has leveled up
     *
     * @param character Character that is dead
     * @param xpGained  int representation of the experience gained
     * @param levelUp   boolean indicating whether the character has leveled up or not
     * @param level
     * @return NONE.
     */
    public void showExperienceGain(Character character, int xpGained, boolean levelUp, int level){
        System.out.println(character.getName() + " gains " + xpGained + " xp.");
        if(levelUp){
            System.out.println(character.getName() + " levels up. They are now level " +
                    level + "!");
        }
    }

    /**
     * Method that shows the user a message indicating that the character has died
     *
     * @param character Character that is dead
     * @param healing  int representation of the healing done
     * @return NONE.
     */
    public void showShortRestActions(Character character, int healing){
        String characterClass = character.getClass_();
        if (character.getCurrentHp() == 0){
            System.out.println(character.getName() + " is unconscious.");
        } else {
            switch (characterClass) {
                case "Adventurer":
                    System.out.println(character.getName() + " uses Bandage time. Heals " + healing + " hit points.");
                    break;
            }
        }
    }

    /**
     * Method that shows the user the short rest header
     * @return NONE.
     */
    public void showShortRestHeader(){
        System.out.println(HYPHEN);
        System.out.println("*** Short rest stage ***");
        System.out.println(HYPHEN);
    }

    /**
     * Method that shows the user the adventure won message
     * @return NONE.
     */
    public void showAdventureVictoryMessage(String adventureName) {
        System.out.println("\nCongratulations, your party completed " + adventureName + "\n");
    }

    /**
     * Method that shows the user the persistence error message.
     * @return NONE.
     */
    public void printLoadingScreenError(String message){
        showHeader();
        System.out.println(message);
    }





}
