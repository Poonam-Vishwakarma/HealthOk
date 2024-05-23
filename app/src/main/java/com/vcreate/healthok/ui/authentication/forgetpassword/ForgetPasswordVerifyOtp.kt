package com.vcreate.healthok.ui.authentication.forgetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vcreate.healthok.R
import com.vcreate.healthok.databinding.FragmentForgetPasswordVerifyOtpBinding

class ForgetPasswordVerifyOtp : Fragment() {

    private lateinit var binding: FragmentForgetPasswordVerifyOtpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetPasswordVerifyOtpBinding.inflate(inflater, container, false)

        binding.ffpupeBtnVerifyOtp.setOnClickListener {
        }

        return binding.root
    }

}