package com.vcreate.healthok.ui.authentication.forgetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vcreate.healthok.R
import com.vcreate.healthok.databinding.FragmentForgetPasswordCreatePasswordBinding


class ForgetPasswordCreatePasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordCreatePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetPasswordCreatePasswordBinding.inflate(inflater, container, false)

        binding.ffpuphBtnResetPassword.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }


        return binding.root
    }
}