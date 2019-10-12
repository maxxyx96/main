package duke.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BudgetStorage {
    protected String budgetFilePath = "./";

    /**
     * Creates a storage with a specified budgetFilePath
     *
     * @param budgetFilePath The location of the budget
     */
    public BudgetStorage(String budgetFilePath) {
        this.budgetFilePath += budgetFilePath;
    }

    public float read() throws IOException{
        File file = new File(budgetFilePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String storedAmount = br.readLine();
        br.close();
        return Float.parseFloat(storedAmount);
    }

    public void write(float budget) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(budgetFilePath));
        writer.write(Float.toString(budget));
        writer.close();
    }
}
