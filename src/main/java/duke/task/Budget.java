package duke.task;
import duke.ui.Ui;


public class Budget {

    protected float amount;
    protected Ui ui;

    /**
     * Creates a new budget
     *
     * @param inputAmount amount to be inputted
     */
    public Budget(String inputAmount) {
        float floatInputAmount = Float.parseFloat(inputAmount);
        this.amount = floatInputAmount;
    }

    public void updateBudget(String amount) {
        float updateAmount = Float.parseFloat(amount);
        this.amount = updateAmount;
        ui.showBudget(this.amount);
    }

    public void addBudget(String amount) {
        float addAmount = Float.parseFloat(amount);
        this.amount = this.amount + addAmount;
        ui.showAddedBudget(addAmount, this.amount);
    }

    public void minusBudget(String amount) {
        float minusAmount = Float.parseFloat(amount);
        this.amount = this.amount - minusAmount;
        ui.showMinusBudget(minusAmount, this.amount);
    }

    public void viewBudget() {
        ui.showBudget(this.amount);
    }

    public String toFile() {
        return "Amount: " + amount;
    }


    @Override
    public String toString() {
        return "Amount: " + amount;
    }


    public String toStringGui() {
        return "Amount: " + amount;
    }
}

