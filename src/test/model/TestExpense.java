package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestExpense {

    private Expense testExpense;

    @BeforeEach
    public void initTests() {
        this.testExpense = new Expense(250, "Test Description", "Test");
    }

    @Test
    public void TestGetAmount() {
        assertEquals(testExpense.getAmount(), 250);
    }

    @Test
    public void TestGetDescription() {
        assertEquals(testExpense.getDescription(), "Test Description");
    }

    @Test
    public void TestGetCategory() {
        assertEquals(testExpense.getCategory(), "Test");
    }

    //getter tests

}

