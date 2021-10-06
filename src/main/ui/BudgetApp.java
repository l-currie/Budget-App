package ui;

import model.BudgetMonth;
import model.Expense;
import model.Income;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

/*
-----------------------------------------------------------------------------------------

BudgetApp class effectively runs the entire program.

Has the fields month and input

- month is type BudgetMonth, Stores and processes data that the user inputs
- input is type Scanner, scans user input.

-----------------------------------------------------------------------------------------
 */
// ----------------------User Interface--------------------

public class BudgetApp {


    private BudgetMonth month;
    private Scanner input;

    //BudgetApp constructor, calls runBudgetApp
    public BudgetApp() {
        runBudgetApp();
    }

    //Inspiration from TellerApp
    //EFFECTS: runs the BudgetApp, processes user input.
    public void runBudgetApp() {
        boolean runApp = true;
        String action = "";

        initialize();

        while (runApp) {
            displayMenu();
            action = input.next();
            action.toString();

            if (action.equals("q") || action.equals("Q")) {
                runApp = false;
            } else {
                doInput(action);
            }
        }

        System.out.println("\n Goodbye, have a terrific day!");
    }

    //MODIFIES: this
    //EFFECTS: Get user to input month and username, create new BudgetMonth with inputted vals.
    public void initialize() {
        input = new Scanner(System.in);

        System.out.println("Please enter your Name: \n");
        String username = input.next();
        System.out.println("Please enter the Month: \n");
        String month = input.next();

        this.month = new BudgetMonth(username, month);
    }

    //EFFECTS: displays the menu with all options.
    public void displayMenu() {
        // add it to say Hi username, this is your budget for the month month, please select:
        System.out.println("\nHi " + month.getUsername() + "! This is your budget for the month of: "
                + month.getMonth());
        System.out.println("\n\t Please select from the features below!");
        System.out.println("\t1 -> add a new income");
        System.out.println("\t2 -> add a new expense");
        System.out.println("\t3 -> view all incomes");
        System.out.println("\t4 -> view all expenses");
        System.out.println("\t5 -> view all expenses of a category");
        System.out.println("\t6 -> Save current Budget");
        System.out.println("\t7 -> Load a previously saved budget");
        System.out.println("\tq -> quit");
    }


    //MODIFIES: this
    //EFFECTS:  determines what command user wants to do, calls method to execute command.
    public void doInput(String input) {
        if (input.equals("1")) {
            doNewIncome();
        } else if (input.equals("2")) {
            doNewExpense();
        } else if (input.equals("3")) {
            printListOfIncome();
        } else if (input.equals("4")) {
            printListOfExpense();
        } else if (input.equals("5")) {
            printListOfExpenseOfCategory();
        } else if (input.equals("6")) {
            doSaveBudgetMonth();
        } else if (input.equals("7")) {
            try {
                doLoadBudgetMonth();
            } catch (IOException e) {
                System.out.println("ERROR: no file found with inputted name and month, please try again");
            }
        } else {
            System.out.println("Input was invalid, please try again...");
        }
    }


    //-------------------------- FEATURES ------------------------

    //MODIFIES: this
    //EFFECTS: creates a new income from inputs, adds it to BudgetMonth.
    public void doNewIncome() {
        System.out.println("Enter income amount: $");
        double amount = input.nextDouble();

        System.out.println("Enter income description: ");
        String description = input.next().toLowerCase(Locale.ROOT);

        Income newIncome = new Income(amount, description);
        this.month.addIncome(newIncome);

        System.out.println("\n Income successfully added!");


    }

    //MODIFIES: this
    //EFFECTS: creates a new expense from inputs, adds it to BudgetMonth.
    public void doNewExpense() {
        System.out.println("Enter expense amount: $");
        double amount = input.nextDouble();

        System.out.println("Enter expense description: ");
        String description = input.next().toLowerCase(Locale.ROOT);

        System.out.println("Enter expense category: ");
        String category = input.next().toLowerCase(Locale.ROOT);

        Expense newExpense = new Expense(amount, description, category);
        this.month.addExpense(newExpense);

        System.out.println("\n Expense successfully added!");

    }

    //EFFECTS: Prints out each income in month incomeList
    public void printListOfIncome() {
        System.out.println("\n Here is a list of your income sources this month: ");
        for (String s : this.month.incomeListToStringList()) {
            System.out.println("\t" + s);
        }

        System.out.println("Your total Income for this month is: $" + month.totalIncome());
        System.out.println("Please press 'm' to go back to the menu!");
        String menu = input.next();

        if (menu.equals("m")) {
            return;
        }
    }

    //EFFECTS: Prints out each expense in month expenseList
    public void printListOfExpense() {

        System.out.println("\n Here is a list of your expenses this month: ");
        for (String s : this.month.expenseListToStringList(month.getExpenseList())) {
            System.out.println("\t" + s);
        }
        System.out.println("Your total Expenses for this month is: $" + month.totalExpenses());
        System.out.println("\nPlease press 'm' to go back to the menu!");
        String menu = input.next();

        if (menu.equals("m")) {
            return;
        }
    }

    //EFFECTS: prints each element in a ListOfString that is related to an expense instance with category category.
    public void printListOfExpenseOfCategory() {
        LinkedList<String> stringList;
        LinkedList<Expense> tempList;

        System.out.println("Please enter the category of expense you want to see!");
        String category = input.next().toLowerCase(Locale.ROOT);
        tempList = this.month.expenseListOfCategory(category);

        stringList = this.month.expenseListToStringList(tempList);

        System.out.println("Here are your expenses with category '" + category + "'");
        for (String s : stringList) {
            System.out.println("\t" + s);
        }

        System.out.println("Your total expenses this month of category " + category + " are: $"
                + month.totalExpensesOfCategory(category));
        System.out.println("\nPlease press 'm' to go back to the menu!");
        String menu = input.next();

        if (menu.equals("m")) {
            return;
        }
    }

    public void doLoadBudgetMonth() throws IOException {

        System.out.println("Please input the username of the budget you want to load: ");
        String name = input.next();
        System.out.println("Please input the month of the budget you want to load: ");
        String month = input.next();


        String jsonFileName = "./data/" + name + "_" + month + ".json";
        JsonReader reader = new JsonReader(jsonFileName);

        this.month = reader.read();

        System.out.println("Success: Budget found! ... Returning to main menu");
    }

    public void doSaveBudgetMonth() {
        try {
            System.out.println("Saving Budget month...");
            String destination = "./data/" + month.getUsername() + "_" + month.getMonth() + ".json";
            JsonWriter writer = new JsonWriter(destination);

            writer.open();
            writer.write(month);
            writer.close();

            System.out.println("Successfully saved your budget to " + destination);

        } catch (IOException e) {
            System.out.println("ERROR WRITING JSON FILE, username or month are probably invalid");
        }

    }
}



