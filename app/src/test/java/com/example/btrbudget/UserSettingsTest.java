package com.example.btrbudget;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class UserSettingsTest {
    @Test
    public void addExpense(Expense newExp)
    {
        // set input and expected output
        Expense input = new Expense(100, "4/8/2022", "rent");
        ArrayList<Expense> expected = new ArrayList<Expense>(Arrays.asList(input));

        // define a test User setting
        UserSettings testUser = new UserSettings();
        testUser.addExpense(input);

        // assert that testUsers expense list equals output
            // function: assertEquals()
        assertEquals(testUser.expenseList, expected);
    }
}