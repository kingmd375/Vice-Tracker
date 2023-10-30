package com.example.vicetracker.Model

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Vice::class), version = 1, exportSchema = false)
abstract class ViceRoomDatabase : RoomDatabase() {
    abstract fun viceDao(): ViceDao

    private class ViceDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("Database","Here1")

            INSTANCE?.let { database ->
                scope.launch {
                    val viceDao = database.viceDao()

                    // Delete all content here.
                    viceDao.deleteAll()

                    // Add sample vices.
                    var vice = Vice(null,"Spaghetti",1)
                    viceDao.insert(vice)
                    vice = Vice(null,"Chicken",7)
                    viceDao.insert(vice)
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