package com.example.vicetracker.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ViceDao {
    //Get all vices alphabetized
    @Query("SELECT * FROM vice_table ORDER BY name ASC")
    fun getAlphabetizedVices(): Flow<List<Vice>>

    //Get a single vice with a given id
    @Query("SELECT * FROM vice_table WHERE id=:id")
    fun getVice(id:Int): Flow<Vice>

    //Get a single vice with a given id
    @Query("SELECT * FROM vice_table WHERE id=:id")
    fun getViceNotLive(id:Int): Vice

    //Insert a single vice
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vice: Vice)

    //Delete all vices
    @Query("DELETE FROM vice_table")
    suspend fun deleteAll()

    //Update a single vice
    @Update
    suspend fun update(vice: Vice):Int

    @Query("DELETE from vice_table WHERE id=:id")
    suspend fun deleteVice(id: Int)

    @Query("UPDATE vice_table SET amount=amount+1 WHERE id=:id")
    suspend fun updateCompleted(id: Int)
}