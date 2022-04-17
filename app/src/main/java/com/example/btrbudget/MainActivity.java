/*
All Code pertaining to MPAndroidChart is subject to the following:

Copyright [2020] [Philipp Jahoda]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */

package com.example.btrbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    //create PieChart instance/object
    private Group thisGroup = new Group(1234);;
    private PieChart pieChart;
    private UserSettings settings = new UserSettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load default class values
        pieChart = findViewById(R.id.activity_main_piechart);

        setupPieChart();
        loadPieChartData();
    }

    protected void onClose()
    {

    }

    public void homeNavigate(View view)
    {
        this.findViewById(android.R.id.content).getRootView();

        setContentView(R.layout.activity_main);

        //Set default class values for pie chart
        pieChart = findViewById(R.id.activity_main_piechart);

        //update data values
        setupPieChart();
        loadPieChartData();
    }

    //Set up the piechart ui
    private void setupPieChart()
    {
        //ui elements of chart
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("March Expenses");
        pieChart.setCenterTextSize(18);
        pieChart.getDescription().setEnabled(false);

        //set legend position
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);
    }

    //Load the data pertaining to the pieChart
    private void loadPieChartData()
    {
        //create arraylist for data entries
        ArrayList<PieEntry> entries = new ArrayList<>();

        //add entries
        entries.add(new PieEntry(10f, "Netflix"));
        entries.add(new PieEntry(8f, "Hulu"));
        entries.add(new PieEntry(26f, "Xbox"));
        entries.add(new PieEntry(87.5f, "Food"));
        entries.add(new PieEntry(300.3f, "Rent"));

        //Create arraylist for colors to be used by chart
        ArrayList<Integer> colors = new ArrayList<>();

        //populate list
        for(int color: ColorTemplate.MATERIAL_COLORS)
        {
            colors.add(color);
        }

        for(int color: ColorTemplate.VORDIPLOM_COLORS)
        {
            colors.add(color);
        }

        //Instantiate data set object -> set colors of data set
        PieDataSet dataSet = new PieDataSet(entries, "Monthly Expenses");
        dataSet.setColors(colors);

        //Instantiate data object -> populate attributes of chart
        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        //pass data to chart
        pieChart.setData(data);

        //refresh piechart for screen
        pieChart.invalidate();

        pieChart.animateY(1300, Easing.EaseInOutQuad);
    }

    // accesses settings data and sets the settings accordingly.
    public UserSettings get_json() throws IOException, JSONException {

        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "settings.json");
        byte[] content = new byte[(int) readFrom.length()];

        try {
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);

            JSONObject obj = new JSONObject(new String(content));
            return new UserSettings(obj.getString("name"), obj.getInt("moneySaved"),
                    obj.getInt("expenseReportPeriod"));

        }catch (Exception e){
            e.printStackTrace();
        }
        return new UserSettings();
    }

    // updates the settings json file to appropriate values
    public void updateSettings(UserSettings usrSettings) throws JSONException {
        // create a JSONObject
        JSONObject obj = new JSONObject();

        // put relevant values into json object
        obj.put("name", usrSettings.name);
        obj.put("moneySaved", usrSettings.moneySaved);
        obj.put("notificationSetting", usrSettings.notificationSetting);

        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "settings.json"));
            writer.write(obj.toString().getBytes());
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // runs when screen is navigated to settings
    public void settingsNavigate(View view) throws JSONException, IOException {
        // set the screen to the settings window
        setContentView(R.layout.options_screen);

        // define variables
        RadioGroup radioGrp = findViewById(R.id.settingsRadioButtons);
        Button saveBtn = findViewById(R.id.saveButton);
        saveBtn.setBackgroundColor(Color.GREEN);

        // check the appropriate button based on the id from the saved data
        radioGrp.check(settings.notificationSetting);

        // run when checked button changes
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                // define variables

                // update settings to the checkedID
                settings.notificationSetting = checkedID;
                // set save button to color red
                saveBtn.setBackgroundColor(Color.RED);
            }
        });

        // run when save button is pressed
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // write new data into json file
                try {
                    updateSettings(settings);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // change save button to green
                saveBtn.setBackgroundColor(Color.GREEN);
            }
        });
    }


    public void groupNavigate(View view)
    {
        setContentView(R.layout.group_screen);
    }
    public void expenseNavigate(View view)
    {
        setContentView(R.layout.expense_page);
    }
    public void reportNavigate(View view)
    {
        setContentView(R.layout.report_screen);
    }

    // runs when navigated to group screen
    public void inGroupNavigate(View view){

        int index;

        // set the content view
        setContentView(R.layout.in_group_screen);

        for(index = 0; index < thisGroup.expenseList.size(); index++)
        {
            if (index % 2 == 0)
            {
                createExpenseXMLElement(thisGroup.expenseList.get(index), Color.GRAY);
            }
            else
            {
                createExpenseXMLElement(thisGroup.expenseList.get(index), Color.LTGRAY);
            }
        }
    }

    public void createExpenseXMLElement(Expense expense, int color)
    {
        TextView text = new TextView(this);
        LinearLayout groupScreen = (LinearLayout) findViewById(R.id.groupExpenses);
        text.setText(expense.name + " - $" + expense.amount + " - " + expense.date);
        text.setBackgroundColor(color);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setTextSize(18);
        text.setHeight(200);
        groupScreen.addView(text);
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.expense_popup, (ViewGroup) findViewById(R.id.popup_layout));

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        Button confirm = (Button)popupView.findViewById(R.id.confirmExpense);

        // create an expense when confirm button pressed
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editName = (EditText)popupView.findViewById(R.id.editName);
                EditText editAmt = (EditText)popupView.findViewById(R.id.editAmount);
                EditText editDate = (EditText)popupView.findViewById(R.id.editDate);
                Expense newExp = new Expense(Double.parseDouble(editAmt.getText().toString()),
                        editDate.getText().toString(), editName.getText().toString());
                thisGroup.addExpense(newExp);

                if (thisGroup.expenseList.size() % 2 == 0)
                {
                    createExpenseXMLElement(newExp, Color.GRAY);
                }
                else
                {
                    createExpenseXMLElement(newExp, Color.LTGRAY);
                }
                popupWindow.dismiss();
            }
        });
    }
}