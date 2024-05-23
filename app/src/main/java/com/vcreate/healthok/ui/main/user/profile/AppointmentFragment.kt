 package com.vcreate.healthok.ui.main.user.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.vcreate.healthok.adapter.AppointmentAdapter
import com.vcreate.healthok.data.remote.FirebaseClient
import com.vcreate.healthok.databinding.FragmentAppointmentBinding
import com.vcreate.healthok.models.Appointment


 class AppointmentFragment : Fragment(), AppointmentAdapter.OnAppointmentItemClick{

    private lateinit var binding: FragmentAppointmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)


        val userId = FirebaseClient.getUid()
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.fragmentAppointmentRecyclerView.layoutManager = layoutManager

        FirebaseClient.getAllTheAppointments(userId) {appointments: List<Appointment> ->
            val appointmentAdapter = AppointmentAdapter(requireContext(), data = appointments, this)
            binding.fragmentAppointmentRecyclerView.adapter = appointmentAdapter
        }

        return binding.root
    }

     override fun onClick(position: Int, uid: String) {
     }


 }