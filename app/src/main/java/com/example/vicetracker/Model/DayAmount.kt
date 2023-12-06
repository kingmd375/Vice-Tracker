package com.example.vicetracker.Model

import androidx.room.ColumnInfo
import androidx.room.Entity

// how many times a vice was engaged in on a particular day
//
@Entity(tableName = "dayAmount_table", primaryKeys = ["vice_id", "date"])
class DayAmount (
    @ColumnInfo(name = "vice_id") var id: Int,
    @ColumnInfo(name = "date") var date: Long,
    @ColumnInfo(name = "amount") var amount: Int
)