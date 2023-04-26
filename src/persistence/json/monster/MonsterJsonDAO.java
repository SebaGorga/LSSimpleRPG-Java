package persistence.json.monster;

import business.entities.Character;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import persistence.exceptions.MonsterFileNotFoundException;
import persistence.exceptions.PersistenceException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import business.entities.Monster;

/**
 * Class that implements the methods described in the {@link MonsterDAO} interface, and will be used as a way to
 * physically separate the persistence layer from the rest of the application.
 *
 * <p>Specifically, it implements the monster persistence in a JSON file.
 *
 * @author DPOO 20-21 Team
 * @version 1.0
 */
public class MonsterJsonDAO implements MonsterDAO {

    // Path to the file where the students will be persisted
    private final Path path;
    // Gson instance to help when persisting
    private final Gson gson;


    /**
     * Parametrized constructor to persist monsters in a JSON file, while being able to read monsters information.
     *
     * @param path a string representation of the file path to open (or create if it doesn't exist)
     * @throws PersistenceException if something goes wrong when opening/creating the file (including but not limited to the path being malformed)
     */
    public MonsterJsonDAO(String path) throws MonsterFileNotFoundException {

        Path p = Paths.get(path);

        this.path = p;
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        if(!Files.exists(p)){
            throw new MonsterFileNotFoundException("Error: The monsters.json file can’t be accessed.");
        }
    }

    /**
     * Method that reads the persisted information of all monsters.
     *
     */
    @Override
    public ArrayList<Monster> getAll() throws PersistenceException {

        ArrayList<Monster> all;
        try {
            String monstersString = Files.readString(path);
            Type listType = new TypeToken<ArrayList<Monster>>() {}.getType();
            all = gson.fromJson(monstersString, listType);
        } catch ( IOException e) {
            throw new PersistenceException("Error: The monsters.json file can’t be accessed.", e);
        }

        return all;
    }

}
