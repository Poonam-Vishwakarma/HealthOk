package com.vcreate.healthok.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vcreate.healthok.models.Reminder


@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminder")
    fun getAll(): MutableList<Reminder>

    @Query("SELECT * FROM Reminder WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Reminder>

    @Insert
    fun insert(reminder: Reminder)

    @Insert
    fun insertAll(vararg users: Reminder)

    @Delete
    fun delete(user: Reminder)
}