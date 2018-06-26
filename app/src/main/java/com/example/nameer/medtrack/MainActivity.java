package com.example.nameer.medtrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;
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
//test


    ArrayList<MedItem> medList; //list of instances of the MedItem method (i.e list of meds & their accompanying info)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medListHelper Obj2 = new medListHelper(medList);

        medList = new ArrayList<MedItem>();
        if(startDate!=null){
            startDate = getIntent().getExtras().getString("start");        }
        if(endDate!=null){
            endDate = getIntent().getExtras().getString("end");        }
        if(medName!=null){
            medName = getIntent().getExtras().getString("medName");        }
        if(condition!=null){
            condition = getIntent().getExtras().getString("condition");        }



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
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(MainActivity.this, MedInput.class);
                //startActivity(i);
                setContentView(R.layout.activity_med_input);

            }
        });





       // if(startDate!=null) {
         //   medList.add(
         //           new MedItem(medName, startDate, endDate, condition, "healed most flares test test test test test")
         //   );


        //}




    }

    public void myMethod(){
        medList.add(new MedItem(medName, startDate, endDate, condition, "disjdisjd"));

    }
}
