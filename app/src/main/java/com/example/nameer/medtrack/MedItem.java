package com.example.nameer.medtrack;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;


@Entity(tableName="med_table")
public class MedItem implements Serializable{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "med_name") //in databases - use underscore convention
    private String medName;


    @ColumnInfo(name = "start_date")
    private String startDate;

    @ColumnInfo(name = "end_date")
    private String endDate;

    @ColumnInfo(name = "condition")
    private String condition;

    @ColumnInfo(name = "notes")
    private String notes;

    //constructor to initialize variables
    public MedItem(String medName, String startDate, String endDate, String condition, String notes) {
        this.medName = medName;
        this.condition = condition;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    //getters to retrieve variables
    public String getMedName() {
        return medName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCondition() {
        return condition;
    }

    public String getNotes() {
        return notes;
    }

    @NonNull
    public int getId() { return id; }

    public void setId(@NonNull int id) { this.id = id; }
}
