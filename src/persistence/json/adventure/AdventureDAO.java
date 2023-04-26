package persistence.json.adventure;

import business.entities.Adventure;
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
 * @author DPOO 20-21 Team
 * @version 1.0
 */
public interface AdventureDAO {

    /**
     * Method that reads the persisted information of all adventures.
     *
     * @throws PersistenceException if something goes wrong when querying the persisted data (reading from the file)
     */
    ArrayList<Adventure> getAll() throws IOException;

    /**
     * Method that saves a specific adventure, persisting its information.
     *
     * @param adventure the adventure to save
     * @throws PersistenceException if something goes wrong when persisting (writing to the file)
     */
    void save(Adventure adventure) throws PersistenceException, IOException;
}