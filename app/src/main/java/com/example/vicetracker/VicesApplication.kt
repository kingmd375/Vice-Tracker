package com.example.vicetracker

import android.app.Application
import com.example.vicetracker.Model.ViceRepository
import com.example.vicetracker.Model.ViceRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class VicesApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { ViceRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { ViceRepository(database.viceDao(), database.dayAmountDao()) }
}