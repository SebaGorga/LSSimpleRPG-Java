import business.BusinessFacade;
import business.BusinessFacadeImpl;
import persistence.exceptions.MonsterFileNotFoundException;
import persistence.exceptions.PersistenceException;
import presentation.UIController;
import presentation.views.UIManager;
import presentation.views.console.ConsoleUIManager;

import java.io.IOException;

/**
 * The application's main class, just to hold the main method.
 *
 * <p>Normally, it can be ignored in the JavaDoc but in this project it's included for completeness purposes.
 *
 * @author Sebastián Félix Gorga & Valèria Ezquerra Rodriguez
 * @version 1.0
 */
public class Main {

    /**
     * The application's main method, just an execution entry point.
     *
     * <p>In object-oriented programming, we tend to minimize the main method's size, and implement our logic passing messages.
     *
     * @param args The program's arguments
     * @throws PersistenceException if the persistence layer encounters any problem
     */
    public static void main(String[] args) throws PersistenceException, IOException {

        try {
            BusinessFacade businessFacade = new BusinessFacadeImpl();
            UIManager uiManager = new ConsoleUIManager();
            UIController controller = new UIController(uiManager, businessFacade);
            controller.run();
        }catch (MonsterFileNotFoundException e){
            UIManager uiManager = new ConsoleUIManager();
            uiManager.printLoadingScreenError(e.getMessage());
        }
        }
    }
