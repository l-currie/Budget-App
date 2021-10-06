package model;

import model.exceptions.InvalidIndex;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.LinkedList;

/*
-----------------------------------------------------------------------------------------

BudgetMonth class stores and operates on data for a particular user's budget.

Stores instances of Income in the incomeList field.
Stores instances of Expense in the expenseList field.
Stores the user's name and the month for the budget.

- Has methods for calculating the totalIncome for a month, total expenses for a month
- Can  give the size of incomeList.
- Has methods to convert LinkedList<Income/Expense> to list of strings with important data
  of the objects.
- Has a method to get expenses from a certain category.

-----------------------------------------------------------------------------------------
 */
public class BudgetMonth implements Writeable {

    private LinkedList<Income> incomeList;
    private LinkedList<Expense> expenseList;
    private String month;
    private String username;


    //EFFECTS: Sets username to user, and this.month to month.
    public BudgetMonth(String user, String month) {
        this.username = user;
        this.month = month;
        this.incomeList = new LinkedList<Income>();
        this.expenseList = new LinkedList<Expense>();
    }

    //MODIFIES: this
    //EFFECTS: adds income to incomeList, returns
    public void addIncome(Income i) {
        this.incomeList.addFirst(i);
    }

    //EFFECTS: returns first Income in incomeList
    public Income getFirstIncome() {
        return this.incomeList.getFirst();
    }

    //MODIFIES: this
    //EFFECTS: adds expense to expenseList
    public void addExpense(Expense e) {
        this.expenseList.addFirst(e);
    }

    //EFFECTS: return first expense in expenseList
    public Expense getFirstExpense() {
        return this.expenseList.getFirst();
    }

    //EFFECTS: returns size of incomeList
    public int incomeListSize() {
        return incomeList.size();
    }

    //EFFECTS: returns size of expenseList
    public int expenseListSize() {
        return expenseList.size();
    }

    //EFFECTS: returns expenseList
    public LinkedList<Expense> getExpenseList() {
        return this.expenseList;
    }

    //EFFECTS: returns expenseList
    public LinkedList<Income> getIncomeList() {
        return this.incomeList;
    }

    //EFFECTS: returns username
    public String getUsername() {
        return this.username;
    }

    //EFFECTS: returns month
    public String getMonth() {
        return this.month;
    }

    //EFFECTS: returns total amount of income for the month
    public double totalIncome() {
        double total = 0;
        for (Income i : this.incomeList) {
            total += i.getAmount();
        }
        return total;
    }

    //EFFECTS: returns total expenses for the month
    public double totalExpenses() {
        double total = 0;
        for (Expense e : this.expenseList) {
            total += e.getAmount();
        }
        return total;
    }

    //EFFECTS: returns total expenses for the month of category x
    public double totalExpensesOfCategory(String category) {
        double total = 0;
        for (Expense e : expenseList) {
            if (e.getCategory().equals(category)) {
                total += e.getAmount();
            }
        }
        return total;
    }

    //EFFECTS: Produces a LinkedList<String> with each item in list being "amount, description" of Income in incomeList
    public LinkedList<String> incomeListToStringList() {
        LinkedList<String> result = new LinkedList<>();
        for (Income i : this.incomeList) {
            result.addLast(i.getAmount() + " , " + i.getDescription());
        }
        return result;
    }

    //EFFECTS: Produces a LinkedList<String> with each item in list being "amount, description, category" of
    // Expense in expenseList
    public LinkedList<String> expenseListToStringList(LinkedList<Expense> expList) {
        LinkedList<String> result = new LinkedList<>();
        for (Expense e : expList) {
            result.addLast(e.getAmount() + " , " + e.getDescription() + " , " + e.getCategory());
        }
        return result;
    }

    //EFFECTS: sets expenseListCategory to a LinkedList<Expense> of all expenses with inputted Category
    public LinkedList<Expense> expenseListOfCategory(String c) {
        LinkedList<Expense> result = new LinkedList<>();
        for (Expense e : this.expenseList) {
            if (e.getCategory().equals(c)) {
                result.addLast(e);
            }
        }
        return result;
    }

    @Override
    //EFFECTS: converts BudgetMonth to a JsonObject
    public JSONObject toJson() {
        JSONObject jsonOBJ = new JSONObject();
        jsonOBJ.put("name", username);
        jsonOBJ.put("month", month);
        jsonOBJ.put("Incomes", incomesToJson());
        jsonOBJ.put("Expenses", expensesToJson());

        return jsonOBJ;
    }

    //EFFECTS: returns incomes in this BudgetMonth as a JSON array
    private JSONArray incomesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Income i : this.incomeList) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns incomes in this BudgetMonth as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense e : this.expenseList) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

    //modifies this
    //EFFECTS: removes the expense at index num-1 from the expenseList;
    public void removeExpense(int num) throws InvalidIndex {
        if (num > this.getExpenseList().size() || num < 0) {
            throw new InvalidIndex();
        }
        this.getExpenseList().remove(num - 1);

    }


    //modifies this
    //EFFECTS: removes the income at index num-1 from the incomeList;
    public void removeIncome(int num) throws InvalidIndex {
        if (num > this.getIncomeList().size() || num < 0) {
            throw new InvalidIndex();
        }
        this.getIncomeList().remove(num - 1);

    }


}

