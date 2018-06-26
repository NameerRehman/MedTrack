package com.example.nameer.medtrack;

public class MedItem {
    private String medName;
    private String startDate;
    private String endDate;
    private String condition;
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
}
