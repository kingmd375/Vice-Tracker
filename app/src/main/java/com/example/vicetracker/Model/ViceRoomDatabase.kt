package com.example.vicetracker.Model

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Vice::class, DayAmount::class), version = 1, exportSchema = false)
abstract class ViceRoomDatabase : RoomDatabase() {
    abstract fun viceDao(): ViceDao
    abstract fun dayAmountDao(): DayAmountDao

    private class ViceDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("Database","Here1")

            INSTANCE?.let { database ->
                scope.launch {
                    val viceDao = database.viceDao()
                    val dayAmountDao = database.dayAmountDao()

                    // Delete all content here.
                    viceDao.deleteAll()
                    dayAmountDao.deleteAll()

                    // Add sample vices.
                    var vice = Vice(0,"Smoking",10, 2, "cigarettes", "1", 1701820800000)
                    viceDao.insert(vice)


                    // Add sample dayAmounts
                    var dayAmount = DayAmount(0, 1701820800000, 4)
                    dayAmountDao.insert(dayAmount)
                    dayAmount = DayAmount(0, 1701820800000 - 24*3600*1000, 7)
                    dayAmountDao.insert(dayAmount)
                    dayAmount = DayAmount(0, 1701820800000 - 3*24*3600*1000, 10)
                    dayAmountDao.insert(dayAmount)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViceRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ViceRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ViceRoomDatabase::class.java,
                    "vice_database"
                )
                    .addCallback(ViceDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}