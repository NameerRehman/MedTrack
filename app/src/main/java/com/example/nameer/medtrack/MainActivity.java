package com.example.nameer.medtrack;

import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String calStartYear;
    FloatingActionButton add;
    private String startDate;
    private String endDate;
    private String medName;
    private String condition;

    ArrayList<MedItem> medList; //list of instances of the MedItem method (i.e list of meds & their accompanying info)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       //medList = new ArrayList<MedItem>();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries() //bad practice - should wrap in background thread (sync task?)
                .build();

        List<MedItem> medList = db.medDao().getAllMedItems();

        adapter = new MedAdaptar(this, medList);
        recyclerView.setAdapter(adapter);


        add = (FloatingActionButton)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MedInput.class);
                startActivity(i);
            }
        });


    }


    /*public void add(View view){
        setContentView(R.layout.activity_med_input);
        medInputTest();
    }

    public void finish(View view){
        medName = selectMed.getEditableText().toString();
        condition = setCondition.getText().toString();
        if (medName.length() >0){
            medList.add(new MedItem(medName,calStartDate , calEndDate, condition, "healed most flares test test test test test"));
            setContentView(R.layout.activity_main);
            recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Please enter medication name", Toast.LENGTH_SHORT).show();
        }

    }*/
}


if(startDate!=null){
            startDate = getIntent().getExtras().getString("start");        }
        if(endDate!=null){
            endDate = getIntent().getExtras().getString("end");        }
        if(medName!=null){
            medName = getIntent().getExtras().getString("medName");        }
        if(condition!=null)
            condition = getIntent().getExtras().getString("condition");        }