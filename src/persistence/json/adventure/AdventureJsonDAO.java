package persistence.json.adventure;

import business.entities.Adventure;
import business.entities.Character;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import persistence.exceptions.PersistenceException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AdventureJsonDAO implements AdventureDAO{

    // Path to the file where the adventures will be persisted
    private final Path path;
    // Gson instance to help when persisting
    private final Gson gson;

    /**
     * Parametrized constructor to persist characters in a JSON file, while being able to read character information.
     *
     * @param path a string representation of the file path to open (or create if it doesn't exist)
     * @throws PersistenceException if something goes wrong when opening/creating the file (including but not limited to the path being malformed)
     */
    public AdventureJsonDAO(String path) throws PersistenceException {
        try {
            Path p = Paths.get(path);

            if (!Files.exists(p)) {
                Files.createFile(p);

            }

            this.path = p;
            this.gson = new GsonBuilder().setPrettyPrinting().create();

        } catch (InvalidPathException | IOException e) {
            throw new PersistenceException("Couldn't create a Character DAO (JSON implementation) with path: " + path + ".", e);
        }
    }

    /**
     * Method that reads the persisted information of all adventures.
     *
     */
    @Override
    public ArrayList<Adventure> getAll() throws IOException {

        String adventuresString = Files.readString(path);
        Type listType = new TypeToken<ArrayList<Adventure>>(){}.getType();
        ArrayList<Adventure> all = gson.fromJson(adventuresString, listType);

        if (all == null){
            all = new ArrayList<>();
        }

        return all;
    }

    /**
     * Method that saves a specific adventure, persisting its information.
     *
     * @param adventure the adventure to save
     */
    @Override
    public void save(Adventure adventure) throws IOException, PersistenceException {

        JsonArray adventures;
        JsonObject adventuresJson = gson.toJsonTree(adventure).getAsJsonObject();
        try {
            adventures = JsonParser.parseString(Files.readString(path)).getAsJsonArray();
        }catch (IllegalStateException e){
            adventures = new JsonArray();
        }
        adventures.add(adventuresJson);

        try {
            Files.writeString(path, gson.toJson(adventures));
        } catch (IOException e) {
            throw new PersistenceException("Couldn't save a Student (JSON implementation).", e);
        }
    }
}
