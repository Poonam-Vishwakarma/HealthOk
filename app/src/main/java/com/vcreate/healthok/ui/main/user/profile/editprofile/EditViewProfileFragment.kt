package com.vcreate.healthok.ui.main.user.profile.editprofile

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vcreate.healthok.data.remote.FirebaseClient
import com.vcreate.healthok.databinding.FragmentEditViewProfileBinding
import com.vcreate.healthok.models.Doctor
import com.vcreate.healthok.models.Patient
import com.vcreate.healthok.utils.Validation
import java.util.Calendar

class EditViewProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditViewProfileBinding
    private var isEditModeEnabled = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditViewProfileBinding.inflate(inflater, container, false)
        loadData()

        setEditTextsEditable(isEditModeEnabled)

        binding.fragmentAddEditBtnUpdateChanges.setOnClickListener {
            // Toggle edit mode
            isEditModeEnabled = !isEditModeEnabled
            setEditTextsEditable(isEditModeEnabled)
            toggleButtonsVisibility(isEditModeEnabled)
        }


        // date of birth
        binding.fragmentAddEditLin6.setOnClickListener {
            // Get the current date from the EditText if available
            val dobEditText = binding.epEtDob
            val currentDate = dobEditText.text.toString()
            Toast.makeText(requireContext(), "Click ", Toast.LENGTH_SHORT).show()

            // Extract year, month, and day from the current date if available
            val calendar = Calendar.getInstance()
            val year: Int
            val month: Int
            val day: Int

            if (currentDate.isNotEmpty()) {
                val dateParts = currentDate.split("/")
                year = dateParts[2].toInt()
                month = dateParts[1].toInt() - 1 // Month in DatePickerDialog starts from 0
                day = dateParts[0].toInt()
            } else {
                // If no date is set, use current date as default
                year = calendar.get(Calendar.YEAR)
                month = calendar.get(Calendar.MONTH)
                day = calendar.get(Calendar.DAY_OF_MONTH)
            }

            // Create and show DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    dobEditText.setText(selectedDate)
                },
                year,
                month,
                day
            )

            // Show the DatePickerDialog
            datePickerDialog.show()
        }

        // gender
        binding.fragmentAddEditLin7.setOnClickListener {
            val genderOptions = arrayOf("Male", "Female", "Other") // Define the gender options
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Select Gender")
            builder.setItems(genderOptions) { _, which ->
                // Set the selected gender in the EditText
                binding.epEtGender.setText(genderOptions[which])
            }
            val dialog = builder.create()
            dialog.show()
        }


        binding.fragmentAddEditBtnSaveChanges.setOnClickListener {
            saveChanges()
        }

        return binding.root
    }


    private fun loadData() {
        val uid = FirebaseClient.getUid()
        FirebaseClient.getPatientDataByUid(uid) { patientData ->
            if (patientData != null) {
                binding.fepEtName.setText(patientData.name)
                binding.fpEtEmail.setText(patientData.email)
                binding.epEtMedicalHistory.setText(patientData.medicalHistory)
                binding.epEtPhoneNumber.setText(patientData.phoneNumber)
                binding.epEtDob.setText(patientData.dob)
                binding.epEtGender.setText(patientData.gender)
                binding.epEtWeight.setText(patientData.weight)
                binding.epEtHeight.setText(patientData.height)
            } else {
                // Handle case where user data is null
                Log.d("myTag", "User data not found")
            }
        }
    }


    private fun saveChanges() {
        isEditModeEnabled = false
        setEditTextsEditable(isEditModeEnabled)
        toggleButtonsVisibility(isEditModeEnabled)

        // Log the text of EditText fields
        val uid = FirebaseClient.getUid()
        val name = binding.fepEtName.text.toString()
        val email = binding.fpEtEmail.text.toString()
        val phoneNumber = binding.epEtPhoneNumber.text.toString()
        val dob = binding.epEtDob.text.toString()
        val gender = binding.epEtGender.text.toString()
        val weight = binding.epEtWeight.text.toString()
        val height = binding.epEtHeight.text.toString()
        val medicalHistory = binding.epEtMedicalHistory.text.toString()


        val valid = uid.isNotEmpty() && name.isNotEmpty() && medicalHistory.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() &&
                dob.isNotEmpty() && gender.isNotEmpty() && weight.isNotEmpty() && height.isNotEmpty()

        if (valid) {
            val patient = Patient (
                uid,name,
                email, phoneNumber, dob, gender, weight, height, medicalHistory
            )
            FirebaseClient.uploadPatientDataUsingUid(uid, patient, requireView())
        } else {
            Validation.showSnackbar(requireView(), "Please fill all the details")
        }
    }

    private fun toggleButtonsVisibility(editModeEnabled: Boolean) {
        if (editModeEnabled) {
            binding.fragmentAddEditBtnUpdateChanges.visibility = View.GONE
            binding.fragmentAddEditBtnSaveChanges.visibility = View.VISIBLE
        } else {
            binding.fragmentAddEditBtnUpdateChanges.visibility = View.VISIBLE
            binding.fragmentAddEditBtnSaveChanges.visibility = View.GONE
        }
    }

    private fun setEditTextsEditable(editable: Boolean) {
        binding.fepEtName.isEnabled = editable
        binding.fpEtEmail.isEnabled = editable
        binding.epEtPhoneNumber.isEnabled = editable
        binding.epEtWeight.isEnabled = editable
        binding.epEtHeight.isEnabled = editable
        binding.epEtMedicalHistory.isEnabled = editable
    }
}