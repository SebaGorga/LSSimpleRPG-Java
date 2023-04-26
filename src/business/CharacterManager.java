package business;

import persistence.exceptions.PersistenceException;
import persistence.json.character.CharacterDAO;
import persistence.json.character.CharacterJsonDAO;
import business.entities.Character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that focuses on logic related to characters and connects the business layer to lower ones, specifically the persistence layer.
 *
 * @author Sebastián Félix Gorga & Valèria Ezquerra Rodriguez
 * @version 1.0
 */
public class CharacterManager {

    // Abstraction from the persistence layer in the form of a Data Access Object, specifically for characters

    private final CharacterDAO dao;

    /**
     * Default constructor, which uses the JSON Data Access Object for characters with a fixed file.
     *
     * @throws PersistenceException if the manager can't be instantiated because of errors in the persistence layer
     */
    public CharacterManager() throws PersistenceException {
        dao = new CharacterJsonDAO("data/characters.json");
    }


    /**
     * Method that checks whether the name has any special characters.
     * @param provisionalName
     * @return a boolean that indicates whether it is correct or not.
     */
    public boolean correctName(String provisionalName){
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(provisionalName);
        Pattern digit = Pattern.compile("[0-9]");
        Matcher hasDigit = digit.matcher(provisionalName);
        return hasSpecial.find() || hasDigit.find();
    }

    /**
     * Method that gets a name and makes it so that it has the correct casing.
     *
     * @return a string representation of a character's name with the correct casing.
     */
    public String correctCasing(String provisionalName){

        String name = provisionalName.toLowerCase();
        String words[]=name.split("\\s");
        String capitalizeWord="";
        for(String w:words){
            String first=w.substring(0,1);
            String afterFirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterFirst+" ";
        }
        return capitalizeWord.trim();
    }

    /**
     * Method that checks whether the name that was input by the user already exists.
     *
     * @param name a string representation of the queried character's name
     * @return whether the name is already in use or not
     */
    public boolean checkUniqueName (String name) throws IOException {
        boolean unique = true;
        ArrayList<Character> characters = dao.getAll();
        if (!characters.isEmpty()) {
            for (int i = 0; i < characters.size(); i++) {
                if (Objects.equals(name, characters.get(i).getName())) {
                    unique = false;
                }
            }
        }
        return unique;
    }



