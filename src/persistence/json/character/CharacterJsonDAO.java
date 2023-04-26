package persistence.json.character;

import business.entities.Character;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import persistence.exceptions.PersistenceException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;


/**
 * Class that implements the methods described in the {@link CharacterDAO} interface, and will be used as a way to
 * physically separate the persistence layer from the rest of the application.
 *
 * <p>Specifically, it implements the monster persistence in a JSON file.
 *
 * @author Sebastián Félix Gorga & Valèria Ezquerra Rodriguez
 * @version 1.0
 */
public class CharacterJsonDAO implements CharacterDAO {

    // Path to the file where the students will be persisted
    private final Path path;
    // Gson instance to help when persisting
    private final Gson gson;


    /**
     * Parametrized constructor to persist characters in a JSON file, while being able to read character information.
     *
     * @param path a string representation of the file path to open (or create if it doesn't exist)
     * @throws PersistenceException if something goes wrong when opening/creating the file (including but not limited to the path being malformed)
     */
    public CharacterJsonDAO(String path) throws PersistenceException {
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
     * Method that reads the persisted information of all characters.
     *
     */
    @Override
    public ArrayList<Character> getAll() throws IOException {

        String charactersString = Files.readString(path);
        Type listType = new TypeToken<ArrayList<Character>>(){}.getType();
        ArrayList<Character> all = gson.fromJson(charactersString, listType);

        if (all == null){
            all = new ArrayList<>();
        }

        return all;
    }

    /**
     * Method that saves a specific character, persisting its information.
     *
     * @param character the character to save
     */
    @Override
    public void save(Character character) throws IOException, PersistenceException {

        JsonArray characters;
        JsonObject characterJson = gson.toJsonTree(character).getAsJsonObject();
        try {
            characters = JsonParser.parseString(Files.readString(path)).getAsJsonArray();
        }catch (IllegalStateException e){
            characters = new JsonArray();
        }
        characters.add(characterJson);

        try {
            Files.writeString(path, gson.toJson(characters));
        } catch (IOException e) {
            throw new PersistenceException("Couldn't save a Student (JSON implementation).", e);
        }
    }

    @Override
    public ArrayList<Character> getByPlayerName(String playerName) throws IOException {
        ArrayList<Character> filtered = new ArrayList<>();
        String charactersString = Files.readString(path);
        Type listType = new TypeToken<ArrayList<Character>>(){}.getType();
        ArrayList<Character> all = gson.fromJson(charactersString, listType);

        if (all == null){
            all = new ArrayList<>();
        }

        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getPlayer().toLowerCase().contains(playerName.toLowerCase())){
                filtered.add(all.get(i));
            }
        }

        return filtered;
    }

    /**
     * Method that deletes a specific character, removing its information.
     *
     * @param character the character to delete
     * @throws PersistenceException if something goes wrong when persisting (reading from / writing to the file)
     */
    public void delete(Character character) throws PersistenceException, IOException {

        String charactersString = Files.readString(path);
        Type listType = new TypeToken<ArrayList<Character>>(){}.getType();
        ArrayList<Character> all = gson.fromJson(charactersString, listType);
        for (int i = 0; i < all.size(); i++) {
            if(Objects.equals(all.get(i).getName(), character.getName())){
                all.remove(i);
            }
        }
        JsonArray jsonArray = gson.toJsonTree(all).getAsJsonArray();

        try {
            Files.writeString(path, gson.toJson(jsonArray));
        } catch (IOException e) {
            throw new PersistenceException("Couldn't delete a Character).", e);
        }

    }

    /**
     * Method that reads the persisted information of a specific character, specifically the one with a specific name.
     *
     * @param name a string representation of the name to use in the query
     * @throws IOException if something goes wrong when querying the persisted data
     */
    public Character getByCharacterName(String name) throws IOException {

        String charactersString = Files.readString(path);
        Type listType = new TypeToken<ArrayList<Character>>() {
        }.getType();
        ArrayList<Character> all = gson.fromJson(charactersString, listType);

        if (all == null) {
            all = new ArrayList<>();
        }

        Character character = null;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getName().equals(name)) {
                character = all.get(i);
            }
        }

        return character;
    }

    /**
     * Method that updates a specific character, persisting its information.
     *
     * @param character the character to update
     * @throws PersistenceException if something goes wrong when persisting (writing to the file)
     */
    public void update(Character character) throws PersistenceException {
        JsonArray characters;
        JsonObject characterJson = gson.toJsonTree(character).getAsJsonObject();
        try {
            characters = JsonParser.parseString(Files.readString(path)).getAsJsonArray();
        }catch (IllegalStateException | IOException e){
            characters = new JsonArray();
        }
        for (int i = 0; i < characters.size(); i++) {
            if(characters.get(i).getAsJsonObject().get("name").getAsString().equals(character.getName())){
                characters.remove(i);
            }
        }
        characters.add(characterJson);

        try {
            Files.writeString(path, gson.toJson(characters));
        } catch (IOException e) {
            throw new PersistenceException("Couldn't update a Character", e);
        }
    }

}
