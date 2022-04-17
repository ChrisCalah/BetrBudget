package com.example.btrbudget;

public class Expense {

    // define member variables
    public double amount;
    public String date;
    public String name;

    public Expense(double expenseAmount, String dateOfExpense,
                   String nameOfExpense)
    {
        amount = expenseAmount;
        date = dateOfExpense;
        name = nameOfExpense;
    }
}
