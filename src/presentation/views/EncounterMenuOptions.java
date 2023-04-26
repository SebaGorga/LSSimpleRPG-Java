package presentation.views;

/**
 * Options that can be used in the encounter menu, including:
 *
 * {@link #ADD_MONSTER}
 * {@link #REMOVE_MONSTER}
 * {@link #CONTINUE}
 *
 * @authors Sebastián Félix Gorga & Valèria Ezquerra Rodriguez
 * @version 1.0
 */
public enum EncounterMenuOptions {

    /**
     * Option that lets the user add a new monster to a particular encounter
     */
    ADD_MONSTER,
    /**
     * Option that lets the user remove a monster from an encounter
     */
    REMOVE_MONSTER,
    /**
     * Option that lets the user create an adventure
     */
    CONTINUE
}