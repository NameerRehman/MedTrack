package com.example.nameer.medtrack;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment implements View.OnClickListener {
    private View view;
    CompactCalendarView compactCalendar;
    private FloatingActionButton add;
    private long date;
    private MedViewModel mMedViewModel;
    private TextView showSymptoms, showMood, showNotes, showWeight, showGlucose, showBp, showPulse;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private List<CalendarEvent> allEvents;

    public interface DataPassListener{
        public void passData(String data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        compactCalendar = view.findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        getActivity().setTitle(dateFormat.format(compactCalendar.getFirstDayOfCurrentMonth()));

        // Calendar c = Calendar.getInstance();
        //date = c.getTimeInMillis();

        //Symptoms, Mood, and Notes fields under calendar
        showSymptoms = view.findViewById(R.id.showSymptoms);
        showMood = view.findViewById(R.id.showMood);
        showNotes = view.findViewById(R.id.showNotes);
        showWeight = view.findViewById(R.id.showWeight);
        showGlucose = view.findViewById(R.id.showGlucose);
        showBp = view.findViewById(R.id.showBp);
        showPulse = view.findViewById(R.id.showPulse);

        mMedViewModel = ViewModelProviders.of(this).get(MedViewModel.class);

        //Get all events and add dot under corresponding dates
        mMedViewModel.getallEvents().observe(this, new Observer<List<CalendarEvent>>() {
            @Override
            public void onChanged(@Nullable final List<CalendarEvent> eventList) {
                allEvents = eventList;
                for (int i = 0; i < allEvents.size(); i++) {
                    long epoch = allEvents.get(i).getCalDate();
                    final Event ev2 = new Event(Color.RED, epoch, "Church Service and Flag Raising Ceremony");
                    if(compactCalendar.getEvents(allEvents.get(i).getCalDate()).size() == 0){
                        compactCalendar.addEvent(ev2);
                    }else{}
                }
            }
        });

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                date = dateClicked.getTime(); //converts date to a long

                //Fill fields with data for selected date (if not null)
                mMedViewModel.getEvents(date).observe(getActivity(), new Observer<CalendarEvent>() {
                    @Override
                    public void onChanged(@Nullable final CalendarEvent cal) {
                        if(cal!=null) {
                            showSymptoms.setText(cal.getCalSymptoms());
                            showMood.setText(cal.getCalMood());
                            showNotes.setText(cal.getCalNotes());
                            if (cal.getCalWeight().equals(" lbs") || cal.getCalWeight().equals(" kg")){
                                showWeight.setText("");
                            }else {
                                showWeight.setText(cal.getCalWeight());
                            }
                            if (cal.getCalGlucose().equals(" mmol/dL") || cal.getCalGlucose().equals(" mg/dL")){
                                showGlucose.setText("");
                            }else {
                                showGlucose.setText(cal.getCalGlucose());
                            }
                            if (cal.getCalBp().equals("/ sp/dp")){
                                showBp.setText("");
                            }else {
                                showBp.setText(cal.getCalBp());
                            }
                            if (cal.getCalPulse().equals( "bpm")){
                                showPulse.setText("");
                            }else {
                                showPulse.setText(cal.getCalPulse());
                            }

                        }else{ //reset textviews to empty if viewing a date with no cal event item
                            showSymptoms.setText("");
                            showMood.setText("");
                            showNotes.setText("");
                            showWeight.setText("");
                            showGlucose.setText("");
                            showBp.setText("");
                            showPulse.setText("");
                        }
                    }
                });
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getActivity().setTitle(dateFormat.format(firstDayOfNewMonth));
            }
        });

        add = view.findViewById(R.id.addNote);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("date",date);
                if (date != 0L){
                    Fragment addEventFragment = new AddEventFragment();
                    addEventFragment.setArguments(bundle);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragment_container, addEventFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }else{
                    Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {}
}

