package com.vcreate.healthok.ui.main.doctor

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
import com.vcreate.healthok.data.remote.FirebaseClient.getDoctorDataByUid
import com.vcreate.healthok.data.remote.FirebaseClient.getUid
import com.vcreate.healthok.data.remote.FirebaseClient.getUserDataByUid
import com.vcreate.healthok.data.remote.FirebaseClient.uploadDoctorDataUsingUid
import com.vcreate.healthok.databinding.FragmentEditProfileDoctorBinding
import com.vcreate.healthok.models.Doctor
import com.vcreate.healthok.utils.Validation
import java.util.Calendar

class DoctorEditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileDoctorBinding
    private var isEditModeEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileDoctorBinding.inflate(inflater)
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
        val uid = getUid()
        getDoctorDataByUid(uid) { userData ->
            if (userData != null) {
                binding.fepEtName.setText(userData.doctorName)
                binding.fpEtEmail.setText(userData.email)
                binding.epEtMedicalAbout.setText(userData.about)
                binding.epEtPhoneNumber.setText(userData.doctorPhoneNumber)
                binding.epEtDob.setText(userData.dob)
                binding.epEtGender.setText(userData.gender)
                binding.epEtWeight.setText(userData.weight)
                binding.epEtHeight.setText(userData.height)
                binding.epEtWeightQualification.setText(userData.qualification)
                binding.epEtWeightSpeciality.setText(userData.speciality)
                binding.epEtWeightExperience.setText(userData.experience)
                binding.epEtWeightAddress.setText(userData.address)
            } else {
                // Handle case where user data is null
                Log.d("myTag", "User data not found")
            }
        }
    }

    private fun setEditTextsEditable(editable: Boolean) {
        binding.fepEtName.isEnabled = editable
        binding.epEtMedicalAbout.isEnabled = editable
        binding.fpEtEmail.isEnabled = editable
        binding.epEtPhoneNumber.isEnabled = editable
        binding.epEtWeight.isEnabled = editable
        binding.epEtHeight.isEnabled = editable
        binding.epEtWeightQualification.isEnabled = editable
        binding.epEtWeightSpeciality.isEnabled = editable
        binding.epEtWeightExperience.isEnabled = editable
        binding.epEtWeightAddress.isEnabled = editable
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

    private fun saveChanges() {
        isEditModeEnabled = false
        setEditTextsEditable(isEditModeEnabled)
        toggleButtonsVisibility(isEditModeEnabled)

        // Log the text of EditText fields
        val uid = getUid()
        val name = binding.fepEtName.text.toString()
        val medicalAbout = binding.epEtMedicalAbout.text.toString()
        val email = binding.fpEtEmail.text.toString()
        val phoneNumber = binding.epEtPhoneNumber.text.toString()
        val dob = binding.epEtDob.text.toString()
        val gender = binding.epEtGender.text.toString()
        val weight = binding.epEtWeight.text.toString()
        val height = binding.epEtHeight.text.toString()
        val qualification = binding.epEtWeightQualification.text.toString()
        val speciality = binding.epEtWeightSpeciality.text.toString()
        val experience = binding.epEtWeightExperience.text.toString()
        val address = binding.epEtWeightAddress.text.toString()


        val valid = uid.isNotEmpty() && name.isNotEmpty() && medicalAbout.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() &&
                dob.isNotEmpty() && gender.isNotEmpty() && weight.isNotEmpty() && height.isNotEmpty() && qualification.isNotEmpty() && speciality.isNotEmpty() &&
                experience.isNotEmpty() && address.isNotEmpty()

        if (valid) {
            val doctor = Doctor(
                doctorId = uid,
                doctorName = name,
                doctorPhoneNumber = phoneNumber,
                email = email,
                about = medicalAbout,
                dob = dob,
                gender = gender,
                height = height,
                qualification = qualification,
                experience = experience,
                address = address)
            uploadDoctorDataUsingUid(uid, doctor, requireView())
        } else {
            Validation.showSnackbar(requireView(), "Please fill all the details")
        }
    }
}
