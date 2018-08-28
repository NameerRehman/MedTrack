package com.nrinfinity.nameer.medtrack;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

//This class maps method calls to database queries
//When the Repository calls getMedListByDate, Room can execute SELECT * from med_table ORDER BY start_date

@Dao
public interface MedDao {
    //Query by order of meds
    @Query("SELECT * FROM med_table ORDER BY start_date")
    LiveData<List<MedItem>> getMedsByStartDate();

    @Query("SELECT * FROM med_table")
    LiveData<List<MedItem>> getMedsByDateAdded();

    @Query("SELECT * FROM med_table ORDER BY med_name")
    LiveData<List<MedItem>> getMedsByAlphabetical();

    @Query("SELECT * FROM med_table WHERE end_date = :endDate")
    LiveData<List<MedItem>> getMedsByOngoingDA(String endDate);

    @Query("SELECT * FROM med_table WHERE end_date = :endDate ORDER BY start_date")
    LiveData<List<MedItem>> getMedsByOngoingStart(String endDate);

    @Query("SELECT * FROM med_table WHERE end_date = :endDate ORDER BY med_name")
    LiveData<List<MedItem>> getMedsByOngoingAlphabetical(String endDate);

    //Query by condition
    @Query("SELECT DISTINCT condition FROM med_table")
    LiveData<List<String>> getConditions();

    @Query("SELECT * FROM med_table WHERE condition = :condition ")
    LiveData<List<MedItem>> getMedsByConditionDateAdded(String condition);

    //Query by condition & order of meds
    @Query("SELECT * FROM med_table WHERE condition = :condition ORDER BY med_name")
    LiveData<List<MedItem>> getMedsByConditionAlphabetical(String condition);

    @Query("SELECT * FROM med_table WHERE condition = :condition ORDER BY start_date")
    LiveData<List<MedItem>> getMedsByConditionStartDate(String condition);

    @Query("SELECT * FROM med_table WHERE condition = :condition and end_date = :endDate")
    LiveData<List<MedItem>> getMedsByConditionOngoingDA(String condition, String endDate);

    @Query("SELECT * FROM med_table WHERE condition = :condition and end_date = :endDate ORDER BY start_date")
    LiveData<List<MedItem>> getMedsByConditionOngoingStart(String condition, String endDate);

    @Query("SELECT * FROM med_table WHERE condition = :condition and end_date = :endDate ORDER BY med_name")
    LiveData<List<MedItem>> getMedsByConditionOngoingAlphabetical(String condition, String endDate);

    //Edit database entries
    @Insert
    void insertAll(MedItem...medItems);

    @Query("DELETE FROM med_table WHERE id = :id")
    void delete(int id);

    @Query("UPDATE med_table SET med_name = :medName, start_date = :startDate, end_date = :endDate, condition = :condition, notes = :notes WHERE id =:id")
    void update(String medName, String startDate, String endDate, String condition, String notes, int id);

}
