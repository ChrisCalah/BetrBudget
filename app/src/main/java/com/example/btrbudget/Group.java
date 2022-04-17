package com.example.btrbudget;

import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Group extends AppCompatActivity {

    // define member variables
    private int groupID;
    private int ownerID;
    private LinearLayout groupScreen;
    public ArrayList<Integer> memberList = new ArrayList<Integer>();
    public ArrayList<Expense> expenseList = new ArrayList<Expense>();

    public Group(int owner) {
        this.ownerID = owner;
    }

    public void setOwner(int newID) {
        ownerID = newID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void addExpense(Expense newExp) {
        expenseList.add(newExp);
    }

    public void getGroupID()
    {

    }
}
