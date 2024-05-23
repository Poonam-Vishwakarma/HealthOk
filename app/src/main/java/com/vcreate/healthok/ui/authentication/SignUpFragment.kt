package com.vcreate.healthok.ui.authentication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vcreate.healthok.R
import com.vcreate.healthok.databinding.FragmentSignUpBinding
import com.vcreate.healthok.models.ApplicationUser
import com.vcreate.healthok.models.enum.UserType
import com.vcreate.healthok.utils.Validation


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private val TAG = "signup"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        initData()
        clickListeners()

        binding.fsupTlEmail.editText?.onFocusChangeListener = View.OnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                binding.fsupTlEmail.error = null
            } else {
                Validation.isValidEmail(binding.fsupTlEmail)
            }
        }

        binding.fsupTlName.editText?.onFocusChangeListener = View.OnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                binding.fsupTlName.error = null
            } else {
                Validation.isValidName(binding.fsupTlName)
            }
        }

        binding.fsupTlPassword.editText?.onFocusChangeListener = View.OnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                binding.fsupTlPassword.error = null
            } else {
                Validation.isValidPassword(binding.fsupTlPassword)
            }
        }

        return binding.root
    }

    private fun initData() {
        auth = Firebase.auth
    }

    private fun clickListeners() {
        // sign up
        binding.fsupBtnSignUp.setOnClickListener {
            val emailTextInputLayout = binding.fsupTlEmail
            val nameTextInputLayout = binding.fsupTlName
            val passwordTextInputLayout = binding.fsupTlPassword

            val validEmail = Validation.isValidEmail(emailTextInputLayout)
            val validName = Validation.isValidName(nameTextInputLayout)
            val validPassword = Validation.isValidPassword(passwordTextInputLayout)

            if (validEmail && validName && validPassword) {
                val userType = when {
                    binding.doctorCheckBox.isChecked -> UserType.DOCTOR
                    binding.patientCheckBox.isChecked -> UserType.USER
                    else -> UserType.ADMIN
                }

                userType.let {
                    var user = ApplicationUser(
                        "",
                        nameTextInputLayout.editText?.text.toString(),
                        emailTextInputLayout.editText?.text.toString(),
                        passwordTextInputLayout.editText?.text.toString(),
                        "",
                        it
                    )
                    auth.createUserWithEmailAndPassword(user.userEmail, user.password)
                        .addOnCompleteListener {task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "Complete listener : Create User Task Completed")
                            } else {
                                Log.d(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(
                                    requireContext(),
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }.addOnFailureListener {exception ->
                            Log.d(TAG, "Failure Listener : Face Error in Creating User")
                            Log.d(TAG, exception.toString())
                        }.addOnSuccessListener { createdUser ->
                            Log.d(TAG, "Success Listener : User Created")
                            val cUser = createdUser.user
                            val userId = cUser?.uid
                            if (userId != null) {
                                user.userId = userId
                            }

                            val database = Firebase.database
                            val myRef = database.getReference("ApplicationUser").child(user.userId)
                            myRef.setValue(user)
                                .addOnCompleteListener {task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "Complete listener : Send Data to Dabase completed")
                                    }
                                }.addOnSuccessListener {
                                    Log.d(TAG, "Success Listener : Data Send")
                                    Toast.makeText(requireContext(), "User Created Successfully", Toast.LENGTH_LONG).show()
                                    findNavController().navigate(R.id.homeActivity)
                                    requireActivity().finish()
                                }.addOnFailureListener { e ->
                                    Log.d(TAG, "Failure Listener : Face Error in Sending Data to Database")
                                    Log.d(TAG, e.toString())
                                }
                        }
                } ?: run {
                    // Handle case where no user type is selected
                    Toast.makeText(requireContext(), "Please select a user type", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // navigate to login
        binding.loginTv.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }
}