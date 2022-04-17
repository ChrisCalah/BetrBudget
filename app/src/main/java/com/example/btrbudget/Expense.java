package com.example.btrbudget;

public class Expense {

    // define member variables
    public double amount;
    public String date;
    public String name;
    public String description;

    public Expense(double expenseAmount, String dateOfExpense,
                   String nameOfExpense, String expenseDesc)
    {
        amount = expenseAmount;
        date = dateOfExpense;
        name = nameOfExpense;
        description = expenseDesc;
    }
}
