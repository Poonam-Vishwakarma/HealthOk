package com.vcreate.healthok.ui.main.user.home.medicationreminder

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vcreate.healthok.R
import com.vcreate.healthok.data.local.AppDatabase
import com.vcreate.healthok.databinding.FragmentMedicationReminderBinding
import com.vcreate.healthok.models.Reminder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MedicationReminderFragment : Fragment(), ReminderAdapter.OnItemClick {

    private lateinit var binding: FragmentMedicationReminderBinding
    private lateinit var reminderAdapter: ReminderAdapter
    var selectedDate: Calendar = Calendar.getInstance()
    private lateinit var appDatabase: AppDatabase
    private lateinit var reminderList: MutableList<Reminder>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMedicationReminderBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())


        lifecycleScope.launch {
            // Perform database operation in IO dispatcher
            reminderList = withContext(Dispatchers.IO) {
                appDatabase.reminderDao().getAll()

            }
            Log.d("myTag", "List$reminderList")
            withContext(Dispatchers.Main) {
                reminderAdapter = ReminderAdapter(reminderList, this@MedicationReminderFragment)
                binding.recyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.recyclerview.adapter = reminderAdapter
            }
        }

        binding.floatingActionButton.setOnClickListener {
            showDialog()
        }

        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_add_reminder)
        dialog.setCancelable(false)

        val selectedDateTextView: TextView = dialog.findViewById(R.id.date)
        val selectDateButton: Button = dialog.findViewById(R.id.selectDate)
        val add: Button = dialog.findViewById(R.id.addButton)



        selectDateButton.setOnClickListener {
            val currentDate = selectedDate
            val year = currentDate.get(Calendar.YEAR)
            val month = currentDate.get(Calendar.MONTH)
            val day = currentDate.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
                // Update selectedDate variable with the chosen date
                selectedDate.set(year, month, day)

                // Call method to show time picker dialog (optional)
                showTimePickerDialog(selectedDateTextView)
            }, year, month, day)
            datePickerDialog.show()
        }

        add.setOnClickListener {
            val message: EditText = dialog.findViewById(R.id.message)
            val mes = message.text.toString()
            val date = selectedDateTextView.text.toString()
            if (mes.isNotEmpty() && date.isNotEmpty()) {
                val reminder = Reminder(mes, date)
                CoroutineScope(Dispatchers.IO).launch {
                    appDatabase.reminderDao().insert(reminder)

                    val list = appDatabase.reminderDao().getAll()

                    withContext(Dispatchers.Main) {
                        reminderAdapter.setData(list)
                    }
                    dialog.dismiss()
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Reminder added", Toast.LENGTH_SHORT)
                            .show()
                    }

                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    val d = dateFormat.parse(date)
                    val milliseconds = d.time ?: 0

                    val notificationHelper  = NotificationHelper(requireContext())
                    notificationHelper.createNotification("HealthOk", mes, milliseconds)
                }
            } else {
                Toast.makeText(requireContext(), "Invalid Input", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    override fun onClick(position: Int) {
        val clickReminder = reminderList[position]
        GlobalScope.launch(Dispatchers.IO) {
            appDatabase.reminderDao().delete(clickReminder)
            val list = appDatabase.reminderDao().getAll()
            withContext(Dispatchers.Main) {
                reminderAdapter.setData(list)
                Toast.makeText(requireContext(), "Reminder Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showTimePickerDialog(textView: TextView) {
        val hour = selectedDate.get(Calendar.HOUR_OF_DAY)
        val minute = selectedDate.get(Calendar.MINUTE)

        // Create and show TimePickerDialog
        val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            // Update selectedDate variable with the chosen time
            selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedDate.set(Calendar.MINUTE, minute)

            val selectedDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(selectedDate.time)
            textView.text = selectedDateTime

        }, hour, minute, false)
        timePickerDialog.show()
    }
}