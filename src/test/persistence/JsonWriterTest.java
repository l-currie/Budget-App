package persistence;


import model.BudgetMonth;
import model.Expense;
import model.Income;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            BudgetMonth bm = new BudgetMonth("Lucas", "July");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //Pass
        }
    }

    @Test
    void testWriterEmptyBudgetMonth() {

        try {
            BudgetMonth bm = new BudgetMonth("Lucas", "July");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBudgetMonth.json");
            writer.open();
            writer.write(bm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBudgetMonth.json");
            bm = reader.read();
            assertEquals("July", bm.getMonth());
            assertEquals("Lucas", bm.getUsername());
        } catch (IOException e) {
            fail("exception shouldnt be thrown");
        }
    }

    @Test
    void testWriterNonEmptyListsBudgetMonth(){
        try {
            BudgetMonth bm = new BudgetMonth("Lucas", "July");
            bm.addIncome(new Income(250,"work"));
            bm.addExpense(new Expense(10, "coffee", "food"));
            JsonWriter writer = new JsonWriter("./data/testWriterNonEmptyListsBudgetMonth.json");
            writer.open();
            writer.write(bm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNonEmptyListsBudgetMonth.json");
            bm = reader.read();
            assertEquals("July", bm.getMonth());
            assertEquals("Lucas", bm.getUsername());
            List<Income> incomeList = bm.getIncomeList();
            List<Expense> expenseList = bm.getExpenseList();
            assertEquals(1,incomeList.size());
            assertEquals(1,expenseList.size());

            checkIncome(250, "work", incomeList.get(0));
            checkExpense(10, "coffee", "food", expenseList.get(0));
        } catch (IOException e){
            fail();
        }
    }

}


