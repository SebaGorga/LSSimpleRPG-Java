package persistence.json.character;

import business.entities.Character;
import persistence.exceptions.PersistenceException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface that abstracts the persistence of students from uppers.
 *
 * <p>In particular, it follows the Data Access Object design pattern, which is commonly used to abstract persistence
 * implementations with a set of generic operations.
 *
 * @author Sebastián Félix Gorga & Valèria Ezquerra Rodriguez
 * @version 1.0
 */
public interface CharacterDAO {

    /**
     * Method that reads the persisted information of all characters.
     *
     * @throws IOException if something goes wrong when querying the persisted data (reading from the file)
     */
    ArrayList<Character> getAll() throws IOException;

    /**
     * Method that saves a specific character, persisting its information.
     *
     * @param character the character to save
     * @throws PersistenceException if something goes wrong when persisting (writing to the file)
     */
    void save(Character character) throws PersistenceException, IOException;


    /**
     * Method that reads the persisted information of a set of characters, specifically those that are owned by a specific player.
     *
     * @param playerName a string representation of the name to use in the query
     * @throws PersistenceException if something goes wrong when querying the persisted data
     */
    ArrayList<Character> getByPlayerName(String playerName) throws PersistenceException, IOException;

    /**
     * Method that deletes a specific character, removing its information.
     *
     * @param character the character to delete
     * @throws PersistenceException if something goes wrong when persisting (reading from / writing to the file)
     */
    void delete(Character character) throws PersistenceException, IOException;

    /**
     * Method that reads the persisted information of a specific character, specifically the one with a specific name.
     *
     * @param name a string representation of the name to use in the query
     * @throws PersistenceException if something goes wrong when querying the persisted data
     */
    Character getByCharacterName(String name) throws IOException;

    /**
     * Method that updates a specific character, persisting its information.
     *
     * @param character the character to update
     * @throws PersistenceException if something goes wrong when persisting (writing to the file)
     */
    void update(Character character) throws PersistenceException;
}
