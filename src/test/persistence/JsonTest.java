package persistence;

import model.Income;
import model.Expense;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Based off the JsonSerializationDemo..
public class JsonTest {
    protected void checkIncome(int amount, String description, Income income) {
        assertEquals(amount, income.getAmount());
        assertEquals(description, income.getDescription());
    }

    protected void checkExpense(int amount, String description, String category, Expense expense) {
        assertEquals(amount, expense.getAmount());
        assertEquals(description, expense.getDescription());
        assertEquals(category, expense.getCategory());
    }
}
