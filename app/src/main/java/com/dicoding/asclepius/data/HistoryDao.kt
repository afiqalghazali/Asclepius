package com.dicoding.asclepius.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * from history_database ORDER BY id DESC")
    fun getResults(): LiveData<List<HistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addResults(history: HistoryEntity)

    @Query("DELETE FROM history_database")
    fun deleteAllResults()
}