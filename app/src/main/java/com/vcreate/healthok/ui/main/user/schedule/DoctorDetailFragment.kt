package com.vcreate.healthok.ui.main.user.schedule

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.vcreate.healthok.R
import com.vcreate.healthok.data.remote.FirebaseClient
import com.vcreate.healthok.databinding.FragmentDoctorDetailBinding
import com.vcreate.healthok.utils.BookingDialog

class DoctorDetailFragment : Fragment() {

    private lateinit var binding: FragmentDoctorDetailBinding
    private var doctorID = ""
    private var userID = ""
    private val args: DoctorDetailFragmentArgs by navArgs()

    private lateinit var bookingDialog: BookingDialog
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDoctorDetailBinding.inflate(inflater, container, false)
        doctorID = args.UID
        userID = FirebaseClient.getUid()
        bookingDialog = BookingDialog(R.layout.dialog_appointment_booking, binding, userID, doctorID)
        loadData()



        binding.button.setOnClickListener {
            bookingDialog.show(requireActivity().supportFragmentManager, "com.vcreate.healthok.utils.BookingDialog")
        }

        return binding.root
    }

    private fun loadData() {
        FirebaseClient.getDoctorDataByUid(doctorID) { doctorData ->
            if (doctorData != null) {
                binding.name.text = doctorData.doctorName
                binding.location.text = doctorData.address
                binding.specialist.text = doctorData.speciality
                binding.experience.text = doctorData.experience
                binding.about.text= doctorData.about

                if (doctorData.profilePhoto.isNotEmpty()) {
                    Picasso.get()
                        .load(doctorData.profilePhoto)
                        .placeholder(R.drawable.account_profile)
                        .into(binding.imageView)
                } else {
                    binding.imageView.setImageResource(R.drawable.account_profile)
                }
            } else {
                Log.d("myTag", "User data not found")
            }
        }
    }


}