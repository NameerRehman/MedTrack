package com.example.nameer.medtrack;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MedInput extends AppCompatActivity {

    private TextView selectStart;
    private TextView selectEnd;
    private EditText selectMed;
    private EditText setCondition;
    private DatePickerDialog.OnDateSetListener mStartDateListener;
    private DatePickerDialog.OnDateSetListener mEndDateListener;
    private String calStartDate;
    private String calEndDate;
    private String medName;
    private String condition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_input);

        //final ArrayList<MedItem> medList = new ArrayList<>();

        selectStart = (TextView) findViewById(R.id.selectStart);
        selectEnd = (TextView) findViewById(R.id.selectEnd);
        selectMed = (EditText) findViewById(R.id.selectMed);
        setCondition = (EditText) findViewById(R.id.setCondition);

        selectStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Calendar calStart = Calendar.getInstance();
                int calStartYear = calStart.get(Calendar.YEAR);
                int calStartMonth = calStart.get(Calendar.MONTH);
                int calStartDay = calStart.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogStart = new DatePickerDialog(MedInput.this, android.R.style.Theme_Material_Light_Dialog, mStartDateListener, calStartYear,calStartMonth,calStartDay);
                dialogStart.show();
            }
        });

        mStartDateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int calStartYear, int calStartMonth, int calStartDay){
                calStartMonth = calStartMonth + 1;
                calStartDate = calStartMonth + "/" + calStartDay + "/" +calStartYear;
                selectStart.setText(calStartDate);
            }
        };



        selectEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Calendar calEnd = Calendar.getInstance();
                int calEndYear = calEnd.get(Calendar.YEAR);
                int calEndMonth = calEnd.get(Calendar.MONTH);
                int calEndDay = calEnd.get(Calendar.DAY_OF_MONTH);

               DatePickerDialog dialogEnd = new DatePickerDialog(MedInput.this, android.R.style.Theme_Material_Light_Dialog, mEndDateListener, calEndYear,calEndMonth,calEndDay);
               dialogEnd.show();
            }
        });

        mEndDateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int calEndYear, int calEndMonth, int calEndDay){
                calEndMonth = calEndMonth + 1;
                calEndDate = calEndMonth + "/" + calEndDay + "/" + calEndYear;
                selectEnd.setText(calEndDate);
            }
        };



        FloatingActionButton finish = (FloatingActionButton)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(MedInput.this, MainActivity.class);
                medName = selectMed.getEditableText().toString();
                condition = setCondition.getText().toString();
                test.putExtra("medName", medName);
                test.putExtra("start", calStartDate);
                test.putExtra("end", calEndDate);
                test.putExtra("condition", condition);
                startActivity(test);
                //medListUpdated.add(new MedItem("test 0.1%", , "June 8", "Psoriasis", "healed most flares test test test test test"));
                //send updated medlist to main activity
                //test.putExtra("medListUpdated", medListUpdated)
            }
        });


       /*medListHelper Obj = new medListHelper(medName, calStartDate, calEndDate, condition);
        Obj.setCalStartDate(calStartDate);
        Obj.setCalEndDate(calEndDate);
        Obj.setMedName(medName);
        Obj.setMedList();*/




    }
}
