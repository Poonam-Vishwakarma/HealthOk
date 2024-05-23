package com.vcreate.healthok.ui.main.user;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.*
import com.vcreate.healthok.data.remote.FirebaseClient
import com.vcreate.healthok.databinding.ActivityHomeBinding
import com.vcreate.healthok.models.ApplicationUser
import com.vcreate.healthok.models.enum.UserType

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val usersReference: DatabaseReference = database.getReference("ApplicationUser")
    private val auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the NavHostFragment and get its NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(com.vcreate.healthok.R.id.main_activity_nav_host) as NavHostFragment
        navController = navHostFragment.navController

        val navView: BottomNavigationView = binding.bottomNavView

        // Set item icon tint list to null (optional, to disable default icon tinting)
        navView.itemIconTintList = null

        // Set up the BottomNavigationView with the NavController for navigation
        navView.setupWithNavController(navController)

        // Fetch user data from Firebase and update bottom navigation menu based on user type
        fetchAndSetUserType()
    }

    private fun fetchAndSetUserType() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            FirebaseClient.getUserDataByUid(uid) { userData ->
                userData?.let { user: ApplicationUser->
                    // User data found, determine user type and update bottom navigation menu
                    updateBottomNavigationMenu(user.userType)
                } ?: run {
                    // No user data found
                    Log.d("myTag", "User data not found")
                }
            }
        } else {
            Log.d("myTag", "Current user is not authenticated or UID is null")
        }
    }

    private fun updateBottomNavigationMenu(userType: UserType) {
        val navView: BottomNavigationView = binding.bottomNavView

        // Determine which menu resource to inflate based on user type
        val menuResId = when (userType) {
            UserType.USER -> com.vcreate.healthok.R.menu.menu_bottom_nav
            UserType.DOCTOR -> com.vcreate.healthok.R.menu.menu_doctor_nav
            UserType.ADMIN -> com.vcreate.healthok.R.menu.menu_bottom_nav
        }

        // Inflate the selected menu resource
        navView.menu.clear()
        navView.inflateMenu(menuResId)
    }

}
