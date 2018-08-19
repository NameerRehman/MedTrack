package com.example.nameer.medtrack;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEventFragment extends Fragment implements View.OnClickListener {
    private View view;
    private CalendarView calendarView;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());

    private MedViewModel mMedViewModel;

    private FloatingActionButton finish;
    private CheckBox headache, diziness, acne, bodyaches, cramps, chills, itchyness, flare, bloating, constipation, diarrhea, gas, abdominalCramps, nausea, stress, moodiness, irritability, insomnia, fatigue, confusion;
    private CheckBox happy;
    private Button deleteEvent;
    private EditText notesEditText;
    ArrayList<String> symptomList, moodList;
    private String symptoms, moods, notes;
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
            //date = bundle.getString("date");
            date = bundle.getLong("date");
        }
        Date dateDisplay = new Date(date); //change Long type to Date type
        String formattedDate = dateFormat.format(dateDisplay); //format by "MMM dd"
        getActivity().setTitle(formattedDate);

        checkedItems();

        deleteEvent = view.findViewById(R.id.deleteEvent);

        mMedViewModel = ViewModelProviders.of(this).get(MedViewModel.class);
        mMedViewModel.getEvents(date).observe(getActivity(), new Observer<CalendarEvent>() {
            @Override
            public void onChanged(@Nullable final CalendarEvent cal) {
                if(cal!=null) {
                    existingEvent = true;
                    notesEditText.setText(cal.getCalNotes());
                    fillSymptomsData("Headache", headache, cal);
                    fillSymptomsData("Diziness", diziness, cal);
                    fillSymptomsData("Acne", acne, cal);
                    fillSymptomsData("Body aches", bodyaches, cal);
                    fillSymptomsData("Cramps", cramps, cal);
                    fillSymptomsData("Chills", chills, cal);

                }else{
                    existingEvent = false;
                }

                deleteEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMedViewModel.deleteCal(date);
                        Fragment CalendarFragment = new CalendarFragment();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragment_container, CalendarFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                        Toast.makeText(getContext(), "Calendar item deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        symptomsCard(); //Cards to show/hide symptoms & moods lists
        moodCard();

        symptomList = new ArrayList<>();
        moodList = new ArrayList<>();

        notesEditText = view.findViewById(R.id.notes);



        finish();
    }

    @Override
    public void onClick(View v) {}

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

    public void finish(){
        finish = view.findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes = notesEditText.getEditableText().toString();

                if(existingEvent == false) {
                    CalendarEvent calendarEvent = new CalendarEvent(date, symptoms, moods, notes);
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
                    mMedViewModel.editCal(date, symptoms, moods, notes);
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
        isCheckedSymptoms(itchyness, R.id.itchyness, "Itchyness");
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
        happy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    moodList.add("Happy");
                    moodListToString();
                }if(isChecked==false){
                    moodList.remove("Happy");
                    moodListToString();
                }
            }
        });
    }

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

    public void fillSymptomsData(String symptom, CheckBox checkBox, CalendarEvent cal) {
        if (cal.getCalSymptoms() != null && cal.getCalSymptoms().contains(symptom)) {
            checkBox.setChecked(true);
        }
    }

}
