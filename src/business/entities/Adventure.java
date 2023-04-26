package business.entities;

import java.util.ArrayList;

public class Adventure {

    private String name;
    private ArrayList<Encounter> encounters;

    public Adventure(String name, ArrayList<Encounter> encounters) {
        this.name = name;
        this.encounters = encounters;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Encounter> getEncounters() {
        return encounters;
    }
}
