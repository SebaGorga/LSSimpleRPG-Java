package persistence.json.monster;

import business.entities.Character;
import business.entities.Monster;
import persistence.exceptions.PersistenceException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface that abstracts the persistence of students from uppers.
 *
 * <p>In particular, it follows the Data Access Object design pattern, which is commonly used to abstract persistence
 * implementations with a set of generic operations.
 *
 * @author DPOO 20-21 Team
 * @version 1.0
 */
public interface MonsterDAO {

    /**
     * Method that reads the persisted information of all monsters
     *
     * @throws IOException if something goes wrong when querying the persisted data (reading from the file)
     */
    ArrayList<Monster> getAll() throws IOException, PersistenceException;
}

