package com.example.nameer.medtrack;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.arch.lifecycle.LiveData;


import java.util.Calendar;
import java.util.List;

public class MedEdit extends AppCompatActivity {

    private TextView EselectStart;
    private TextView EselectEnd;
    private EditText EselectMed;
    private EditText EsetCondition;
    private EditText EsetNotes;
    private CheckBox Eongoing;
    private Button delete;

    private MedItem currentPosition;

    private DatePickerDialog.OnDateSetListener mStartDateListener;
    private DatePickerDialog.OnDateSetListener mEndDateListener;
    private String calStartDate;
    private String calEndDate;
    private String medName;
    private String condition;
    private String notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_edit);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        EselectStart = findViewById(R.id.EselectStart);
        EselectEnd = findViewById(R.id.EselectEnd);
        EselectMed = findViewById(R.id.EselectMed);
        EsetCondition = findViewById(R.id.EsetCondition);
        EsetNotes = findViewById(R.id.EsetNotes);
        Eongoing = findViewById(R.id.Eongoing);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //get MedItem object that was clicked in RecyclerView adapter
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentPosition = (MedItem) extras.getSerializable("position");

            EselectMed.setText(currentPosition.getMedName());
            EsetCondition.setText(currentPosition.getCondition());
            EsetNotes.setText(currentPosition.getNotes());

            if (currentPosition.getStartDate() != null) {
                if(!currentPosition.getStartDate().equals("")) {
                    EselectStart.setText(currentPosition.getStartDate());
                }else {
                    EselectStart.setText("Select Start Date");
                }
            } else {
                EselectStart.setText("Select Start Date");
            }

        if (currentPosition.getEndDate() != null) {
            if(!currentPosition.getEndDate().equals("")) {
                EselectEnd.setText(currentPosition.getEndDate());
                if (currentPosition.getEndDate().equals("Ongoing")) {
                    Eongoing.setChecked(true);
                    EselectEnd.setTextColor(Color.parseColor("#D3D3D3"));
                } else {
                    Eongoing.setChecked(false);
                }
            }else {
                EselectEnd.setText("Select End Date");
            }
        }else {
            EselectEnd.setText("Select End Date");
            }
        }

        Eongoing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    EselectEnd.setText("Ongoing");
                    calEndDate = "Ongoing";
                    EselectEnd.setClickable(false);
                    EselectEnd.setTextColor(Color.parseColor("#D3D3D3"));
                }else if(Eongoing.isChecked()==false){
                    EselectEnd.setClickable(true);
                    EselectEnd.setTextColor(Color.parseColor("#000000"));

                }
            }
        });

        editEndDate(); //date picker dialog to edit start/end dates
        editStartDate();


        FloatingActionButton Efinish = (FloatingActionButton)findViewById(R.id.Efinish);
        Efinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MedEdit.this, MainActivity.class);

                if (TextUtils.isEmpty(EselectMed.getEditableText())) {
                    Toast.makeText(MedEdit.this, "Please enter medication name", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED, i);

                } else {
                    medName = EselectMed.getEditableText().toString();
                    condition = EsetCondition.getText().toString();
                    notes = EsetNotes.getText().toString();
                    calStartDate = EselectStart.getText().toString();
                    calEndDate = EselectEnd.getText().toString();

                    i.putExtra("EmedName", medName);
                    //i.putExtra("Estart", calStartDate);
                    //i.putExtra("Eend", calEndDate);
                    i.putExtra("Econdition", condition);
                    i.putExtra("Enotes", notes);
                    i.putExtra("id", currentPosition.getId());

                    if(calStartDate.equals("Select Start Date")){
                        i.putExtra("Estart", "");
                    }else{
                        i.putExtra("Estart", calStartDate);
                    }

                    if(calEndDate.equals("Select End Date")){
                        i.putExtra("Eend", "");
                    }else{
                        i.putExtra("Eend", calEndDate);
                    }
                    setResult(2, i);
                    finish();

                }

            }
        });

        delete = (Button) findViewById(R.id.delete);
        delete.bringToFront();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MedEdit.this);
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MedEdit.this, MainActivity.class);
                        i.putExtra("id", currentPosition.getId());
                        setResult(3,i);
                        finish();
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();

            }
        });
    }

    public void editStartDate(){
        EselectStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Calendar calStart = Calendar.getInstance();
                int calStartYear = calStart.get(Calendar.YEAR);
                int calStartMonth = calStart.get(Calendar.MONTH);
                int calStartDay = calStart.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogStart = new DatePickerDialog(MedEdit.this, android.R.style.Theme_Material_Light_Dialog, mStartDateListener, calStartYear,calStartMonth,calStartDay);
                dialogStart.show();
                Toast.makeText(getApplicationContext(), calStartDate, Toast.LENGTH_SHORT).show();
            }
        });

        mStartDateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int calStartYear, int calStartMonth, int calStartDay){
                calStartMonth = calStartMonth + 1;
                calStartDate = calStartMonth + "/" + calStartDay + "/" +calStartYear;
                EselectStart.setText(calStartDate);

            }
        };
    }

    public void editEndDate(){
        EselectEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Calendar calEnd = Calendar.getInstance();
                int calEndYear = calEnd.get(Calendar.YEAR);
                int calEndMonth = calEnd.get(Calendar.MONTH);
                int calEndDay = calEnd.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogEnd = new DatePickerDialog(MedEdit.this, android.R.style.Theme_Material_Light_Dialog, mEndDateListener, calEndYear,calEndMonth,calEndDay);
                dialogEnd.show();
            }
        });

        mEndDateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int calEndYear, int calEndMonth, int calEndDay){
                calEndMonth = calEndMonth + 1;
                calEndDate = calEndMonth + "/" + calEndDay + "/" + calEndYear;
                EselectEnd.setText(calEndDate);
            }
        };

    }

}