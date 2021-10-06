package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestIncome {

    private Income testIncome;

    @BeforeEach
    public void initTests(){
        testIncome = new Income(250, "Test income.");
    }

    @Test
    public void testGetDescription(){
        assertEquals(testIncome.getDescription(), "Test income.");
    }

    @Test
    public void testSetAmount(){
        assertEquals(testIncome.getAmount(), 250);
        testIncome.setAmount(100);
        assertEquals(testIncome.getAmount(), 100);
    }

    @Test
    public void testSetDescription(){
        assertEquals(testIncome.getDescription(), "Test income.");
        testIncome.setDescription("Description Changed");
        assertEquals(testIncome.getDescription(), "Description Changed");
    }

    //getter and setter tests

}
