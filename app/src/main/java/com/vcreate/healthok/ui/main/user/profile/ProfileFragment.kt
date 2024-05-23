package com.vcreate.healthok.ui.main.user.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.vcreate.healthok.R
import com.vcreate.healthok.data.remote.FirebaseClient
import com.vcreate.healthok.databinding.FragmentProfileBinding
import com.vcreate.healthok.utils.Utility


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        loadData()

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding.profilePicture.setImageURI(it)
            if (it != null){
                uri = it
                val uid = FirebaseClient.getUid()
                FirebaseClient.uploadImageToFirebase(uid, uri!!, requireContext())
            }
        }

        binding.profilePicture.setOnClickListener {
            pickImage.launch("image/*")
        }



        binding.profileEditProfile.setOnClickListener {
            findNavController().navigate(R.id.editViewProfileFragment)
        }

        binding.appointmentsview.setOnClickListener {
            findNavController().navigate(R.id.appointmentFragment)
        }


        binding.logput.setOnClickListener {
            auth.signOut()
            requireActivity().finish()

        }

        binding.chatView.setOnClickListener {
            findNavController().navigate(R.id.supportFragment)
        }

        return binding.root
    }

    private fun loadData() {
        val uid = FirebaseClient.getUid()
        FirebaseClient.getPatientDataByUid(uid) { patientData ->
            if (patientData != null) {
                binding.name.text = patientData.name
                binding.age.text = Utility.processAge(patientData.dob)
                binding.height.text = patientData.height
                binding.weight.text = patientData.weight
                if (patientData.profilePhoto.isNotEmpty()) {
                    Picasso.get()
                        .load(patientData.profilePhoto)
                        .placeholder(R.drawable.account_profile)
                        .into(binding.profilePicture)
                } else {
                    binding.profilePicture.setImageResource(R.drawable.account_profile)
                }

            } else {
                // Handle case where user data is null
                Log.d("myTag", "User data not found")
            }
        }
    }
}