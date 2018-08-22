package com.example.nameer.medtrack;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    FloatingActionButton add;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private String startDate, endDate, medName, condition, notes;

    private Spinner conditionsSpinner, orderSpinner;
    private CheckBox ongoing;
    private String viewCodeCondition, viewCodeOrder;
    private boolean viewCodeOngoing;

    ArrayList<String> conditionsArrayList;
    private String conditionSelect;
    private MedViewModel mMedViewModel;
    public static final int NEW_MEDITEM_REQUEST_CODE = 1;
    public static final int EDIT_MEDITEM_REQUEST_CODE = 2;

    CardView filterCard, spinnersCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewCodeCondition = "view all"; //show all conditions on activity start
        viewCodeOrder = "date added"; //sort by date added on activity start

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MedAdaptar adapter = new MedAdaptar(this);
        recyclerView.setAdapter(adapter);

        mMedViewModel = ViewModelProviders.of(this).get(MedViewModel.class);

        setupConditionsSpinner();
        conditionsSpinner.setOnItemSelectedListener(this);

        setupOrderSpinner();
        orderSpinner.setOnItemSelectedListener(this);

        observer(); //shows view based on viewCode (determined by conditionsSpinner) and sets up observer

        filterCard(); //Hide and show filter options

        add = (FloatingActionButton)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MedInput.class);
                startActivityForResult(i, NEW_MEDITEM_REQUEST_CODE);
            }
        });

        setupNavDrawer();

        ongoing = findViewById(R.id.showOngoing);
        ongoing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                   viewCodeOngoing = true;
                    observer();
                }else if(ongoing.isChecked()==false){
                    viewCodeOngoing = false;
                    observer();
                }
            }
        });

        SharedPreferences sp = getSharedPreferences("test",Context.MODE_PRIVATE);
        if (!sp.getBoolean("first", false)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first", true);
            editor.apply();
            Intent intent = new Intent(this, Intro.class); // Call the AppIntro java class
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    @Override

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

        } else if(requestCode == EDIT_MEDITEM_REQUEST_CODE && resultCode == 2){
            String EmedName = i.getStringExtra("EmedName");
            String EstartDate = i.getStringExtra("Estart");
            String EendDate = i.getStringExtra("Eend");
            String Econdition = i.getStringExtra("Econdition");
            String Enotes = i.getStringExtra("Enotes");

            int ID1 = i.getIntExtra("id",0);
            if(ID1 != 0){
                mMedViewModel.update(EmedName, EstartDate, EendDate, Econdition, Enotes, ID1);
            }

        }else if(requestCode == EDIT_MEDITEM_REQUEST_CODE && resultCode ==3){
            int ID = i.getIntExtra("id",0);
            if(ID != 0){
                mMedViewModel.delete(ID);
            }
        } else {
            Toast.makeText(getApplicationContext(),"Empty not saved", Toast.LENGTH_LONG).show();
        }
    }

    public void setupConditionsSpinner(){
        conditionsSpinner = findViewById(R.id.conditionsSpinner);
        conditionsArrayList = new ArrayList<String>();
        final ArrayAdapter<String> conditionsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,conditionsArrayList);

        mMedViewModel.getConditions().observe(this, new Observer<List<String>>(){
            @Override
            public void onChanged (@Nullable final List<String> conditionsList){
                conditionsArrayList.clear();
                conditionsArrayList.add("View All");
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
    }

    public void setupOrderSpinner(){
        orderSpinner = findViewById(R.id.orderSpinner);
        ArrayList<String> orderArrayList = new ArrayList<>();
        final ArrayAdapter<String> orderAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,orderArrayList);
        orderArrayList.add("Date Added");
        orderArrayList.add("Start Date");
        orderArrayList.add("Med Name");
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(orderAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       Spinner spinner = (Spinner) parent;
        switch(spinner.getId()){
            case(R.id.conditionsSpinner):
                String selectedCondition = parent.getItemAtPosition(position).toString();
                if(selectedCondition == "View All"){
                    viewCodeCondition = "view all";
                    observer();
                }else{
                    conditionSelect = selectedCondition;
                    viewCodeCondition = "condition";
                    observer();
                }
                break;

            case(R.id.orderSpinner):
                String selectedOrder = parent.getItemAtPosition(position).toString();
                switch(selectedOrder){
                    case("Date Added"):
                        viewCodeOrder = "date added";
                        observer();
                        break;
                    case("Start Date"):
                        viewCodeOrder = "start date";
                        observer();
                        break;
                    case("Med Name"):
                        viewCodeOrder = "med name";
                        observer();
                        break;
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void filterCard(){
        filterCard = findViewById(R.id.filterCard);
        spinnersCard = findViewById(R.id.spinnersCard);
        spinnersCard.setVisibility(View.GONE);
        filterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnersCard.isShown()) {
                    spinnersCard.setVisibility(View.GONE);
                } else {
                    spinnersCard.setVisibility(View.VISIBLE);
                    //Fx.slide_down(MainActivity.this, spinnersCard);
                }
            }
        });
    }

    private void setupNavDrawer(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        if(item.getItemId()==R.id.nav_calendar){
            Intent i = new Intent(MainActivity.this, Calendar.class);

            startActivity(i);
            //overridePendingTransition(0,0);
        }

        //DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        //drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void observer(){
        final MedAdaptar adapter = new MedAdaptar(this);
        recyclerView.setAdapter(adapter);

        if (viewCodeCondition == "view all" && viewCodeOrder == "start date" && viewCodeOngoing == false){
            mMedViewModel.getMedsByStartDate().observe(this, new Observer<List<MedItem>>(){
                @Override
                public void onChanged (@Nullable final List<MedItem> medList){
                    adapter.setMedList(medList); //Updates the "mMedList" variable in adapter
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }else if (viewCodeCondition == "view all" && viewCodeOrder == "date added" && viewCodeOngoing == false) {
            mMedViewModel.getMedsByDateAdded().observe(this, new Observer<List<MedItem>>() {
                @Override
                public void onChanged(@Nullable final List<MedItem> medList) {
                    adapter.setMedList(medList);
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }else if (viewCodeCondition == "view all" && viewCodeOrder == "med name"&& viewCodeOngoing == false) {
            mMedViewModel.getMedsByAlphabetical().observe(this, new Observer<List<MedItem>>() {
                @Override
                public void onChanged(@Nullable final List<MedItem> medList) {
                    adapter.setMedList(medList);
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }else if (viewCodeCondition == "condition" && viewCodeOrder == "date added" && viewCodeOngoing == false) {
            mMedViewModel.getMedsByConditionDateAdded(conditionSelect).observe(this, new Observer<List<MedItem>>() {
                @Override
                public void onChanged(@Nullable final List<MedItem> medList) {
                    adapter.setMedList(medList);
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }else if (viewCodeCondition == "condition" && viewCodeOrder == "start date" && viewCodeOngoing == false) {
            mMedViewModel.getMedsByConditionStartDate(conditionSelect).observe(this, new Observer<List<MedItem>>() {
                @Override
                public void onChanged(@Nullable final List<MedItem> medList) {
                    adapter.setMedList(medList);
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }else if (viewCodeCondition == "condition" && viewCodeOrder == "med name" && viewCodeOngoing == false) {
            mMedViewModel.getMedsByConditionAlphabetical(conditionSelect).observe(this, new Observer<List<MedItem>>() {
                @Override
                public void onChanged(@Nullable final List<MedItem> medList) {
                    adapter.setMedList(medList);
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }else if (viewCodeCondition == "view all" && viewCodeOrder == "start date" && viewCodeOngoing == true) {
            mMedViewModel.getMedsByOngoingStart("Ongoing").observe(this, new Observer<List<MedItem>>() {
                @Override
                public void onChanged(@Nullable final List<MedItem> medList) {
                    adapter.setMedList(medList);
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }else if (viewCodeCondition == "view all" && viewCodeOrder == "date added" && viewCodeOngoing == true) {
            mMedViewModel.getMedsByOngoingDA("Ongoing").observe(this, new Observer<List<MedItem>>() {
                @Override
                public void onChanged(@Nullable final List<MedItem> medList) {
                    adapter.setMedList(medList);
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }else if (viewCodeCondition == "view all" && viewCodeOrder == "med name" && viewCodeOngoing == true) {
            mMedViewModel.getMedsByOngoingAlphabetical("Ongoing").observe(this, new Observer<List<MedItem>>() {
                @Override
                public void onChanged(@Nullable final List<MedItem> medList) {
                    adapter.setMedList(medList);
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }else if (viewCodeCondition == "condition" && viewCodeOrder == "start date" && viewCodeOngoing == true) {
            mMedViewModel.getMedsByConditionOngoingStart(conditionSelect, "Ongoing").observe(this, new Observer<List<MedItem>>() {
                @Override
                public void onChanged(@Nullable final List<MedItem> medList) {
                    adapter.setMedList(medList);
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }else if (viewCodeCondition == "condition" && viewCodeOrder == "date added" && viewCodeOngoing == true) {
            mMedViewModel.getMedsByConditionOngoingDA(conditionSelect, "Ongoing").observe(this, new Observer<List<MedItem>>() {
                @Override
                public void onChanged(@Nullable final List<MedItem> medList) {
                    adapter.setMedList(medList);
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }else if (viewCodeCondition == "condition" && viewCodeOrder == "med name" && viewCodeOngoing == true) {
            mMedViewModel.getMedsByConditionOngoingAlphabetical(conditionSelect, "Ongoing").observe(this, new Observer<List<MedItem>>() {
                @Override
                public void onChanged(@Nullable final List<MedItem> medList) {
                    adapter.setMedList(medList);
                }
            });
            Fx.slide_down(MainActivity.this, recyclerView);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
