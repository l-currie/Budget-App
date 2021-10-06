package model;

import model.exceptions.InvalidIndex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBudgetMonth {

    private BudgetMonth testBudget;

    private Expense testExpense1;
    private Expense testExpense2;
    private Expense testExpense3;

    private Income testIncome1;
    private Income testIncome2;
    private Income testIncome3;

    @BeforeEach
    public void initTests() {
        this.testBudget = new BudgetMonth("user", "July");
        this.testExpense1 = new Expense(100, "Coffee", "Food");
        this.testExpense2 = new Expense(50, "Pizza", "Food");
        this.testExpense3 = new Expense(300, "TV", "Entertainment");

        this.testIncome1 = new Income(1000, "Work");
        this.testIncome2 = new Income(50, "Birthday gift");
        this.testIncome3 = new Income(250, "Work");
    }

    @Test
    public void testAddIncome1() {
        testBudget.addIncome(testIncome1);
        //List should contain only testIncome1
        assertEquals(testBudget.getFirstIncome(), testIncome1);
        assertEquals(testBudget.incomeListSize(), 1);
    }

    @Test
    public void testAddMultipleIncome() {
        testBudget.addIncome(testIncome1);
        //List should contain only testIncome1
        assertEquals(testBudget.getFirstIncome(), testIncome1);
        testBudget.addIncome(testIncome2);
        //List should be [testIncome2, testIncome1]
        assertEquals(testBudget.getFirstIncome(), testIncome2);
        assertEquals(testBudget.incomeListSize(), 2);
    }

    @Test
    public void testAddExpense1() {
        testBudget.addExpense(testExpense1);
        //List should contain only testExpense1
        assertEquals(testBudget.getFirstExpense(), testExpense1);
    }

    @Test
    public void testAddMultipleExpense() {
        testBudget.addExpense(testExpense1);
        //List should contain only testExpense1
        assertEquals(testBudget.getFirstExpense(), testExpense1);
        testBudget.addExpense(testExpense2);
        //List should be [testExpense2, testExpense1]
        assertEquals(testBudget.getFirstExpense(), testExpense2);
        assertEquals(testBudget.expenseListSize(), 2);
    }

    @Test
    public void testIncomeListSizeEmpty() {
        //List should be empty
        assertEquals(testBudget.incomeListSize(), 0);
    }

    @Test
    public void testIncomeListSize1() {
        testBudget.addIncome(testIncome1);
        //List should only contain testIncome1
        assertEquals(testBudget.incomeListSize(), 1);
    }

    @Test
    public void testExpenseListSizeEmpty() {
        //List should be empty
        assertEquals(testBudget.expenseListSize(), 0);
    }

    @Test
    public void testExpenseListSize1() {
        //list should contain only testExpense1
        testBudget.addExpense(testExpense1);
        assertEquals(testBudget.expenseListSize(), 1);
    }

    @Test
    public void testGetFirstIncome1() {
        testBudget.addIncome(testIncome1);
        //list should contain only testIncome1
        assertEquals(testBudget.getFirstIncome(), testIncome1);
    }

    @Test
    public void testGetFirstIncome2() {
        testBudget.addIncome(testIncome1);
        testBudget.addIncome(testIncome2);
        //List should be [testIncome2, testIncome1] therefore testIncome2 is first
        assertEquals(testBudget.getFirstIncome(), testIncome2);
    }

    @Test
    public void testGetFirstExpense1() {
        testBudget.addExpense(testExpense1);
        //list should only contains testExpense1
        assertEquals(testBudget.getFirstExpense(), testExpense1);
    }

    @Test
    public void testGetFirstExpense2() {
        testBudget.addExpense(testExpense1);
        testBudget.addExpense(testExpense3);
        //List should be [testExpense3, testExpense1] first should be testExpense3
        assertEquals(testBudget.getFirstExpense(), testExpense3);
    }

    @Test
    public void testTotalIncome0() {
        //total income should be 0 as we have no incomes added when initializing tests
        assertEquals(testBudget.totalIncome(), 0);
    }

    @Test
    public void testTotalIncome3() {
        testBudget.addIncome(testIncome1);
        testBudget.addIncome(testIncome2);
        testBudget.addIncome(testIncome3);
        //total income should be the amounts of testIncome1 + testIncome2 + testIncome3 = 1300
        assertEquals(testBudget.totalIncome(), 1300);
    }

    @Test
    public void testTotalExpenses0() {
        //total expense should be 0 since we have no expenses when initializing
        assertEquals(testBudget.totalExpenses(), 0);
    }

    @Test
    public void testTotalExpenses2() {
        testBudget.addExpense(testExpense1);
        testBudget.addExpense(testExpense2);
        //total expense should be the amounts of testExpense1 + testExpense2   = 150
        assertEquals(testBudget.totalExpenses(), 150);
    }

    @Test
    public void testTotalExpensesOfCategory0Expenses() {
        //total expenses of both category should be 0 since we have no expenses added.
        assertEquals(testBudget.totalExpensesOfCategory("Food"), 0);
        assertEquals(testBudget.totalExpensesOfCategory("Entertainment"), 0);
    }

    @Test
    public void testTotalExpensesOfCategory1ExpNoneOfCategory() {
        //total expenses of Entertainment should be 0 since no expenses of category Entertainment are in expenseList
        testBudget.addExpense(testExpense2);
        assertEquals(testBudget.totalExpensesOfCategory("Entertainment"), 0);
    }

    @Test
    public void testTotalExpensesOfCategory1ExpOfCategory() {
        //total expenses 50 since there is one expense of category Food with amount 50 in the expenseList
        testBudget.addExpense(testExpense2);
        assertEquals(testBudget.totalExpensesOfCategory("Food"), 50);
    }

    @Test
    public void testTotalExpensesOfCategory2ExpOfCategory1ExpDifferent() {
        testBudget.addExpense(testExpense1);
        testBudget.addExpense(testExpense2);
        testBudget.addExpense(testExpense3);
        //total expense for Food 150 since we add the amounts of both Food category expenses
        //total expense for Entertainment is 300 since we have only one expense with the category.
        assertEquals(testBudget.totalExpensesOfCategory("Food"), 150);
        assertEquals(testBudget.totalExpensesOfCategory("Entertainment"), 300);
    }

    @Test
    public void testIncomeListToStringListEmpty() {
        //income list is empty (size 0) therefore the size of the string list representing incomeList should be 0 also
        assertEquals(testBudget.incomeListToStringList().size(), 0);
    }

    @Test
    public void testIncomeListToStringList1() {
        testBudget.addIncome(testIncome1);
        // we have one income in the list therefore size 0
        assertEquals(testBudget.incomeListToStringList().size(), 1);
        // check that testIncome1 was added properly
        assertEquals(testBudget.incomeListToStringList().getFirst(), "1000.0 , Work");
    }

    @Test
    public void testIncomeListToStringList2() {
        testBudget.addIncome(testIncome1);
        testBudget.addIncome(testIncome2);
        //now we have two income in list, therefore String representation should have 2 also.
        assertEquals(testBudget.incomeListToStringList().size(), 2);
        //check that income were added properly.
        assertEquals(testBudget.incomeListToStringList().getLast(), "1000.0 , Work");
        assertEquals(testBudget.incomeListToStringList().getFirst(), "50.0 , Birthday gift");
    }


    @Test
    public void testExpenseListToStringListEmpty() {
        //expense list starts empty, therefore String representation is also empty, size 0
        assertEquals(testBudget.expenseListToStringList(this.testBudget.getExpenseList()).size(), 0);
    }

    @Test
    public void testExpenseListToStringList1() {
        testBudget.addExpense(testExpense1);
        //expenseList now has 1 item, therefore String representation now has 1 item, size 1
        assertEquals(testBudget.expenseListToStringList(this.testBudget.getExpenseList()).size(), 1);
        //check that the right object was added correctly.
        assertEquals(testBudget.expenseListToStringList(this.testBudget.getExpenseList()).getFirst(),
                "100.0 , Coffee , Food");
    }

    @Test
    public void testExpenseListToStringList2() {
        testBudget.addExpense(testExpense1);
        testBudget.addExpense(testExpense2);
        // expenseList is now size 2, the String representation should be size 2 also.
        assertEquals(testBudget.expenseListToStringList(this.testBudget.getExpenseList()).size(), 2);
        //check that Strings were added properly.
        assertEquals(testBudget.expenseListToStringList(this.testBudget.getExpenseList()).getLast(),
                "100.0 , Coffee , Food");
        assertEquals(testBudget.expenseListToStringList(this.testBudget.getExpenseList()).getFirst(),
                "50.0 , Pizza , Food");
    }

    @Test
    public void testExpenseListOfCategoryEmpty() {
        // no expenses, therefore when we get list of expense of category x, still size 0
        assertEquals(testBudget.expenseListOfCategory("Food").size(), 0);
    }

    @Test
    public void testExpenseListOfCategory1NoMatch() {
        testBudget.addExpense(testExpense3);
        // 1 expense now, but the category is different, stil size 0
        assertEquals(testBudget.expenseListOfCategory("Food").size(), 0);
    }

    @Test
    public void testExpenseListOfCategory1MatchMultiple() {
        testBudget.addExpense(testExpense3);
        testBudget.addExpense(testExpense1);
        //size 2 for expenseList, only 1 matches category, size 1
        assertEquals(testBudget.expenseListOfCategory("Food").size(), 1);
        //checks that correct expense is added in the list.
        assertEquals(testBudget.expenseListOfCategory("Food").getLast(), testExpense1);
    }

    @Test
    public void testRemoveExpenseNoException() {

        try {
            testBudget.addExpense(testExpense1);
            testBudget.addExpense(testExpense2);
            testBudget.addExpense(testExpense3);
            assertEquals(3, testBudget.getExpenseList().size());
            testBudget.removeExpense(2);

            assertEquals(2, testBudget.getExpenseList().size());
            assertEquals(testExpense3, testBudget.getExpenseList().getFirst());
            assertEquals(testExpense1, testBudget.getExpenseList().getLast());
        } catch (InvalidIndex exc) {
            fail("InvalidIndex should not have been thrown");
        }
    }

    @Test
    public void testRemoveExpenseThrowExc() {

        try {
            testBudget.addExpense(testExpense1);
            testBudget.addExpense(testExpense2);
            testBudget.addExpense(testExpense3);
            assertEquals(3, testBudget.getExpenseList().size());
            testBudget.removeExpense(4);
            fail("InvalidIndex should have been thrown");
        } catch (InvalidIndex exc) {
            //EXPECTED
        }
    }

    @Test
    public void testRemoveExpenseThrowExc2() {

        try {
            testBudget.addExpense(testExpense1);
            testBudget.addExpense(testExpense2);
            testBudget.addExpense(testExpense3);
            assertEquals(3, testBudget.getExpenseList().size());
            testBudget.removeExpense(-1);
            fail("InvalidIndex should have been thrown");
        } catch (InvalidIndex exc) {
            //EXPECTED
        }
    }

    @Test
    public void testRemoveIncome() {

        try {
            testBudget.addIncome(testIncome1);
            testBudget.addIncome(testIncome2);
            testBudget.addIncome(testIncome3);
            assertEquals(3, testBudget.getIncomeList().size());
            testBudget.removeIncome(2);

            assertEquals(2, testBudget.getIncomeList().size());
            assertEquals(testIncome3, testBudget.getIncomeList().getFirst());
            assertEquals(testIncome1, testBudget.getIncomeList().getLast());
        } catch (InvalidIndex exc) {
            fail("InvalidIndex should not have been thrown");
        }
    }

    @Test
    public void testRemoveIncomeThrowException() {

        try {
            testBudget.addIncome(testIncome1);
            testBudget.addIncome(testIncome2);
            testBudget.addIncome(testIncome3);
            assertEquals(3, testBudget.getIncomeList().size());
            testBudget.removeIncome(4);
            fail("Expected an InvalidIndex exception to be thrown");

        } catch (InvalidIndex exc) {
            // good

        }
    }

    @Test
    public void testRemoveIncomeThrowException2() {

        try {
            testBudget.addIncome(testIncome1);
            testBudget.addIncome(testIncome2);
            testBudget.addIncome(testIncome3);
            assertEquals(3, testBudget.getIncomeList().size());
            testBudget.removeIncome(-1);
            fail("Expected an InvalidIndex exception to be thrown");

        } catch (InvalidIndex exc) {
            // good

        }
    }

}

