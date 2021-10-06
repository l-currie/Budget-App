package model;

import org.json.JSONObject;
import persistence.Writeable;

public class Expense implements Writeable {
/*
-----------------------------------------------------------------------------------------

Expense class has fields amount, description and category.

- amount is a double which stores the amount of money the user spent on the item
- description is a String which describes the item
- category is a String which categorizes the purchase

Methods are getter's and setter's

-----------------------------------------------------------------------------------------
 */

    private double amount;
    private String description;
    private String category;

    //Creates an expense Instance, sets objects fields to values inputted to the method by user.
    public Expense(double a, String d, String c) {
        this.amount = a;
        this.description = d;
        this.category = c;
    }

    //EFFECTS: returns objects amount
    public Double getAmount() {
        return this.amount;
    }

    //EFFECTS: returns objects description
    public String getDescription() {
        return this.description;
    }

    //EFFECTS: returns objects category
    public String getCategory() {
        return this.category;
    }

    //EFFECTS: returns a JsonObject representation of the Expense
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", this.amount);
        json.put("description", this.description);
        json.put("category", this.category);
        return json;
    }

}


