package com.vcreate.healthok.ui.authentication

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vcreate.healthok.R
import com.vcreate.healthok.databinding.FragmentLoginBinding
import com.vcreate.healthok.models.enum.UserType
import com.vcreate.healthok.utils.Validation


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private val TAG = "login"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        initData()
        clickListeners()

        binding.flTlEmail.editText?.onFocusChangeListener = View.OnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                binding.flTlEmail.error = null
            } else {
                Validation.isValidEmail(binding.flTlEmail)
            }
        }

        binding.flTlPassword.editText?.onFocusChangeListener = View.OnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                binding.flTlPassword.error = null
            } else {
                Validation.isValidName(binding.flTlPassword)
            }
        }


        return binding.root
    }

    private fun clickListeners() {

        // go to sign up
        binding.signupTv.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }

        // go to forget password
        binding.tvForgetPassword.setOnClickListener {
            findNavController().navigate(R.id.forgetPasswordFragment)
        }

        binding.loginButton.setOnClickListener {

            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)

            val emailTextInputLayout = binding.flTlEmail
            val passwordTextInputLayout = binding.flTlPassword

            val validEmail = Validation.isValidEmail(emailTextInputLayout)
            val validPassword = Validation.isValidPassword(passwordTextInputLayout)

            if (validEmail && validPassword) {
                passwordTextInputLayout.helperText = "Strong Password"

                Log.d(TAG, "Email and Name validated successfully")

                val email = emailTextInputLayout.editText?.text.toString()
                val password = passwordTextInputLayout.editText?.text.toString()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Complete listener : Login User Task Completed")
                        } else {
                            Log.d(TAG, "signInWithEmail:failure", task.exception)
                        }
                    }
                    .addOnSuccessListener {user ->
                        Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.homeActivity)
                        requireActivity().finish()
                    }
                    .addOnFailureListener {e ->
                        Log.d(TAG, "signInWithEmail:failure ${e.message}")
                        // Inside your fragment
                        val rootView = binding.root
                        val snackbar = Snackbar.make(rootView, e.message ?: "Unknown error", Snackbar.LENGTH_LONG)
                        snackbar.setAction("Dismiss") { /**/ }
                        snackbar.show()
                    }
            }
        }
    }

    private fun initData() {
        auth = Firebase.auth
    }


    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            findNavController().navigate(R.id.homeActivity)
            requireActivity().finish()
        }
    }
}