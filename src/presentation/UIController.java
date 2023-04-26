package presentation;

import business.BusinessFacade;
import business.entities.Adventure;
import business.entities.Character;
import business.entities.Encounter;
import business.entities.Monster;
import persistence.exceptions.PersistenceException;
import presentation.views.UIManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static presentation.views.EncounterMenuOptions.*;

/**
 * Controller for the user interface, the main entry point for the application's logic, which will talk to the rest of the
 * presentation layer as well as to the business layer.
 *
 * @author Sebastián Félix Gorga & Valèria Ezquerra Rodriguez
 * @version 1.0
 */
public class UIController {
    // Instance of any class implementing the BusinessFacade interface, to talk to business layer
    private final BusinessFacade businessLayer;
    // Instance of any class implementing the UIManager interface, to talk to the rest of the presentation layer
    private final UIManager ui;


    /**
     * Constructor with parameters to create a user interface controller.
     *
     * @param ui an instance of any class implementing the UIManager interface, to talk to the rest of the presentation layer
     * @param businessFacade an instance of any class implementing the BusinessFacade interface, to talk to business layer
     */
    public UIController(UIManager ui, BusinessFacade businessFacade) {
        this.ui = ui;
        this.businessLayer = businessFacade;
    }

    /**
     * Main method of the controller (and by extension main method of the application's logic).
     *
     * <p>It will run infinitely, presenting the user with the application's menu and executing the chosen option.
     */
    public void run() throws PersistenceException, IOException {

        boolean option = true;
        String name;
        String characterName;
        String playerName;
        int level;
        int[] stats;
        boolean ok;
        int choice;
        ArrayList<Character> characters;
        ArrayList<Monster> monsters = new ArrayList<>();
        ArrayList<Adventure> adventures = new ArrayList<>();
        ArrayList<Encounter> encounters;
        String[] names;
        int numEncounters;
        String adventureName = new String();
        int counter;

        ui.showHeader();

        while (option) {
            switch (ui.showMainMenu(businessLayer.checkNumCharacters())) {
                case CHARACTER_CREATION:
                    // Asking for the character's name
                    name = ui.requestCharacterName();
                    if (!businessLayer.correctName(name)) {
                        characterName = businessLayer.correctCasing(name);

                        if(!businessLayer.checkUniqueNameCharacter(characterName)){
                            ui.showNotUniqueMessage();
                            break;
                        }

                    }else{
                        ui.showErrorName();
                        break;
                    }

                    // Asking for the player name
                    playerName = ui.requestPlayerNameToAdd(characterName);
                    //Asking for the experience level
                    level = ui.requestCharacterLevel();
                    //Generating the character's statistics
                    stats = ui.generateStats();
                    ok = businessLayer.addNewCharacter(characterName, playerName, level, stats);
                    if (ok){
                        ui.showCharacterCreationStatus(characterName);
                    }
                    break;
                case LIST_CHARACTERS:
                    playerName = ui.requestPlayerNameToList();
                    characters = businessLayer.findCharacterByPlayerName(playerName);
                    names = businessLayer.extractNamesFromCharacters(characters);
                    //get names and then send the name to businesss to get the full info in A character
                    ui.showCharacterListMessage();
                    ui.showCharacterList(names);
                    if (names.length == 0){
                        break;
                    }
                    else {
                        choice = ui.requestCharacterFullInfo(names);
                        if (choice == 0) {
                            //we do nothing since the user just goes back to the main menu
                        } else if (!businessLayer.checkChoiceValid(names.length, choice)) {
                            ui.showErrorWrongOptionMessage();
                        } else {
                            boolean removeDecided = false;
                            level = businessLayer.translateExpToLevel(characters.get(choice - 1).getXp());
                            ui.showCharacterFullInfo(characters.get(choice - 1), level);
                            while (!removeDecided) {
                                String decision = ui.askToRemove(characters.get(choice - 1));
                                decision.trim();
                                if (decision.equals("")) {
                                    removeDecided = true;
                                } else if (businessLayer.decisionEqualsName(decision, characters.get(choice - 1))) {
                                    ok = businessLayer.removeCharacter(characters.get(choice - 1));
                                    ui.showCharacterRemoved(characters.get(choice - 1));
                                    removeDecided = true;
                                } else {
                                    ui.showErrorWrongOptionMessage();
                                }
                            }
                        }
                    }
                    break;
                case CREATE_ADVENTURE:
                    int counterTries = 0;
                    adventureName = ui.requestAdventureName();
                    if(!businessLayer.checkUniqueNameAdventure(adventureName)){
                        ui.showNotUniqueMessage();
                        break;
                    }
                    while (counterTries < 3) {
                        numEncounters = ui.requestNumberEncounters(adventureName);
                        if (numEncounters > 4 || numEncounters < 1) {
                            ui.showErrorEncounter();
                            counterTries++;
                        } else {
                            counterTries = 3;
                            ui.showNumEncountersMessage(numEncounters);

                            encounters = new ArrayList<>(numEncounters);
                            for (int i = 0; i < numEncounters; i++) {
                                encounters.add(new Encounter());
                            }


                            int optionEncounters = 0;

                            while (optionEncounters < numEncounters) {
                                switch (ui.showEncounterMenu(optionEncounters, encounters.get(optionEncounters),
                                numEncounters)) {
                                    case ADD_MONSTER:
                                        //Show numerated monster's list : monster's name (difficulty)
                                        monsters = businessLayer.findMonsters();
                                        ui.showMonstersList(monsters);
                                        int num = ui.requestMonsterToAdd(monsters.size());
                                        int quantity = ui.requestQuantityMonsters(monsters.get(num - 1).getName());
                                        if (businessLayer.bossMonsterCheck(encounters.get(optionEncounters),
                                            monsters.get(num - 1), quantity)) {
                                            businessLayer.addMonsterToEncounter(encounters.get(optionEncounters),
                                                    monsters.get(num - 1), quantity);
                                        } else {
                                            ui.showErrorBossMonsterMessage();
                                        }
                                        break;
                                    case REMOVE_MONSTER:
                                        if ( businessLayer.checkEncounterEmpty(encounters.get(optionEncounters))) {
                                            ui.showErrorEmptyEncounter();
                                        } else {
                                            int numMonster = ui.requestMonsterToRemove(
                                                    encounters.get(optionEncounters).getNumberOfDifferentMonsters());
                                            if (numMonster < 1 || numMonster > encounters.get(optionEncounters)
                                                    .getNumberOfDifferentMonsters()) {
                                                ui.showErrorWrongOptionMessage();
                                            } else {
                                                quantity = businessLayer.getQuantityOfMonster(encounters.get(optionEncounters),
                                                        (numMonster - 1));
                                                name = businessLayer.getNameOfMonster(encounters.get(optionEncounters),
                                                        (numMonster - 1));
                                                ui.showMonsterRemoved(name, quantity);
                                                businessLayer.removeMonsterFromEncounter(encounters.get(optionEncounters),
                                                        name);
                                            }
                                        }

                                        break;
                                    case CONTINUE:
                                        if ( businessLayer.checkEncounterEmpty(encounters.get(optionEncounters))) {
                                            ui.showErrorEmptyEncounter();
                                        } else {
                                            optionEncounters++;
                                        }
                                        break;
                                }
                            }
                            businessLayer.addNewAdventure(adventureName, encounters);

                        }
                    }
                    break;
                case START_ADVENTURE:
                    if (businessLayer.checkNumCharacters() < 3){
                        ui.showNotEnoughCharacters();
                        break;
                    } else {
                        ui.showAdventureMessage();
                        adventures = businessLayer.findAdventures();
                        int adventure;
                        do {
                            ui.showAdventuresList(adventures);
                            adventure = ui.requestAdventureToPlay();
                            if (adventure < 1 || adventure > adventures.size()) {
                                ui.showErrorWrongOptionMessage();
                            }
                        } while (adventure < 1 || adventure > adventures.size());

                        int party;
                        do {
                            party = ui.requestNumCharacters(adventures.get(adventure - 1).getName());
                            if (party < 3 || party > 5) {
                                ui.showErrorWrongOptionMessage();
                            }
                        } while (party < 3 || party > 5);

                        ui.showCharacterChoosingMessage(party);

                        String[] partyNames = new String[party];

                        counter = 0;
                        while (counter < party) {

                            ui.showParty(counter, party, partyNames);
                            // Esta función es la misma, podriamos separarla al final.
                            characters = businessLayer.findCharacterByPlayerName("\n");
                            names = businessLayer.extractNamesFromCharacters(characters);
                            ui.showAvailableMessage();
                            ui.showCharacterList(names);
                            // Pedir el character y añadirlo a la array
                            int character = ui.requestCharacterParty(counter + 1);
                            if (character < 1 || character > characters.size()) {
                                ui.showErrorWrongOptionMessage();
                            } else {
                                if (businessLayer.checkCharacterInParty(partyNames, characters.get(character - 1).getName())) {
                                    ui.showErrorCharacterInParty();
                                } else {
                                    partyNames[counter] = characters.get(character - 1).getName();
                                    counter++;
                                }
                            }
                        }
                        ui.showFullParty(party, partyNames, adventures.get(adventure - 1).getName());

                        // Start adventure
                        ArrayList<Character> adventureParty = businessLayer.createAdventureParty(partyNames);
                        for (Character character : adventureParty) {
                            businessLayer.initializeHP(character);
                        }
                        Adventure adventureToPlay = adventures.get(adventure - 1);
                        businessLayer.initializeCurrentHPMonsters(adventureToPlay);
                        numEncounters = 0;


                        boolean charactersUnconscious = false;
                        while (numEncounters < adventureToPlay.getEncounters().size()) {
                            int XPGained = businessLayer.getTotalXP(adventureToPlay.getEncounters().get(numEncounters));
                            boolean monstersDefeated = false;
                            ui.showEncounterDescription(numEncounters + 1,
                                    adventureToPlay.getEncounters().get(numEncounters));
                            adventureParty = businessLayer.preparationStage(adventureParty);
                            ui.showPreparationStageActions(adventureParty);
                            businessLayer.setInitiativeValues(adventureParty, adventureToPlay.getEncounters().get(numEncounters));
                            businessLayer.setCombatOrder(adventureParty, adventureToPlay.getEncounters().get(numEncounters));
                            ui.showCombatOrder(adventureParty, adventureToPlay.getEncounters().get(numEncounters));

                            int counterRound = 1;
                            int[] combatOutputs = new int[2];

                            ui.showCombatStageSign();
                            //number of combatants in the encounter
                            int totalCombatants = adventureParty.size() +
                                    adventureToPlay.getEncounters().get(numEncounters).getMonsters().size();

                            //only continue while no side has won yet
                            while (!charactersUnconscious && !monstersDefeated) {
                                ui.showRoundAndHP(counterRound, adventureParty);

                                for (int i = 0; i < totalCombatants; i++) {
                                    //in the middle of a round, check if any side has won
                                    if (!charactersUnconscious && !monstersDefeated) {
                                        combatOutputs = businessLayer.combatStageActions(adventureParty,
                                                adventureToPlay.getEncounters().get(numEncounters), i);
                                        ui.showCombatAction(combatOutputs[0], combatOutputs[1], adventureParty,
                                                adventureToPlay.getEncounters().get(numEncounters), i);
                                        businessLayer.updateCombatants(adventureParty,
                                                adventureToPlay.getEncounters().get(numEncounters));
                                    }
                                    charactersUnconscious = businessLayer.checkTPU(adventureParty);
                                    monstersDefeated = businessLayer.checkMonstersDefeated(adventureToPlay.getEncounters().get(numEncounters));

                                }
                                ui.endOfRoundMessage(counterRound);
                                counterRound++;

                            }
                            if (charactersUnconscious) {
                                ui.showDefeatMessage();
                                break;
                            } else {

                                ui.showEncounterVictoryMessage();
                                ui.showShortRestHeader();
                                for (int i = 0; i < adventureParty.size(); i++) {
                                    boolean levelUp = businessLayer.experienceGain(adventureParty.get(i), XPGained);
                                    level = businessLayer.translateExpToLevel(adventureParty.get(i).getXp());
                                    ui.showExperienceGain(adventureParty.get(i), XPGained, levelUp, level);
                                    int healing = businessLayer.shortRestActions(adventureParty.get(i), levelUp);
                                    ui.showShortRestActions(adventureParty.get(i), healing);
                                    businessLayer.reverseSupportActions(adventureParty.get(i));
                                }


                                numEncounters++;

                            }
                        }
                        if (!charactersUnconscious) {
                            ui.showAdventureVictoryMessage(adventures.get(adventure - 1).getName());
                            for (Character character : adventureParty) {
                                boolean okay = businessLayer.updateCharacter(character);
                            }
                        }

                    }
                    break;
                case EXIT:
                    option = false;
                    ui.showExitMessage();
                    break;
            }
        }
    }
}

