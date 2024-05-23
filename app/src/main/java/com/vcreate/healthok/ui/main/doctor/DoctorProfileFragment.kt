package com.vcreate.healthok.ui.main.doctor

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.vcreate.healthok.R
import com.vcreate.healthok.data.remote.FirebaseClient
import com.vcreate.healthok.databinding.FragmentDoctorProfileBinding
import com.vcreate.healthok.utils.Utility.processAge
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class DoctorProfileFragment : Fragment() {

    private lateinit var binding: FragmentDoctorProfileBinding
    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorProfileBinding.inflate(inflater)
        loadData()


        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding.profileImage.setImageURI(it)
            if (it != null){
                uri = it
                val uid = FirebaseClient.getUid()
                FirebaseClient.uploadImageToFirebase(uid, uri!!, requireContext())
            }
        }


        binding.profileImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.profileEditProfile.setOnClickListener {
            findNavController().navigate(R.id.editProfileDoctorFragment)
        }

        binding.logput.setOnClickListener {
            FirebaseClient.logout()
            requireActivity().finish()
        }
        return binding.root
    }


    private fun loadData() {
        val uid = FirebaseClient.getUid()
        FirebaseClient.getDoctorDataByUid(uid) { doctorData ->
            if (doctorData != null) {
                binding.name.text = doctorData.doctorName
                binding.age.text = processAge(doctorData.dob)
                binding.height.text = doctorData.height
                binding.weight.text = doctorData.weight
                if (doctorData.profilePhoto.isNotEmpty()) {
                    Picasso.get()
                        .load(doctorData.profilePhoto)
                        .placeholder(R.drawable.account_profile)
                        .into(binding.profileImage)
                } else {
                    binding.profileImage.setImageResource(R.drawable.account_profile)
                }

            } else {
                // Handle case where user data is null
                Log.d("myTag", "User data not found")
            }
        }
    }


}