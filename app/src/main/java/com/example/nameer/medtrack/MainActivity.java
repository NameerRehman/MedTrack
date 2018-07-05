package com.example.nameer.medtrack;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String calStartYear;
    FloatingActionButton add;
    private Spinner conditionsSpinner;
    private String startDate;
    private String endDate;
    private String medName;
    private String condition;
    private String notes;

    ArrayList<String> conditionsArrayList;
    private String conditions;
    private MedViewModel mMedViewModel;
    public static final int NEW_MEDITEM_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MedAdaptar adapter = new MedAdaptar(this);
        recyclerView.setAdapter(adapter);

        mMedViewModel = ViewModelProviders.of(this).get(MedViewModel.class);

        //Observer that observes mAllMeds LiveData from the database and executes onChanged when it changes
        mMedViewModel.getAllMeds().observe(this, new Observer<List<MedItem>>(){
            @Override
            public void onChanged (@Nullable final List<MedItem> medList){
                adapter.setMedList(medList); //Updates the "mMedList" variable in adapter
            }
        });

        conditionsSpinner = (Spinner) findViewById(R.id.conditionsSpinner);
        conditionsArrayList = new ArrayList<String>();
        conditions = "";
        final ArrayAdapter<String> conditionsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,conditionsArrayList);
        mMedViewModel.getConditions().observe(this, new Observer<List<String>>(){
            @Override
            public void onChanged (@Nullable final List<String> conditionsList){
                conditionsArrayList.clear();
                if (conditionsList != null){
                    for (int i = 0; i < conditionsList.size(); i++) {
                        if (conditionsArrayList.contains(conditionsList.get(i))){

                        }else{
                            conditionsArrayList.add(conditionsList.get(i));
                        }
                    }
                }

                conditionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                conditionsSpinner.setAdapter(conditionsAdapter);

            }
        });


        add = (FloatingActionButton)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MedInput.class);
                startActivityForResult(i, NEW_MEDITEM_REQUEST_CODE);

            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent i){
        super.onActivityResult(requestCode, resultCode, i);

        if(requestCode == NEW_MEDITEM_REQUEST_CODE && resultCode == RESULT_OK) {
            medName = i.getStringExtra("medName");
            startDate = i.getStringExtra("start");
            endDate = i.getStringExtra("end");
            condition = i.getStringExtra("condition");
            notes = i.getStringExtra("notes");


            MedItem medItem = new MedItem(medName, startDate, endDate, condition, notes);
            mMedViewModel.insert(medItem);

        } else if(requestCode == 2 && resultCode == 2){
            String EmedName = i.getStringExtra("EmedName");
            String EstartDate = i.getStringExtra("Estart");
            String EendDate = i.getStringExtra("Eend");
            String Econdition = i.getStringExtra("Econdition");
            String Enotes = i.getStringExtra("Enotes");

            int ID1 = i.getIntExtra("id",0);
            if(ID1 != 0){
                mMedViewModel.update(EmedName, EstartDate, EendDate, Econdition, Enotes, ID1);
            }

        }else if(requestCode == 2 && resultCode ==3){
            int ID = i.getIntExtra("id",0);
            if(ID != 0){
                mMedViewModel.delete(ID);
            }
        } else {
            Toast.makeText(getApplicationContext(),"Empty not saved", Toast.LENGTH_LONG).show();
        }
    }
}
