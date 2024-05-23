package com.vcreate.healthok.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Reminder")
data class Reminder(
    @ColumnInfo(name = "Message")
    val message: String,
    @ColumnInfo(name = "Date")
    val date: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
