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
    private TextView showSymptoms, showMood, showNotes;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private String calSymptoms, calNotes, calMood;
    private List<CalendarEvent> allEvents;

    String test;
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

        showSymptoms = view.findViewById(R.id.showSymptoms);
        showMood = view.findViewById(R.id.showMood);
        showNotes = view.findViewById(R.id.showNotes);

        mMedViewModel = ViewModelProviders.of(this).get(MedViewModel.class);

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
                /*long epoch = dateClicked.getTime();
                final Event ev2 = new Event(Color.RED, epoch, "Church Service and Flag Raising Ceremony");
                if(compactCalendar.getEvents(dateClicked).size() == 0){
                    compactCalendar.addEvent(ev2);
                }else{}*/

                mMedViewModel.getEvents(date).observe(getActivity(), new Observer<CalendarEvent>() {
                    @Override
                    public void onChanged(@Nullable final CalendarEvent cal) {
                        if(cal!=null) {
                            calSymptoms = cal.getCalSymptoms();
                            showSymptoms.setText(calSymptoms);

                            calMood = cal.getCalMood();
                            showMood.setText(calMood);

                            calNotes = cal.getCalNotes();
                            showNotes.setText(calNotes);
                        }else{ //reset textviews to empty if viewing a date with no cal event item
                            showSymptoms.setText("");
                            showMood.setText("");
                            showNotes.setText("");
                        }
                    }
                });
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getActivity().setTitle(dateFormat.format(firstDayOfNewMonth));
            }
        });



       /* date = Calendar.getInstance().getTime().toString();
        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull final CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + month + "/" + year;
            }
        });*/

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

