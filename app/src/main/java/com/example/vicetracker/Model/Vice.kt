package com.example.vicetracker.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vice_table")
class Vice (
    //Note that we now allow for ID as the primary key
    //It needs to be nullable when creating a new word in the database
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "vice") var vice: String,
    @ColumnInfo(name = "quantity") val quantity: Int
)