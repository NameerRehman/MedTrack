package com.nrinfinity.nameer.medtrack;

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

    @ColumnInfo(name = "weight")
    private String calWeight;

    @ColumnInfo(name = "glucose")
    private String calGlucose;

    @ColumnInfo(name = "bp")
    private String calBp;

    @ColumnInfo(name = "pulse")
    private String calPulse;

    public CalendarEvent(long calDate, String calSymptoms, String calMood, String calNotes, String calWeight, String calGlucose, String calBp, String calPulse){
        this.calDate = calDate;
        this.calSymptoms = calSymptoms;
        this.calMood = calMood;
        this.calNotes = calNotes;
        this.calWeight = calWeight;
        this.calGlucose = calGlucose;
        this.calBp = calBp;
        this.calPulse = calPulse;
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

    public String getCalSymptoms() {
        return calSymptoms;
    }

    public String getCalMood() {
        return calMood;
    }

    public String getCalNotes() {
        return calNotes;
    }

    public String getCalWeight() { return calWeight; }

    public String getCalGlucose() { return calGlucose;  }

    public String getCalBp() { return calBp;   }

    public String getCalPulse() {  return calPulse;  }
}
