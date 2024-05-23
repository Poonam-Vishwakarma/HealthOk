package com.vcreate.healthok.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utility {

     fun processAge(dob: String): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calDOB = Calendar.getInstance()
        val calToday = Calendar.getInstance()

        calDOB.time = dateFormat.parse(dob) ?: return "" // Parse the DOB string

        var age = calToday.get(Calendar.YEAR) - calDOB.get(Calendar.YEAR)
        if (calToday.get(Calendar.DAY_OF_YEAR) < calDOB.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age.toString()
    }

}