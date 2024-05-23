package com.vcreate.healthok.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vcreate.healthok.databinding.FragmentWelcomeBinding
import com.vcreate.healthok.models.enum.UserType


class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        binding.welcomePatientButton.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment(UserType.USER)
            findNavController().navigate(action)
        }

        binding.welcomeDoctorButton.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment(UserType.DOCTOR)
            findNavController().navigate(action)
        }


        return binding.root
    }
}