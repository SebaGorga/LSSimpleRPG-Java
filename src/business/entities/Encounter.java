package business.entities;

import java.util.ArrayList;

public class Encounter {

    private ArrayList<Monster> monsters;

    public Encounter() {
        this.monsters = new ArrayList<>();
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public int getNumberOfMonster(String nameMonster) {
        int number = 0;
        for (Monster monster : monsters) {
            if (monster.getName().equals(nameMonster)) {
                number++;
            }
        }
        return number;
    }

    public int getNumberOfDifferentMonsters() {
       return (int) monsters.stream().distinct().count();
    }
}
