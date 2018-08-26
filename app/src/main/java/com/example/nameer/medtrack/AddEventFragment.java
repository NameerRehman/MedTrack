package com.example.nameer.medtrack;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEventFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private View view;
    private CalendarView calendarView;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());

    private MedViewModel mMedViewModel;

    private FloatingActionButton finish;
    private CheckBox headache, diziness, acne, bodyaches, cramps, chills, itchyness, flare, bloating, constipation, diarrhea, gas, abdominalCramps, nausea, stress, moodiness, irritability, insomnia, fatigue, confusion;
    private CheckBox happy,angry,lonely,sad,worried,neutral,anxious,cranky,scared,loving,weird,cheerful;
    private Button deleteEvent;
    private Spinner glucoseSpinner, weightSpinner;
    private String weightUnit, glucoseUnit;
    private EditText weightEditText, glucoseEditText, spEditText, dpEditText, pulseEditText, notesEditText;
    ArrayList<String> symptomList, moodList;
    private String symptoms, moods, weight, glucose, bp, pulse, notes;
    long date;
    CardView symptomsCard, moodCard;
    LinearLayout symptomsView, moodView;
    private boolean existingEvent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_event, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();

        if(bundle != null){
            date = bundle.getLong("date");
        }

        //Set actionbar title to current Calendar date
        Date dateDisplay = new Date(date); //change Long type to Date type
        final String formattedDate = dateFormat.format(dateDisplay);
        getActivity().setTitle(formattedDate);

        checkedItems();

        deleteEvent = view.findViewById(R.id.deleteEvent);
        notesEditText = view.findViewById(R.id.notes);
        weightEditText = view.findViewById(R.id.weight);
        glucoseEditText = view.findViewById(R.id.glucose);
        dpEditText = view.findViewById(R.id.dpBlood);
        spEditText = view.findViewById(R.id.spBlood);
        pulseEditText = view.findViewById(R.id.pulse);

        //default units
        weightUnit = "lbs";
        glucoseUnit = "mg/dL";

        //setup spinners for weight and glucose units
        weightSpinner = view.findViewById(R.id.weightSpinner);
        final ArrayList<String> weightList = new ArrayList<>();
        weightList.add("lbs");
        weightList.add("kg");
        final ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,weightList);
        weightSpinner.setAdapter(weightAdapter);
        weightSpinner.setOnItemSelectedListener(this);

        glucoseSpinner = view.findViewById(R.id.glucoseSpinner);
        final ArrayList<String> glucoseList = new ArrayList<>();
        glucoseList.add("mg/dL");
        glucoseList.add("mmol/L");
        final ArrayAdapter<String> glucoseAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,glucoseList);
        glucoseSpinner.setAdapter(glucoseAdapter);
        glucoseSpinner.setOnItemSelectedListener(this);

        symptomList = new ArrayList<>();
        moodList = new ArrayList<>();

        //getEvents(date) query gets event from database based on "date"
        mMedViewModel = ViewModelProviders.of(this).get(MedViewModel.class);
        mMedViewModel.getEvents(date).observe(getActivity(), new Observer<CalendarEvent>() {
            @Override
            public void onChanged(@Nullable final CalendarEvent cal) {
                if (cal != null) {
                    //set existingEvent true so existing database entry is updated upon finish
                    existingEvent = true;

                    notesEditText.setText(cal.getCalNotes());
                    fillSymptomsData("Headache", headache, cal);
                    fillSymptomsData("Diziness", diziness, cal);
                    fillSymptomsData("Acne", acne, cal);
                    fillSymptomsData("Body aches", bodyaches, cal);
                    fillSymptomsData("Cramps", cramps, cal);
                    fillSymptomsData("Chills", chills, cal);
                    fillSymptomsData("Itchiness", itchyness, cal);
                    fillSymptomsData("Skin flare/rash", flare, cal);
                    fillSymptomsData("Bloating", bloating, cal);
                    fillSymptomsData("Constipation", constipation, cal);
                    fillSymptomsData("Diarrhea", diarrhea, cal);
                    fillSymptomsData("Gas", gas, cal);
                    fillSymptomsData("Abdominal cramps", abdominalCramps, cal);
                    fillSymptomsData("Nausea", nausea, cal);
                    fillSymptomsData("Stress", stress, cal);
                    fillSymptomsData("Moodiness", moodiness, cal);
                    fillSymptomsData("Irritability", irritability, cal);
                    fillSymptomsData("Insomnia", insomnia, cal);
                    fillSymptomsData("Fatigue", fatigue, cal);
                    fillSymptomsData("Confusion", confusion, cal);

                    fillMoodData(happy, "Happy", cal);
                    fillMoodData(angry, "Angry", cal);
                    fillMoodData(lonely, "Lonely", cal);
                    fillMoodData(sad, "Sad", cal);
                    fillMoodData(neutral, "Neutral", cal);
                    fillMoodData(worried, "Worried", cal);
                    fillMoodData(anxious, "Anxious", cal);
                    fillMoodData(cranky, "Cranky", cal);
                    fillMoodData(scared, "Scared", cal);
                    fillMoodData(loving, "Loving", cal);
                    fillMoodData(weird, "Weird", cal);
                    fillMoodData(cheerful, "Cheerful", cal);

                    //click listener for delete button - deletes queried date
                    delete(cal, formattedDate);

                } else {
                    //set existingEvent false so new database entry is created upon finish
                    existingEvent = false;
                }

    }

});

        //Cards to show & hide symptoms & moods lists
        symptomsCard();
        moodCard();

        finish();
    }

    @Override
    public void onClick(View v) {}

    //Show & hide symptoms and moods layouts
    public void symptomsCard(){
        symptomsCard = view.findViewById(R.id.symptomsCard);
        symptomsView = view.findViewById(R.id.symptomsView);
        symptomsView.setVisibility(View.GONE);
        symptomsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (symptomsView.isShown()) {
                    symptomsView.setVisibility(View.GONE);
                } else {
                    symptomsView.setVisibility(View.VISIBLE);
                    //Fx.slide_down(MainActivity.this, spinnersCard);
                }
            }
        });
    }

    public void moodCard(){
        moodCard = view.findViewById(R.id.moodCard);
        moodView = view.findViewById(R.id.moodView);
        moodView.setVisibility(View.GONE);
        moodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moodView.isShown()) {
                    moodView.setVisibility(View.GONE);
                } else {
                    moodView.setVisibility(View.VISIBLE);
                    //Fx.slide_down(MainActivity.this, spinnersCard);
                }
            }
        });
    }

    //Set logic for check/unchecked checkboxes
    public void isCheckedSymptoms(CheckBox symptomCheck, int id, final String symptomName){
        //symptomCheck = view.findViewById(id);
        symptomCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    symptomList.add(symptomName);
                    symptomsListToString();
                }if(isChecked==false){
                    symptomList.remove(symptomName);
                    symptomsListToString();
                }
            }
        });
    }

    public void isCheckedMood(CheckBox moodCheck, final String mood){
        moodCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    moodList.add(mood);
                    moodListToString();
                }if(isChecked==false){
                    moodList.remove(mood);
                    moodListToString();
                }
            }
        });
    }

    //Implement checkboxes and isChecked methods
    public void checkedItems(){
        headache = view.findViewById(R.id.headache);
        diziness = view.findViewById(R.id.diziness);
        acne = view.findViewById(R.id.acne);
        bodyaches = view.findViewById(R.id.bodyAches);
        cramps = view.findViewById(R.id.cramps);
        chills = view.findViewById(R.id.chills);
        itchyness = view.findViewById(R.id.itchyness);
        flare = view.findViewById(R.id.flare);
        bloating = view.findViewById(R.id.bloating);
        constipation = view.findViewById(R.id.constipation);
        diarrhea = view.findViewById(R.id.diarrhea);
        gas = view.findViewById(R.id.gas);
        abdominalCramps = view.findViewById(R.id.abdominalCramps);
        nausea = view.findViewById(R.id.nausea);
        stress = view.findViewById(R.id.stress);
        moodiness = view.findViewById(R.id.moodiness);
        irritability = view.findViewById(R.id.irritability);
        insomnia = view.findViewById(R.id.insomnia);
        fatigue = view.findViewById(R.id.fatigue);
        confusion = view.findViewById(R.id.confusion);

        isCheckedSymptoms(headache, R.id.headache, "Headache");
        isCheckedSymptoms(diziness, R.id.diziness, "Diziness");
        isCheckedSymptoms(acne, R.id.acne, "Acne");
        isCheckedSymptoms(bodyaches, R.id.bodyAches, "Body aches");
        isCheckedSymptoms(cramps, R.id.cramps, "Cramps");
        isCheckedSymptoms(chills, R.id.chills, "Chills");
        isCheckedSymptoms(itchyness, R.id.itchyness, "Itchiness");
        isCheckedSymptoms(flare, R.id.flare, "Skin flare/rash");
        isCheckedSymptoms(bloating, R.id.bloating, "Bloating");
        isCheckedSymptoms(constipation, R.id.constipation, "Constipation");
        isCheckedSymptoms(diarrhea, R.id.diarrhea, "Diarrhea");
        isCheckedSymptoms(gas, R.id.gas, "Gas");
        isCheckedSymptoms(abdominalCramps, R.id.cramps, "Abdominal cramps");
        isCheckedSymptoms(nausea, R.id.nausea, "Nausea");
        isCheckedSymptoms(stress, R.id.stress, "Stress");
        isCheckedSymptoms(moodiness, R.id.moodiness, "Moodiness");
        isCheckedSymptoms(irritability, R.id.irritability, "Irritability");
        isCheckedSymptoms(insomnia, R.id.insomnia, "Insomnia");
        isCheckedSymptoms(fatigue, R.id.fatigue, "Fatigue");
        isCheckedSymptoms(confusion, R.id.confusion, "Confusion");

        happy = view.findViewById(R.id.happy);
        angry = view.findViewById(R.id.angry);
        lonely = view.findViewById(R.id.lonely);
        sad = view.findViewById(R.id.sad);
        neutral = view.findViewById(R.id.neutral);
        worried = view.findViewById(R.id.worried);
        anxious = view.findViewById(R.id.anxious);
        cranky = view.findViewById(R.id.cranky);
        scared = view.findViewById(R.id.scared);
        loving = view.findViewById(R.id.loving);
        weird = view.findViewById(R.id.weird);
        cheerful = view.findViewById(R.id.cheerful);

        isCheckedMood(happy, "Happy");
        isCheckedMood(angry, "Angry");
        isCheckedMood(lonely, "Lonely");
        isCheckedMood(sad, "Sad");
        isCheckedMood(neutral, "Neutral");
        isCheckedMood(worried, "Worried");
        isCheckedMood(anxious, "Anxious");
        isCheckedMood(cranky, "Cranky");
        isCheckedMood(scared, "Scared");
        isCheckedMood(loving, "Loving");
        isCheckedMood(weird, "Weird");
        isCheckedMood(cheerful, "Cheerful");

    }

    //Convert lists of symptoms and moods to strings
    public String symptomsListToString(){
        symptoms="";
        for (int i = 0; i < symptomList.size() ; i++) {
            if(i == (symptomList.size() -1)){  //if on last item of the list, do not add a comma after
                symptoms = symptoms + symptomList.get(i);
            }else{
                symptoms = symptoms + symptomList.get(i) + ", ";
            }
        }
        return symptoms;
    }

    public String moodListToString(){
        moods="";
        for (int i = 0; i < moodList.size() ; i++) {
            if(i == (moodList.size() -1)){  //if on last item of the list, do not add a comma after
                moods = moods + moodList.get(i);
            }else{
                moods = moods + moodList.get(i) + ", ";
            }
        }
        return moods;
    }

    //populate fields when editing existing data
    public void fillSymptomsData(String symptom, CheckBox checkBox, CalendarEvent cal) {
        if (cal.getCalSymptoms() != null && cal.getCalSymptoms().contains(symptom)) {
            checkBox.setChecked(true);
        }
    }

    public void fillMoodData(CheckBox checkBox, String mood, CalendarEvent cal) {
        if (cal.getCalMood() != null && cal.getCalMood().contains(mood)) {
            checkBox.setChecked(true);
        }
    }

    public void finish(){
        finish = view.findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes = notesEditText.getEditableText().toString();
                weight = weightEditText.getEditableText().toString() + " " + weightUnit;
                glucose = glucoseEditText.getEditableText().toString() + " " + glucoseUnit;
                bp = spEditText.getEditableText().toString() + "/" + dpEditText.getEditableText().toString() + " sp/dp";
                pulse = pulseEditText.getEditableText().toString() + " " + "bpm";
                if(existingEvent == false) {
                    CalendarEvent calendarEvent = new CalendarEvent(date, symptoms, moods, notes, weight, glucose, bp, pulse);
                    mMedViewModel.insertCal(calendarEvent);

                    Fragment CalendarFragment = new CalendarFragment();
                    //addEventFragment.setArguments(bundle);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragment_container, CalendarFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    InputMethodManager inputManager = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    View currentFocusedView = getActivity().getCurrentFocus();
                    if (currentFocusedView != null) {
                        inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }else{
                    mMedViewModel.editCal(date, symptoms, moods, notes, weight, glucose, bp, pulse);
                    Fragment CalendarFragment = new CalendarFragment();
                    //addEventFragment.setArguments(bundle);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragment_container, CalendarFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    InputMethodManager inputManager = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);

                    // check if no view has focus:
                    View currentFocusedView = getActivity().getCurrentFocus();
                    if (currentFocusedView != null) {
                        inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    Toast.makeText(getContext(), "Calendar item updated", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void delete(final CalendarEvent cal,final String formattedDate ){
        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (symptomList.size() == 0 && moodList.size() == 0 && cal.getCalNotes() == null) {
                    Toast.makeText(getContext(), "There is nothing to delete", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Delete");
                    alert.setMessage("Are you sure you want to delete?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mMedViewModel.deleteCal(date);
                            Fragment CalendarFragment = new CalendarFragment();
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragment_container, CalendarFragment);
                            ft.addToBackStack(null);
                            ft.commit();
                            Toast.makeText(getContext(), "Cleared data for " + formattedDate, Toast.LENGTH_SHORT).show();
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

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch(spinner.getId()){
            case (R.id.weightSpinner):
                switch (parent.getItemAtPosition(position).toString()){
                    case ("lbs"):
                        weightUnit = "lbs";
                        break;
                    case ("kg"):
                        weightUnit = "kg";
                        Toast.makeText(getContext(), weightUnit, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case (R.id.glucoseSpinner):
                switch (parent.getItemAtPosition(position).toString()){
                    case ("mg/dL"):
                        glucoseUnit = "mg/dL";
                        break;
                    case ("mmol/L"):
                        glucoseUnit = "mmol/L";
                        break;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
