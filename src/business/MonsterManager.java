package business;

import business.entities.Character;
import business.entities.Monster;
import persistence.exceptions.MonsterFileNotFoundException;
import persistence.exceptions.PersistenceException;
import persistence.json.monster.MonsterDAO;
import persistence.json.monster.MonsterJsonDAO;

import java.io.IOException;
import java.util.ArrayList;

public class MonsterManager {

    private final MonsterDAO dao;

    public MonsterManager () throws MonsterFileNotFoundException {
        dao = new MonsterJsonDAO("data/monsters.json");
    }

    /**
     * Method that searches for existing monsters
     *
     * @return an array list containing object representations of all monsters
     */
    public ArrayList<Monster> findMonsters() {
        try {
            ArrayList<Monster> monsters;

            monsters = dao.getAll();

            return monsters;
        } catch (IOException | PersistenceException ignored) {
            return new ArrayList<Monster>();
        }
    }

}
