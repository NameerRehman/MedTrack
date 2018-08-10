package com.example.nameer.medtrack;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
    private MedViewModel mMedViewModel;

    private FloatingActionButton finish;
    private CheckBox headache, diziness, acne, bodyaches, cramps, chills, itchyness, flare, bloating, constipation, diarrhea, gas, abdominalCramps, nausea, stress, moodiness, irritability, insomnia, fatigue, confusion;
    private CheckBox happy;
    private EditText notesEditText;
    ArrayList<String> symptomList, moodList;
    private String symptoms, moods, notes;
    long date;
    CardView symptomsCard, moodCard, notesCard;
    LinearLayout symptomsView, moodView, notesView;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());


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
        Date dateDisplay = new Date(date);
        String formattedDate = dateFormat.format(dateDisplay);
        getActivity().setTitle(formattedDate);

        mMedViewModel = ViewModelProviders.of(this).get(MedViewModel.class);

        symptomsCard(); //Cards to show/hide symptoms & moods lists
        moodCard();

        symptomList = new ArrayList<>();
        moodList = new ArrayList<>();

        notesEditText = view.findViewById(R.id.notes);

        checkedItems();


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
                CalendarEvent calendarEvent = new CalendarEvent(date,symptoms,moods,notes);
                mMedViewModel.insertCal(calendarEvent);

                Fragment CalendarFragment = new CalendarFragment();
                //addEventFragment.setArguments(bundle);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragment_container, CalendarFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    public void checkedItems(){
        headache = view.findViewById(R.id.headache);
        headache.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    symptomList.add("Headache");
                    symptomsListToString();
                }if(isChecked==false){
                    symptomList.remove("Headache");
                    symptomsListToString();
                }
            }
        });

        diziness = view.findViewById(R.id.diziness);
        diziness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    symptomList.add("Diziness");
                    symptomsListToString();
                }if(isChecked==false){
                    symptomList.remove("Diziness");
                    symptomsListToString();
                }
            }
        });

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


    public String symptomsListToString(){
        symptoms="";
        for (int i = 0; i < symptomList.size() ; i++) {
            if(i == (symptomList.size() -1)){  //if on last item of the list, do not add a comma after
                symptoms = symptoms + symptomList.get(i);
            }else{
                symptoms = symptoms + symptomList.get(i) + ", ";
            }
        }
        Toast.makeText(getContext(), symptoms, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getContext(), moods, Toast.LENGTH_SHORT).show();
        return moods;
    }

}
