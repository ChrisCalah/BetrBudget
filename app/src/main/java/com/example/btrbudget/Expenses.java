package com.example.btrbudget;

import android.content.Context;
import android.widget.ListView;
import android.widget.EditText;

import java.util.ArrayList;

public class Expenses extends ListView {

    ArrayList<Integer> expenses;

    public Expenses(Context context) {
        super(context);
    }

    public void addExpense()
    {
        EditText text = (EditText)findViewById(R.id.editText);
        Integer value = Integer.parseInt(text.getText().toString());
    }
}
