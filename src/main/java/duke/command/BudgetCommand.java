package duke.command;

import duke.storage.Storage;
import duke.task.Budget;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Representing a command that adds a new task.
 */
public class BudgetCommand extends Command {
    protected Budget budget;

    /**
     * Creates a command with the specified task.
     *
     * @param budget The budget to be added.
     */
    public BudgetCommand(Budget budget) {
        this.budget = budget;
    }


    @Override
    public void execute(TaskList items, Ui ui) {

    }


    public void execute(TaskList items, float budgetStorage, Ui ui) {

    }


    @Override
    public String executeGui(TaskList items, Ui ui) {
    return "";
    }

    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) {
    }
}