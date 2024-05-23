package com.vcreate.healthok.ui.main.user.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vcreate.healthok.databinding.FragmentCycleBinding
import com.vcreate.healthok.utils.PreferenceManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CycleFragment : Fragment() {

    private lateinit var binding: FragmentCycleBinding
    private val calendar = Calendar.getInstance()
    private lateinit var pref: PreferenceManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCycleBinding.inflate(inflater, container, false)
        pref = PreferenceManager(requireContext())

        setUpViews()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btn.setOnClickListener {
            showDatePicker()
        }
    }

    private fun setUpViews() {
        val savedDate = pref.fetchDate()
        Log.d("myTag", savedDate.toString())
        if (savedDate.isNullOrEmpty()) {
            binding.btn.text = "Click to set"
        } else {
            binding.calendarView.date = convert(savedDate)
            binding.btn.text = "Click to update"
        }
    }

    private fun convert(date: String): Long {
        val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date: Date? = dateFormat.parse(date)
        return date!!.time
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(), { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.btn.text = "Click to update"
                binding.calendarView.date = convert(formattedDate)
                Log.d("myTag", formattedDate)
                pref.saveDate(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Set the maximum date to today to prevent selecting future dates
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

        // Show the DatePicker dialog
        datePickerDialog.show()
    }

}

