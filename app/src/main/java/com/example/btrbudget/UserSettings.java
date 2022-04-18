package com.example.btrbudget;

import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

public class UserSettings extends AppCompatActivity {
    // define member variables
    public String name;
    public int moneySaved;
    public int notificationSetting;
    public ArrayList<Expense> expenseList = new ArrayList<Expense>();

    public UserSettings(String usrName, int money, int notif)
    {
        name = usrName;
        moneySaved = money;
        notificationSetting = notif;
    }


    public UserSettings()
    {
        name = "user";
        moneySaved = 0;
        notificationSetting = -1;
    }

    public void addExpense(Expense newExp) {

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                expenseList.add(newExp);
                String[] field = new String[4];
                field[0] = "username";
                field[1] = "name";
                field[2] = "date";
                field[3] = "amount";
                String[] data = new String[4];
                data[0] = name;
                data[1] = newExp.name;
                data[2] = newExp.date;
                data[3] = Double.toString(newExp.amount);
                PutData putData = new PutData("http://54.215.66.149/BetrBudget/personalExpenses.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if(result.equals("Amount successfully added.")){
                            System.out.println(result);
                        }
                        else {
                            System.out.println(result);
                        }
                    }
                }
            }
        });
    }

    public void getExpenses()
    {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "username";
                String[] data = new String[1];
                data[0] = name;
                PutData putData = new PutData("http://54.215.66.149/BetrBudget/getExpenses.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        System.out.println("Data:");
                        System.out.println(result);
                    }
                }
                //End Write and Read data with URL
            }
        });
    }
}
