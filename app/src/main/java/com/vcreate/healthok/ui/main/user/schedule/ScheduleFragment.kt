package com.vcreate.healthok.ui.main.user.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vcreate.healthok.R
import com.vcreate.healthok.adapter.DoctorAdapter
import com.vcreate.healthok.data.remote.FirebaseClient
import com.vcreate.healthok.databinding.FragmentScheduleBinding
import com.vcreate.healthok.ui.main.user.home.HomeFragmentDirections


class ScheduleFragment : Fragment(), DoctorAdapter.OnDoctorItemClick{

    private lateinit var binding: FragmentScheduleBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager

        FirebaseClient.getAllTheDoctors { doctorList ->
            val doctorAdapter = DoctorAdapter(data = doctorList, R.layout.item_layout_doctor_view, this)
            binding.recyclerView.adapter = doctorAdapter
        }



        return binding.root
    }


    override fun onDoctorClick(position: Int, uid: String) {
        val action = ScheduleFragmentDirections.actionScheduleFragmentToDoctorDetailFragment2(uid)
        findNavController().navigate(action)
    }
}