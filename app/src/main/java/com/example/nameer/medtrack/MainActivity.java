package com.example.nameer.medtrack;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String calStartYear;
    FloatingActionButton add;
    /*private String startDate;
    private String endDate;
    private String medName;
    private String condition;*/
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




    ArrayList<MedItem> medList; //list of instances of the MedItem method (i.e list of meds & their accompanying info)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       medList = new ArrayList<MedItem>();
        /*if(startDate!=null){
            startDate = getIntent().getExtras().getString("start");        }
        if(endDate!=null){
            endDate = getIntent().getExtras().getString("end");        }
        if(medName!=null){
            medName = getIntent().getExtras().getString("medName");        }
        if(condition!=null){
            condition = getIntent().getExtras().getString("condition");        } */



        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medList.add(
                new MedItem("Probiotic 0.1%", "May 30", "June 8", "Psoriasis", "healed most flares")
        );

        medList.add(
                new MedItem("Probiotic 0.1%", "May 30", "June 8", "Psoriasis", "healed most flares test test test test test")
        );

        adapter = new MedAdaptar(this, medList);
        recyclerView.setAdapter(adapter);

        add = (FloatingActionButton)findViewById(R.id.add);
        /*add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(MainActivity.this, MedInput.class);
                //startActivity(i);
                setContentView(R.layout.activity_med_input);
                medInputTest();

            }
        });*/





       // if(startDate!=null) {
         //   medList.add(
         //           new MedItem(medName, startDate, endDate, condition, "healed most flares test test test test test")
         //   );


        //}


    }

    public void medInputTest(){
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

                DatePickerDialog dialogStart = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Material_Light_Dialog, mStartDateListener, calStartYear,calStartMonth,calStartDay);
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

                DatePickerDialog dialogEnd = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Material_Light_Dialog, mEndDateListener, calEndYear,calEndMonth,calEndDay);
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
        /*finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medName = selectMed.getEditableText().toString();
                condition = setCondition.getText().toString();
                medList.add(new MedItem(medName,calStartDate , calEndDate, condition, "healed most flares test test test test test"));
                setContentView(R.layout.activity_main);
                recyclerView.setAdapter(adapter);
            }
        });*/
    }

    public void add(View view){
        setContentView(R.layout.activity_med_input);
        medInputTest();
    }

    public void finish(View view){
        medName = selectMed.getEditableText().toString();
        condition = setCondition.getText().toString();
        medList.add(new MedItem(medName,calStartDate , calEndDate, condition, "healed most flares test test test test test"));
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
