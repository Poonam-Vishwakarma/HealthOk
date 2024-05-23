package com.vcreate.healthok.ui.main.user.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vcreate.healthok.R
import com.vcreate.healthok.databinding.FragmentCreateAppointmentBinding

class CreateAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentCreateAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAppointmentBinding.inflate(inflater, container, false)


        return binding.root
    }
}