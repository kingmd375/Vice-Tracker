package com.example.vicetracker.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vice_table")
class Vice (
    //Note that we now allow for ID as the primary key
    //It needs to be nullable when creating a new word in the database
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "limit") var limit: Int,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "unit") var unit: String,
    @ColumnInfo(name = "increment") var increment: String
)