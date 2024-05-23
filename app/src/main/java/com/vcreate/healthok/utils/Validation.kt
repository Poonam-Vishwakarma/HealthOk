package com.vcreate.healthok.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object Validation {

    private val emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    fun isValidEmail(textInputLayout: TextInputLayout): Boolean {
        val email = textInputLayout.editText?.text.toString().trim()
        val emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

        if (email.isEmpty()) {
            textInputLayout.error = "Email cannot be Empty"
            return false
        } else if (!email.matches(emailPattern)) {
            textInputLayout.error = "Enter a Valid Email"
            return false
        } else {
            textInputLayout.error = null // Clear any previous error
            return true
        }
    }

    fun isValidName(textInputLayout: TextInputLayout) : Boolean {
        val name = textInputLayout.editText?.text.toString().trim()

        if (name.isEmpty()) {
            textInputLayout.error = "Name cannot be Empty"
            return false
        } else {
            textInputLayout.error = null
            return true
        }
    }

    fun isValidPassword(textInputLayout: TextInputLayout): Boolean {
        val password = textInputLayout.editText?.text.toString().trim()
        val capRegex = ".*[A-Z].*".toRegex()
        val smallRegex = ".*[a-z].*".toRegex()
        val digitRegex = ".*[0-9].*".toRegex()
        val speCharRegex = ".*[^A-Za-z0-9].*".toRegex()

        if (password.isEmpty()) {
            textInputLayout.error = "Password cannot be empty"
            return false
        } else if (password.length < 6) { // Updated to match the condition specified in the message
            textInputLayout.error = "Password length must be at least 6 characters"
            return false
        } else if (!password.matches(capRegex)) {
            textInputLayout.error = "Password must contain at least one uppercase letter"
            return false
        } else if (!password.matches(smallRegex)) {
            textInputLayout.error = "Password must contain at least one lowercase letter"
            return false
        } else if (!password.matches(digitRegex)) {
            textInputLayout.error = "Password must contain at least one digit"
            return false
        } else if (!password.matches(speCharRegex)) {
            textInputLayout.error = "Password must contain at least one special character"
            return false
        } else {
            textInputLayout.error = null
            return true
        }
    }

    fun showSnackbar(view: View, message: String, actionText: String? = null, action: (() -> Unit)? = null) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

}
