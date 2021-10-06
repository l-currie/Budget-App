package persistence;

import model.BudgetMonth;
import model.Income;
import model.Expense;
import org.json.JSONObject;

import java.io.*;

//MODIFIED FROM JsonSerializationDemo
//Creates a JSON file if there is not already one in the data folder.
//Writes JSON representations of objects into the file, receives these representations from the object's own method.
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constrcuts a writer to write to destination file.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //Modifies: this
    //EFFECTS: opens writer, throws FileNotFoundException if destination file unable to be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: writes a JSON representation of BudgetMonth to the file.
    public void write(BudgetMonth bm) {
        JSONObject json = bm.toJson();
        saveToFile(json.toString(TAB));
    }

    //EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes String to file.
    private void saveToFile(String json) {
        writer.print(json);
    }
}
