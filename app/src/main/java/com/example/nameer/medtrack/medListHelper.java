package com.example.nameer.medtrack;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

import java.util.List;


public class medListHelper {

    private List<MedItem> medList;
    private Context context;

    private String calStartDate;
    private String calEndDate;
    private String medName;
    private String condition;

    public medListHelper(String medName, String calStartDate, String calEndDate, String condition) {
        this.calStartDate = calStartDate;
        this.calEndDate = calEndDate;
        this.medName = medName;
        this.condition = condition;
    }

    public medListHelper(List<MedItem> medList) {
        this.medList = medList;
    }

    public String getCalStartDate() {
        return calStartDate; }

    public void setCalStartDate(String calStartDate) {
        this.calStartDate = calStartDate; }

    public String getCalEndDate() {
        return calEndDate; }

    public void setCalEndDate(String calEndDate) {
        this.calEndDate = calEndDate; }

    public String getMedName() {
        return medName; }

    public void setMedName(String medName) {
        this.medName = medName; }

    public String getCondition() {
        return condition; }

    public void setCondition(String condition) {
        this.condition = condition; }

    public List<MedItem> getMedList() {
        return medList;
    }
}


