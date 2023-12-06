package com.example.vicetracker.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DayAmountDao {
    // Get all occurrences of a dayAmount in ascending order
    @Query("SELECT * FROM dayAmount_table WHERE vice_id=:id ORDER BY date ASC")
    fun getViceDayAmounts(id:Int): Flow<List<DayAmount>>

    // Insert a single dayAmount
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dayAmount: DayAmount)

    // Update a single dayAmount
    @Update
    suspend fun update(dayAmount: DayAmount)

    // Delete all dayAmounts of a Vice
    @Query("DELETE FROM dayAmount_table WHERE vice_id=:id")
    suspend fun deleteViceDayAmounts(id: Int)

    //Delete all dayAmounts
    @Query("DELETE FROM dayAmount_table")
    suspend fun deleteAll()
}