package com.example.vicetracker.Model

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.Date

class ViceRepository(private val viceDao: ViceDao, private val dayAmountDao: DayAmountDao) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allVices: Flow<List<Vice>> = viceDao.getAlphabetizedVices()

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    fun getVice(id:Int): Flow<Vice> {
        return viceDao.getVice(id)
    }

    fun getViceNotLive(id:Int):Vice{
        return viceDao.getViceNotLive(id)
    }

    // Get list of dayAmounts of a vice
    fun getViceDayAmounts(id:Int): Flow<List<DayAmount>> {
        return dayAmountDao.getViceDayAmounts(id)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(vice: Vice) {
        //If you don't run this in a scope that is still active
        //Then the call won't complete
        viceDao.insert(vice)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(vice: Vice) {
        viceDao.update(vice)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteVice(id: Int) {
        Log.d("Model","Deleting id: $id")
        viceDao.deleteVice(id)
    }

    // increment the amount of a vice and keep track of the engagement in a dayAmount
    // Since we need to keep track of daily engagement, this either updates the most recent dayAmount or inserts a new one.
    suspend fun incrementViceAmount(vice: Vice) {
        // get the current date without hours, minutes, seconds, milliseconds
        val currentTime = Date().time
        val currentDate = currentTime - currentTime % (24*60*60*1000)

        // If the previous date (with time) was later than today's date, update the dayAmount corresponding to today
        if(vice.prevDate == currentDate) {
            // update today's dayAmount
            val updatedDayAmount = vice.id?.let { DayAmount(it, currentDate, vice.amount+1) }
            if (updatedDayAmount != null) {
                dayAmountDao.update(updatedDayAmount)
            }
        } else {
            // create a new dayAmount corresponding to today
            val newDayAmount = vice.id?.let { DayAmount(it, currentDate, 1) }
            if (newDayAmount != null) {
                dayAmountDao.insert(newDayAmount)
            }
        }
    }
}