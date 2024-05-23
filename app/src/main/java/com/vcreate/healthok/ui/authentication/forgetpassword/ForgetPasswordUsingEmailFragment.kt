package com.vcreate.healthok.ui.authentication.forgetpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vcreate.healthok.databinding.FragmentForgetPasswordUsingEmailBinding
import com.vcreate.healthok.utils.Validation

class ForgetPasswordUsingEmailFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordUsingEmailBinding
    private val TAG = "fpmail"
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetPasswordUsingEmailBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        binding.ffpupeTlEmail.editText?.onFocusChangeListener = View.OnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                binding.ffpupeTlEmail.error = null
            } else {
                Validation.isValidEmail(binding.ffpupeTlEmail)
            }
        }

        binding.ffpupeBtnResetPassword.setOnClickListener {
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)

            val emailTextInputLayout = binding.ffpupeTlEmail
            val validEmail = Validation.isValidEmail(emailTextInputLayout)

            if (validEmail) {
                Log.d(TAG, "Email Validated successfully")

                val email = emailTextInputLayout.editText?.text.toString()

                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Complete listener : Email Sending Task Completed")
                            val rootView = binding.root
                            val snackbar = Snackbar.make(rootView, "Email Sent", Snackbar.LENGTH_LONG)
                            snackbar.setAction("Open Email App") {
                                // Create an Intent to open the email app
                                val emailIntent = Intent(Intent.ACTION_MAIN).apply {
                                    addCategory(Intent.CATEGORY_APP_EMAIL)
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                }

                                // Check if there's any email app available to handle the Intent
                                if (emailIntent.resolveActivity(requireContext().packageManager) != null) {
                                    startActivity(emailIntent)
                                } else {
                                    Toast.makeText(requireContext(), "No email app found", Toast.LENGTH_SHORT).show()
                                }
                            }
                            snackbar.show()
                        } else {
                            Log.d(TAG, "Sending Password Reset Email:failure", task.exception)
                        }
                    }.addOnSuccessListener { user ->

                    }.addOnFailureListener { e ->
                        Log.d(TAG, "Sending Password Reset Email:failure ${e.message}")
                        val rootView = binding.root
                        val snackbar = Snackbar.make(rootView, e.message ?: "Unknown error", Snackbar.LENGTH_LONG)
                        snackbar.setAction("Dismiss") { /**/ }
                        snackbar.show()
                    }

                auth.fetchSignInMethodsForEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val result = task.result
                            if (result != null && result.signInMethods != null && result.signInMethods!!.isNotEmpty()) {
                                // Email exists, user has at least one sign-in method
                                Log.d(TAG, "User with email $email exists")
                            } else {
                                // Email does not exist or user has no sign-in methods
                                Log.d(TAG, "User with email $email does not exist")
                            }
                        } else {
                            // Error occurred while checking
                            Log.d(TAG, "Error checking user existence: ${task.exception}")
                        }
                    }

            }

        }

        return binding.root
    }



}