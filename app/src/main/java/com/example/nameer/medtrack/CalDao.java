package com.example.nameer.medtrack;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

//This class maps method calls to database queries
//When the Repository calls getMedListByDate, Room can execute SELECT * from med_table ORDER BY start_date

@Dao
public interface CalDao {

    @Query("SELECT * FROM cal_event WHERE date = :date")
    LiveData<CalendarEvent> getEvents(String date);

    //Edit database entries
    @Insert
    void insertAll(CalendarEvent...calendarEvents);

    @Query("DELETE FROM cal_event WHERE date = :date")
    void deleteEvent(int date);

}
