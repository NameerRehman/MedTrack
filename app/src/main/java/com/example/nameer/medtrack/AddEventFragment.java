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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddEventFragment extends Fragment implements View.OnClickListener {
    private View view;
    private CalendarView calendarView;
    private FloatingActionButton finish;
    private CheckBox headache;
    ArrayList<String> symptomList;
    private String date, symptoms;
    private MedViewModel mMedViewModel;
    private String calSymptoms, calNotes, calMood;
    CardView symptomsCard, moodCard, notesCard;
    LinearLayout symptomsView, moodView, notesView;

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
            date = bundle.getString("date");
        }

        symptomsCard();
        moodCard();

        final TextView text = view.findViewById(R.id.text);
        mMedViewModel = ViewModelProviders.of(this).get(MedViewModel.class);
        mMedViewModel.getEvents(date).observe(this, new Observer<CalendarEvent>() {
            @Override
            public void onChanged(@Nullable final CalendarEvent cal) {
                if(cal!=null) {
                    calSymptoms = cal.getCalSymptoms();
                    text.setText(calSymptoms);
                }
            }
            });

        symptomList = new ArrayList<>();
        headache = view.findViewById(R.id.headache);
        headache.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    symptomList.add("headache");
                    symptomsListToString();
                }if(isChecked==false){
                    symptomList.remove("headache");
                    symptomsListToString();
                }
            }
        });

        finish = view.findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarEvent calendarEvent = new CalendarEvent(date,symptoms,"test","test");
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
}
