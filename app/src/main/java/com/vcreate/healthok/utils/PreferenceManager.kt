package com.vcreate.healthok.utils

import android.content.Context
import android.content.SharedPreferences
import com.vcreate.healthok.R

class PreferenceManager (context: Context){

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.period_tracking), Context.MODE_PRIVATE)


    private val editor = prefs.edit()

    fun saveDate(date: String) {
        editor.putString("saved date", date)
        editor.apply()
    }

    fun fetchDate(): String? {
        return prefs.getString("saved date", null)
    }
}