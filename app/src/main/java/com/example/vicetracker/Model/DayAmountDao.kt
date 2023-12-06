package com.example.vicetracker.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DayAmountDao {
    // Get all occurrences of a vice
    @Query("SELECT * FROM dayAmount_table WHERE vice_id=:id")
    fun getViceDayAmounts(id:Int): Flow<List<DayAmount>>

    // Insert a single vice
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dayAmount: DayAmount)

    // Update a single vice
    @Update
    suspend fun update(dayAmount: DayAmount)

    // Delete all dayAmounts of a Vice
    @Query("DELETE FROM dayAmount_table WHERE vice_id=:id")
    suspend fun deleteViceDayAmounts(id: Int)

    //Delete all dayAmounts
    @Query("DELETE FROM dayAmount_table")
    suspend fun deleteAll()
}