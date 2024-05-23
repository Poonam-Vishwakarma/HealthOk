package com.vcreate.healthok.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.button.MaterialButton
import com.vcreate.healthok.R
import com.vcreate.healthok.data.remote.FirebaseClient
import com.vcreate.healthok.models.Appointment
import kotlinx.coroutines.flow.combine
import java.util.Calendar

class BookingDialog(private val layout: Int, private val vb: ViewBinding, private val userId: String, private val doctorId: String) : DialogFragment() {

    private lateinit var dialogView: View

    private var selectedDate: String? = null
    private var selectedTime: String? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialogView = requireActivity().layoutInflater.inflate(layout, null)
        dialog.setContentView(dialogView)

        val window = dialog.window
        val params = window?.attributes
        params?.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        window?.attributes = params

        val cancelButton = dialogView.findViewById<Button>(R.id.btn_book_appointment_cancel)
        val dateButton = dialogView.findViewById<Button>(R.id.btn_date)
        val timeButton = dialogView.findViewById<Button>(R.id.btn_time)
        val bookButton = dialogView.findViewById<Button>(R.id.btn_book_appointment)
        val selectDateTv = dialogView.findViewById<TextView>(R.id.tv_data)
        val selectTimeTv = dialogView.findViewById<TextView>(R.id.tv_time)

        dateButton.setOnClickListener {
            // Create a DatePickerDialog
            val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                // Update selectedDate variable with the selected date
                selectedDate = "$dayOfMonth/${month + 1}/$year"
                // Update selectDateTv with the selected date
                selectDateTv.text = selectedDate
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

            // Show the DatePickerDialog
            datePickerDialog.show()
        }

        timeButton.setOnClickListener {
            // Create a TimePickerDialog
            val timePickerDialog = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
                // Check if selected time is between 9 AM and 9 PM
                if (hourOfDay in 9..20) {
                    // Update selectedTime variable with the selected time
                    selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                    // Update timeTv with the selected time
                    selectTimeTv.text = selectedTime
                } else {
                    // If selected time is not between 9 AM and 9 PM, show an error message
                    Toast.makeText(requireContext(), "Please select a time between 9 AM and 9 PM", Toast.LENGTH_SHORT).show()
                }
            }, 9, 0, false) // Set initial time to 9:00 AM

            // Show the TimePickerDialog
            timePickerDialog.show()
        }

        bookButton.setOnClickListener {
            selectedDate?.let { date ->
                selectedTime?.let { time ->
                    // Create an appointment object with the selected date, time, and other necessary details
                    val appointment = Appointment(
                        userId = userId,
                        doctorId = doctorId,
                        appointmentData = date,
                        appointmentTime = time
                    )
                    // Book the appointment
                    FirebaseClient.appointBook(appointment)
                }
            }
            Validation.showSnackbar(vb.root, "Appointment Booked Successfully")
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        return dialog
    }


    override fun dismiss() {
        super.dismiss()
    }
}