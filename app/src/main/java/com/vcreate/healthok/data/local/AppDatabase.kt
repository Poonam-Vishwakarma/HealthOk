package com.vcreate.healthok.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vcreate.healthok.models.Reminder

@Database(entities = [Reminder::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                  val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "HealthOk"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
