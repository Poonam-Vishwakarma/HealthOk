package com.vcreate.healthok.ui.authentication.forgetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vcreate.healthok.R
import com.vcreate.healthok.databinding.FragmentForgetPasswordUsingPhoneBinding
import com.vcreate.healthok.utils.Validation

class ForgetPasswordUsingPhoneFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordUsingPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetPasswordUsingPhoneBinding.inflate(inflater, container, false)

        binding.ffpuphTlPhoneNumber.editText?.onFocusChangeListener = View.OnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                binding.ffpuphTlPhoneNumber.error = null
            } else {
                Validation.isValidName(binding.ffpuphTlPhoneNumber)
            }
        }

        binding.ffpuphBtnResetPassword.setOnClickListener {

            val phoneTextInputLayout = binding.ffpuphTlPhoneNumber
            val validPhone = Validation.isValidName(phoneTextInputLayout)

            if (validPhone) {
                findNavController().navigate(R.id.forgetPasswordCreatePasswordFragment)
            }

        }

        return binding.root
    }

}