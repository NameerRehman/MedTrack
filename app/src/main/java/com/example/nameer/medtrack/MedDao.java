package com.example.nameer.medtrack;

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
    @Query("SELECT * FROM med_table ORDER BY start_date")
    LiveData<List<MedItem>> getMedListByDate();

    @Query("SELECT * FROM med_table WHERE condition = :condition")
    LiveData<List<MedItem>> getMedsByCondition(String condition);

    @Query("SELECT DISTINCT condition FROM med_table")
    LiveData<List<String>> getConditions();

    @Insert
    void insertAll(MedItem...medItems);

    @Query("DELETE FROM med_table WHERE id = :id")
    void delete(int id);

    @Query("UPDATE med_table SET med_name = :medName, start_date = :startDate, end_date = :endDate, condition = :condition, notes = :notes WHERE id =:id")
    void update(String medName, String startDate, String endDate, String condition, String notes, int id);

}