    /**
     * Method that adds a character to the system.
     *
     * @param name a string representation of the character's name
     * @param player a string representation of the character's creator
     * @param level an int representation of the character's level which is turned to xp
     * @param stats an array of ints that represents the character's statistics
     * @return whether the student was successfully created or not
     */
    public boolean addNewCharacter(String name, String player, int level, int[] stats) {

        int xp = translateLevelToExp (level);
        Character character = new Character(name, player, xp, stats[0], stats[1], stats[2], "Adventurer");
        try {
            dao.save(character);
        } catch (PersistenceException | IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Method that searches existing characters for those owned by a given player name.
     *
     * @param playerName a string representation of the name of the player whose characters we need to look for
     * @return an array list containing object representations of all characters owned by the given player
     */
    public ArrayList<Character> findCharacterByPlayerName(String playerName) {
        try {
            ArrayList<Character> characters;
            if (playerName.equals("\n")) {
                characters = dao.getAll();
            }
            else {
                characters = dao.getByPlayerName(playerName);
            }

            return characters;
        } catch (PersistenceException | IOException ignored) {
            return new ArrayList<Character>();
        }
    }

    /**
     * Method that extracts the names of a list of characters.
     *
     * @param characters an array list representation of characters.
     * @return an array containing string representations of all characters' names.
     */
    public String[] extractNamesFromCharacters (ArrayList<Character> characters){
        String[] names = new String[characters.size()];
        for (int i = 0; i < characters.size(); i++) {
            names[i] = characters.get(i).getName();
        }
        return names;
    }


    /**
     * Method that checks whether the character choice by the user is within the bounds of the list
     *
     * @param num    an int representation of the length of the list
     * @param choice
     * @return a boolean representation of whether the choice made by the user is correct or not.
     */
    public boolean checkChoiceValid(int num, int choice) {
        boolean valid = false;

        if(choice <= num && choice > 0){
            valid = true;
        }

        return valid;
    }

    /**
     * Method that checks whether the input of the user matches the name of the character
     *
     * @param decision    a String representation of the input of the user
     * @param character an object representation of the character whose existence is put into debate
     * @return a boolean representation of whether the input of the user matches the name of the character or not.
     */
    public boolean decisionEqualsName(String decision, Character character){
       return decision.equals(character.getName());
    }

    /**
     * Method that deletes a character from the system.
     *
     * @param character an object representation of the character to remove
     * @return whether the character was successfully deleted or not
     */
    public void removeCharacter(Character character) {
        try {
            dao.delete(character);
        } catch (PersistenceException | IOException e) {
            // e.printStackTrace();
        }

    }

    public int translateLevelToExp(int level) {
        int xp = 0;
        switch (level){
            case 1:
                xp = 0;
                break;
            case 2:
                xp = 100;
                break;
            case 3:
                xp = 200;
                break;
            case 4:
                xp = 300;
                break;
            case 5:
                xp = 400;
                break;
            case 6:
                xp = 500;
                break;
            case 7:
                xp = 600;
                break;
            case 8:
                xp = 700;
                break;
            case 9:
                xp = 800;
                break;
            case 10:
                xp = 900;
                break;
        }
        return xp;
    }

    public int translateExpToLevel(int xp) {
        int level;
        if (xp >= 0  && xp < 100){
            level = 1;
        } else if (xp > 99  && xp < 200) {
            level = 2;
        } else if (xp > 199  && xp < 300) {
            level = 3;
        } else if (xp > 299  && xp < 400) {
            level = 4;
        } else if (xp > 399  && xp < 500) {
            level = 5;
        } else if (xp > 499  && xp < 600) {
            level = 6;
        } else if (xp > 599  && xp < 700) {
            level = 7;
        } else if (xp > 699  && xp < 800) {
            level = 8;
        } else if (xp > 799  && xp < 900) {
            level = 9;
        } else {
            level = 10;
        }
        return level;
    }

    /**
     * Method that gets the number of characters in the system
     *
     * @return an int representation of the characters already stored in the system
     */
    public int checkNumCharacters() {

        try{
            ArrayList<Character> characters;

            characters = dao.getAll();

            return characters.size();
        } catch (IOException ignored){
            return 0;
        }
    }

    /**
     * Method that creates an ArrayList of characters based on the names given.
     *
     * @param partyNames a string array representation of the party's characters
     * @return the ArrayList containing the characters
     */
    public ArrayList<Character> createAdventureParty(String[] partyNames) throws IOException {
        ArrayList<Character> party = new ArrayList<>();
        Character character;
        for (int i = 0; i < partyNames.length; i++) {
            character = dao.getByCharacterName(partyNames[i]);
            party.add(character);
        }
        return party;
    }

    /**
     * Method that initializes de maxHP and currentHP of the characters in the party.
     *
     * @param character the character to initialize
     * @return NONE
     */
    public void initializeHP(Character character){
        int maxHP;
        int level;
        level = translateExpToLevel(character.getXp());
        maxHP = (10 + character.getBody()) * level;
        character.setMaxHP(maxHP);
        character.setCurrentHP(maxHP);
    }

    /**
     * Method that calculates and gives the corresponding experience gained from the encounter.
     *
     * @param character the character involved in combat
     * @param xpGained the amount of xp gained
     * @return whether the character levelled up or not
     */
    public boolean experienceGain(Character character, int xpGained) {
        int oldLevel = translateExpToLevel(character.getXp());
        int newLevel = translateExpToLevel(character.getXp() + xpGained);
        boolean levelUp = false;
        if(newLevel > oldLevel){
            levelUp = true;
        }
        character.setXp(character.getXp() + xpGained);

        return levelUp;

    }

    /**
     * Method that generates the short rest stage actions after the encounter
     *
     * @param character the character involved in the encounter
     * @param levelUp   whether the character leveled up or not
     * @return int representation of the amount of health the character has recovered
     */
    public int shortRestActions(Character character, boolean levelUp) {
        Random dice = new Random();
        if (levelUp) {
            initializeHP(character);
        }
        String characterClass = character.getClass_();
        int healing = 0;
        if (character.getCurrentHp() > 0) {
            switch (characterClass) {
                case "Adventurer":
                    healing = dice.nextInt(8) + 1 + character.getMind();
                    character.setCurrentHP(character.getCurrentHp() + healing);
                    if (character.getCurrentHp() > character.getMaxHp()) {
                        character.setCurrentHP(character.getMaxHp());
                    }
                    break;
            }
        }
        return healing;
    }

    /**
     * Method that reverses the effects of any support actions taken during the encounter
     *
     * @param character the character involved in the encounter
     * @return NONE
     */
    public void reverseSupportActions(Character character){
        String characterClass = character.getClass_();
        switch (characterClass) {
            case "Adventurer":
                character.setSpirit(character.getMind() - 1);
                break;
        }
    }

    /**
     * Method that updates the character's xp after the adventure
     *
     * @param character the character involved in the adventure
     * @return NONE
     */
    public boolean updateCharacter(Character character){
        try {
            dao.update(character);
        } catch (PersistenceException e) {
            return false;
        }
        return true;
    }

}
