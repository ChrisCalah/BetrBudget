package com.example.btrbudget;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.sax.Element;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Group extends AppCompatActivity {

    // define member variables
    private int ownerID;
    private LinearLayout groupScreen;
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

    public void addExpense(double amount, String date, String name, String desc) {
        expenseList.add(new Expense(amount, date, name, desc));
    }
}
