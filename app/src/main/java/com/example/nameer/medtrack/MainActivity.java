package com.example.nameer.medtrack;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    private MedViewModel mMedViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    ArrayList<MedItem> medList; //list of instances of the MedItem method (i.e list of meds & their accompanying info)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       //medList = new ArrayList<MedItem>();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MedAdaptar adapter = new MedAdaptar(this);
        recyclerView.setAdapter(adapter);

        mMedViewModel = ViewModelProviders.of(this).get(MedViewModel.class);

        mMedViewModel.getMedList().observe(this, new Observer<List<MedItem>>(){
            @Override
            public void onChanged (@Nullable final List<MedItem> medList){
                //Update the cached copy of medList in the adapter
                adapter.setMedList(medList);
            }
        });




        add = (FloatingActionButton)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MedInput.class);
                startActivityForResult(i, NEW_WORD_ACTIVITY_REQUEST_CODE);


            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent i){
        super.onActivityResult(requestCode, resultCode, i);

        if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            medName = i.getStringExtra("medName");

                startDate = i.getStringExtra("start");
                endDate = i.getStringExtra("end");
                condition = i.getStringExtra("condition");

            MedItem medItem = new MedItem(medName, startDate, endDate, condition, "dfdf");
            mMedViewModel.insert(medItem);
        } else {
            Toast.makeText(getApplicationContext(),"empty not saved", Toast.LENGTH_LONG).show();
        }
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
