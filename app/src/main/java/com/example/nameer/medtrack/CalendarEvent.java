package com.example.nameer.medtrack;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

@Entity (tableName = "cal_event")
public class CalendarEvent implements Serializable{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int calId;

    @ColumnInfo(name = "date")
    private long calDate;

    @ColumnInfo(name = "symptoms")
    private String calSymptoms;

    @ColumnInfo(name = "mood")
    private String calMood;

    @ColumnInfo(name = "notes")
    private String calNotes;

    public CalendarEvent(long calDate, String calSymptoms, String calMood, String calNotes){
        this.calDate = calDate;
        this.calSymptoms = calSymptoms;
        this.calMood = calMood;
        this.calNotes = calNotes;
    }

    @NonNull
    public int getCalId() {
        return calId;
    }

    public void setCalId(@NonNull int id) {
        this.calId = id;
    }


    public long getCalDate() {
        return calDate;
    }

    public void setCalDate(long calDate) {
        this.calDate = calDate;
    }

    public String getCalSymptoms() {
        return calSymptoms;
    }

    public void setCalSymptoms(String calSymptoms) {
        this.calSymptoms = calSymptoms;
    }

    public String getCalMood() {
        return calMood;
    }

    public void setCalMood(String calMood) {
        this.calMood = calMood;
    }

    public String getCalNotes() {
        return calNotes;
    }

    public void setCalNotes(String calNotes) {
        this.calNotes = calNotes;
    }
}
