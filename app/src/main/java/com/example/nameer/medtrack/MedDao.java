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

    @Insert
    void insertAll(MedItem...medItems);

    @Query("DELETE FROM med_table WHERE id = :id")
    void delete(int id);

    @Query("UPDATE med_table SET med_name = :medName WHERE id =:id")
    void update(String medName, int id);

}
