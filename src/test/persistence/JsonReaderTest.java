package persistence;


import model.Expense;
import model.Income;
import model.BudgetMonth;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    //Modified From JsonSerializationDemo
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/lolNoFile.json");
        try {
            BudgetMonth bm = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBudgetMonth(){
        JsonReader reader = new JsonReader("./data/testReaderEmptyBudgetMonth.json");
        try {
            BudgetMonth bm = reader.read();
            assertEquals("Lucas", bm.getUsername());
            assertEquals("July", bm.getMonth());
            List<Income> incomeList = bm.getIncomeList();
            List<Expense> expenseList = bm.getExpenseList();
            assertEquals(0, incomeList.size());
            assertEquals(0,expenseList.size());
        } catch (Exception e) {
            fail("Couldn't read from file, exception thrown.");
        }
    }

    @Test
    void testReader1Income1ExpenseBudgetMonth(){
        JsonReader reader = new JsonReader("./data/testReader1Income1ExpenseBudgetMonth.json");
        try {
            BudgetMonth bm = reader.read();
            assertEquals("Lucas", bm.getUsername());
            assertEquals("July", bm.getMonth());
            List<Income> incomeList = bm.getIncomeList();
            List<Expense> expenseList = bm.getExpenseList();
            assertEquals(1, incomeList.size());
            checkIncome(250,"work",incomeList.get(0));
            assertEquals(1,expenseList.size());
            checkExpense(250,"coffee","food",expenseList.get(0));
        } catch (Exception e) {
            fail("Couldn't read from file, exception thrown.");
        }
    }
}