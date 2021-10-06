package model;
/*
-----------------------------------------------------------------------------------------

Income class has fields amount and description,

- amount is a double which stores the amount of money the user made from this Income
- description is a String which stores a brief description of the source, e.g "work"

Methods are getter's and setters.

-----------------------------------------------------------------------------------------
 */

import org.json.JSONObject;
import persistence.Writeable;

public class Income implements Writeable {

    private double amount;
    private String description;

    //Creates an Income instance by setting fields to values inputted to the method by the user.
    public Income(double amnt, String desc) {
        this.amount = amnt;
        this.description = desc;
    }

    //EFFECTS: returns amount
    public double getAmount() {
        return this.amount;
    }

    //EFFECTS: returns description
    public String getDescription() {
        return this.description;
    }

    //MODIFIES: this
    //EFFECTS: sets amount to newAmount
    public void setAmount(double newAmount) {
        this.amount = newAmount;
    }

    //MODIFIES: this
    //EFFECTS: sets description to newDescription
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    //EFFECTS: returns a JsonObject representation of the Income
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", this.amount);
        json.put("description", this.description);
        return json;
    }

}
