package persistence;

import model.BudgetMonth;
import model.Expense;
import model.Income;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//Largely modelled after JsonSerializationDemo
//represents a reader that reads BudgetMonth from data stored in a file.
// reads JSON objects from json file in data folder.  Can return objects such as a BudgetMonth from json file reading.
public class JsonReader {

    private String source;

    //creates a new JsonReader
    public JsonReader(String source) {
        this.source = source;
    }

    //Modified from JsonSerializationDemo
    //EFFECTS: reads BudgetMonth from file and returns it
    //Throws IOException if there is an error in reading.
    public BudgetMonth read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBudgetMonth(jsonObject);
    }

    //Taken from JsonSerializationDemo
    //EFFECTS: reads the file as a string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //Modified from JsonSerializationDemo
    // EFFECTS: parses workroom from JSON object and returns it
    private BudgetMonth parseBudgetMonth(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String month = jsonObject.getString("month");
        BudgetMonth bm = new BudgetMonth(name, month);
        addIncomes(bm, jsonObject);
        addExpenses(bm, jsonObject);
        return bm;
    }

    // modified from JsonSerializationDemo
    // MODIFIES: bm
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addIncomes(BudgetMonth bm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Incomes");
        for (Object json : jsonArray) {
            JSONObject nextIncome = (JSONObject) json;
            addIncome(bm, nextIncome);
        }
    }

    // MODIFIES: bm
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addIncome(BudgetMonth bm, JSONObject jsonObject) {
        double amount = jsonObject.getDouble("amount");
        String description = jsonObject.getString("description");
        Income income = new Income(amount, description);
        bm.addIncome(income);
    }


    // modified from JsonSerializationDemo
    // MODIFIES: bm
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addExpenses(BudgetMonth bm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Expenses");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(bm, nextExpense);
        }
    }

    // MODIFIES: bm
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addExpense(BudgetMonth bm, JSONObject jsonObject) {
        double amount = jsonObject.getDouble("amount");
        String description = jsonObject.getString("description");
        String category = jsonObject.getString("category");
        Expense expense = new Expense(amount, description, category);
        bm.addExpense(expense);
    }



}
