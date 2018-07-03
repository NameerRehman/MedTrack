package com.example.nameer.medtrack;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

        EselectStart = (TextView) findViewById(R.id.EselectStart);
        EselectEnd = (TextView) findViewById(R.id.EselectEnd);
        EselectMed = (EditText) findViewById(R.id.EselectMed);
        EsetCondition = (EditText) findViewById(R.id.EsetCondition);
        EsetNotes = (EditText) findViewById(R.id.EsetNotes);

        //get MedItem object that was clicked in RecyclerView adapter
        Bundle extras = getIntent().getExtras();
        if (extras!= null){
            currentPosition = (MedItem)extras.getSerializable("position");

            EselectMed.setText(currentPosition.getMedName());
            EsetCondition.setText(currentPosition.getCondition());
            EsetNotes.setText(currentPosition.getNotes());

            if(currentPosition.getStartDate()!=null){
                EselectStart.setText(currentPosition.getStartDate());
            }else{
                EselectStart.setText("N/A");
            }

            if(currentPosition.getEndDate() != null){
                EselectEnd.setText(currentPosition.getEndDate());
            }else{
                EselectEnd.setText("N/A");
            }
        }

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
                    i.putExtra("Estart", calStartDate);
                    i.putExtra("Eend", calEndDate);
                    i.putExtra("Econdition", condition);
                    i.putExtra("Enotes", notes);
                    i.putExtra("id", currentPosition.getId());
                    setResult(2, i);
                    finish();

                }

            }
        });

        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MedEdit.this, MainActivity.class);
                i.putExtra("id", currentPosition.getId());
                setResult(3,i);
                finish();

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

                //date listener in onclick method, so will only listen for date when clicked
                mStartDateListener = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int calStartYear, int calStartMonth, int calStartDay){
                        calStartMonth = calStartMonth + 1;
                        calStartDate = calStartMonth + "/" + calStartDay + "/" +calStartYear;
                        EselectStart.setText(calStartDate);
                    }
                };
            }
        });


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

                mEndDateListener = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int calEndYear, int calEndMonth, int calEndDay){
                        calEndMonth = calEndMonth + 1;
                        calEndDate = calEndMonth + "/" + calEndDay + "/" + calEndYear;
                        EselectEnd.setText(calEndDate);
                    }
                };
            }
        });



    }

}




  /*final AppDatabase db = AppDatabase.getDatabase(this);
            mAllMeds = db.medDao().getMedListByDate();

            mAllMeds.observe(this, new Observer<List<MedItem>>(){
                @Override
                public void onChanged(@Nullable List<MedItem> mAllMeds1) {
                    if (mAllMeds == null) {
                        test = "empty";
                    } else {
                        //test = mAllMeds1.get(position).getMedName();
                        test = "not empty";
                    }
                }
            });

            mAllMeds.observe(this,);*/