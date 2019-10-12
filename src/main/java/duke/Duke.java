package duke;

import duke.command.SetPriorityCommand;
import duke.command.AddMultipleCommand;
import duke.command.DeleteCommand;
import duke.command.AddContactsCommand;
import duke.command.Command;
import duke.command.ListPriorityCommand;
import duke.command.ExitCommand;
import duke.command.BackupCommand;
import duke.command.BudgetCommand;

import duke.dukeexception.DukeException;
import duke.parser.Parser;
import duke.storage.BudgetStorage;
import duke.storage.PriorityStorage;
import duke.storage.Storage;
import duke.storage.ContactStorage;
import duke.task.PriorityList;
import duke.task.ContactList;
import duke.command.ListContactsCommand;

import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;

/**
 * Represents a duke that controls the program.
 */
public class Duke {
    private Storage storage;
    private TaskList items;
    private Ui ui;
    private ContactStorage contactStorage;
    private ContactList contactList;
    private BudgetStorage budgetStorage;
    private PriorityStorage priorityStorage;
    private PriorityList priorityList;
    private float storedBudget;

    /**
     * Creates a duke to initialize storage, task list, and ui.
     *
     * @param filePath1 The location of the text file.
     * @param filePath2 The location of the priority text file.
     * @param filePathBudget The location of the budget text file.
     * @param filePathForContacts The location of the contact text file.
     */
    public Duke(String filePath1, String filePath2, String filePathBudget,String filePathForContacts) {
        ui = new Ui();
        storage = new Storage(filePath1);
        priorityStorage = new PriorityStorage(filePath2);
        budgetStorage = new BudgetStorage(filePathBudget);
        contactStorage = new ContactStorage(filePathForContacts);
        try {
            items = new TaskList(storage.read());
        } catch (IOException e) {
            ui.showLoadingError();
            items = new TaskList();
        }
        try {
            priorityList = new PriorityList(priorityStorage.read());
        } catch (IOException e) {
            ui.showLoadingError();
            priorityList = new PriorityList();
        }

        try {
            contactList = new ContactList(contactStorage.read());
        } catch (IOException e) {
            ui.showLoadingError();
            contactList = new ContactList();
        }

        try {
            storedBudget = budgetStorage.read();
        } catch (IOException e) {
            ui.showLoadingError();
            storedBudget = 0;
        }
    }

    /**
     * Echoes the user input back the the user.
     * (Not in use)
     *
     * @param input The user input.
     * @return String of the response.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }

    /**
     * Retrieves a command from interpreting the user input (GUI).
     *
     * @param sentence The user input.
     * @return Command to be executed thereafter.
     * @throws Exception  If there is an error reading the command.
     */
    public Command getCommand(String sentence) throws Exception {
        Command cmd = Parser.parse(sentence, items);
        return cmd;
    }

    /**
     * Executes a command to overwrite exiting storage with an updated task list (GUI).
     *
     * @param cmd Command to be executed.
     * @throws IOException  If there is an error writing the text file.
     */
    public void saveState(Command cmd) throws IOException {
        cmd.executeStorage(items,ui,storage);
    }

    /**
     * Executes a command and outputs the result to the user (GUI).
     *
     * @param cmd Command to be executed.
     * @return String to be outputted.
     */
    public String executeCommand(Command cmd) {
        String str = cmd.executeGui(items, ui);
        return str;
    }

    /**
     * Runs the duke program until exit command is executed.
     */
    public void run() {
        ui.showWelcome();
        Ui.showReminder(items);
        String sentence;

        while (true) {
            sentence = ui.readCommand();
            ui.showLine(); //Please do not remove!
            try {
                Command cmd = Parser.parse(sentence, items);
                if (cmd instanceof ExitCommand) {
                    priorityStorage.write(priorityList);
                    cmd.executeStorage(items, ui, storage);
                    break;
                } else if (cmd instanceof ListPriorityCommand
                        || cmd instanceof AddMultipleCommand
                        || cmd instanceof DeleteCommand
                        || cmd instanceof SetPriorityCommand) {
                    cmd.execute(items, priorityList, ui);
                } else if (cmd instanceof BackupCommand) {
                    cmd.executeStorage(items, ui, storage);
                } else if (cmd instanceof BudgetCommand) {
                    cmd.execute(items,budgetStorage,ui);
                } else if (cmd instanceof AddContactsCommand) {
                    cmd.execute(items, contactList, ui);
                    cmd.executeStorage(items, ui, contactStorage,contactList);
                } else if (cmd instanceof ListContactsCommand) {
                    cmd.execute(items, contactList, ui);
                } else {
                    cmd.execute(items,ui);
                    priorityList = priorityList.addDefaultPriority(cmd);
                }
            } catch (DukeException e) {
                ui.showErrorMsg(e.getMessage());
            } catch (Exception e) {
                ui.showErrorMsg("     New error, please fix:");
                e.printStackTrace();
                ui.showErrorMsg("     Duke will continue as per normal.");
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Duke("data/duke.txt", "data/priority.txt", "data/budget.txt","data/contacts.txt").run();
    }
}