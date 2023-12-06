package com.example.vicetracker.Model

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ViceRepository(private val viceDao: ViceDao) {
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

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(vice: Vice) {
        //Note that I am pretending this is a network call by adding
        //a 5 second sleep call here
        //If you don't run this in a scope that is still active
        //Then the call won't complete
        Thread.sleep(5000)
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

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateCompleted(id: Int) {
        viceDao.updateCompleted(id)
    }
}