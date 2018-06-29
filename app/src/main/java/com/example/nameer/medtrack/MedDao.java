package com.example.nameer.medtrack;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MedDao {
    @Query("SELECT * FROM med_table ORDER BY start_date")
    LiveData<List<MedItem>> getMedList();

    @Insert
    void insertAll(MedItem...medItems);

    @Query("DELETE FROM med_table")
    void deleteAll();

    @Delete
    void delete(MedItem...medItems);
}
