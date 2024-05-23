package com.vcreate.healthok.data.remote

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.vcreate.healthok.models.ApplicationUser
import com.vcreate.healthok.models.Appointment
import com.vcreate.healthok.models.Article
import com.vcreate.healthok.models.Doctor
import com.vcreate.healthok.models.Patient
import com.vcreate.healthok.utils.Validation


object FirebaseClient {


    // video call
    private val gson = Gson()
    private val dbRef = FirebaseDatabase.getInstance().getReference().child("VideoCall")
    private val currentUsername: String? = null
    private val LATEST_EVENT_FIELD_NAME = "latest_event"

    // database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    // difference line for database
    private val applicationUserReference: DatabaseReference = database.getReference("ApplicationUser")
    private val doctorReference = database.getReference("DoctorData")
    private val patientReference = database.getReference("PatientData")
    private val articleReference = database.getReference("Articles")
    val appointmentReference = database.getReference("Appointment")

    private val auth: FirebaseAuth = Firebase.auth

    private val storage: FirebaseStorage  = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = storage.reference
    private val profilePhotoReference = storageReference.child("Profile Photo")



    fun getUid() : String{
        val user = auth.currentUser
        if (user != null) {
            return user.uid
        }

        return ""
    }

    fun logout() {
        auth.signOut()
    }

    fun getAllTheDoctors(callback: (List<Doctor>) -> Unit) {
        val doctorsList = mutableListOf<Doctor>()
        doctorReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                doctorsList.clear()
                for (doctorSnapshot in snapshot.children) {
                    val doctor = doctorSnapshot.getValue(Doctor::class.java)
                    doctor?.let {
                        doctorsList.add(it)
                    }
                }
                callback(doctorsList)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase Database Error: ${error.message}")
                callback(emptyList())
            }
        })
    }

    fun getAllTheAppointments(userId: String, callback: (appointments: List<Appointment>) -> Unit) {
        val appointmentList = mutableListOf<Appointment>()
        val query: Query = appointmentReference.orderByChild("userId").equalTo(userId)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (appointmentSnapshot in snapshot.children) {
                        val appointment = appointmentSnapshot.getValue(Appointment::class.java)
                        appointment?.let {
                            appointmentList.add(it)
                        }
                    }
                    callback(appointmentList)
                } else {
                    // No appointments found for the userId
                    callback(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Error occurred while retrieving appointments
                callback(emptyList())
            }
        })
    }

    fun getAllTheAppointmentsForDoctor(userId: String, callback: (appointments: List<Appointment>) -> Unit) {
        val appointmentList = mutableListOf<Appointment>()
        val query: Query = appointmentReference.orderByChild("doctorId").equalTo(userId)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (appointmentSnapshot in snapshot.children) {
                        val appointment = appointmentSnapshot.getValue(Appointment::class.java)
                        appointment?.let {
                            appointmentList.add(it)
                        }
                    }
                    callback(appointmentList)
                } else {
                    // No appointments found for the userId
                    callback(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Error occurred while retrieving appointments
                callback(emptyList())
            }
        })
    }

    fun getAllTheArticles(callback: (List<Article>) -> Unit) {
       val articleList = mutableListOf<Article>()
        articleReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                articleList.clear()
                for (doctorSnapshot in snapshot.children) {
                    val doctor = doctorSnapshot.getValue(Article::class.java)
                    doctor?.let {
                        articleList.add(it)
                    }
                }
                callback(articleList)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase Database Error: ${error.message}")
                callback(emptyList())
            }

        })
    }

    fun uploadImageToFirebase(uid: String, imageUri: Uri, context: Context) {
        // Create a reference to the storage location where the image will be stored
        val imageRef = profilePhotoReference.child("$uid.jpg")

        // Upload the image file to Firebase Storage
        imageRef.putFile(imageUri)
            .addOnSuccessListener { uploadTask ->
                // Once the image upload is successful, retrieve the download URL
                uploadTask.storage.downloadUrl
                    .addOnSuccessListener { url ->
                        val imageUrl = url.toString()

                        // Save URL to ApplicationUser
                        applicationUserReference.child(uid).child("profileUrl").setValue(imageUrl)

                        // Save URL to DoctorData
                        doctorReference.child(uid).child("profilePhoto").setValue(imageUrl)

                        // Display success message
                        Toast.makeText(context, "Image stored successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        // Display error message if retrieving URL fails
                        Toast.makeText(context, "Failed to get image URL: $e", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { e ->
                // Display error message if image upload fails
                Toast.makeText(context, "Image upload failed: $e", Toast.LENGTH_SHORT).show()
            }
    }

    fun getPatientDataByUid(uid: String, callback: (patient: Patient?) -> Any)  {
        val query: Query = patientReference.orderByChild("userId").equalTo(uid)
        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapShot in snapshot.children) {
                        val patient = dataSnapShot.getValue(Patient::class.java)
                        callback(patient)
                        return
                    }
                } else {
                    callback(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }
    fun getArticleDataByUId(uid: Long, callback: (article: Article?) -> Any) {
        val query: Query = articleReference.orderByChild("articleId").startAt(uid.toDouble()).endAt(uid.toDouble() + 0.1)
        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapShot in snapshot.children) {
                        val article = dataSnapShot.getValue(Article::class.java)
                        callback(article)
                        return
                    }
                } else {
                    callback(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }
    fun uploadPatientDataUsingUid(uid: String, patient: Patient, view: View) {
        patientReference.child(uid).setValue(patient)
            .addOnSuccessListener {
                Log.d("myTag", "Doctor data uploaded successfully")
                Validation.showSnackbar(view, "Details Submitted Successfully")
                updateUserWithDoctorDataPatientData(uid, patient.email, patient.name)
            }
            .addOnFailureListener { e ->
                Validation.showSnackbar(view, "Error Uploading Data")
            }
    }

    fun getDoctorDataByUid(uid: String, callback: (doctor: Doctor?) -> Any) {
        val query: Query = doctorReference.orderByChild("doctorId").equalTo(uid)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snapshot in snapshot.children) {
                        val doctor = snapshot.getValue(Doctor::class.java)
                        callback(doctor)
                        return
                    }
                } else {
                    callback(null)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun uploadDoctorDataUsingUid(uid: String, doctor: Doctor, view:View) {
        doctorReference.child(uid).setValue(doctor)
            .addOnSuccessListener {
                Log.d("myTag", "Doctor data uploaded successfully")
                Validation.showSnackbar(view, "Details Submitted Successfully")
                updateUserWithDoctorDataPatientData(uid, doctor.email, doctor.doctorName)
            }
            .addOnFailureListener { e ->
                Validation.showSnackbar(view, "Error Uploading Data")
            }
    }

    fun getUserDataByUid(uid: String, callback: (userData: ApplicationUser?) -> Any) {
        val userQuery: Query = applicationUserReference.orderByChild("userId").equalTo(uid)

        userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val userData = snapshot.getValue(ApplicationUser::class.java)
                        callback(userData)
                        return
                    }
                } else {
                    callback(null)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(null)
            }
        })
    }

    private fun updateUserWithDoctorDataPatientData(uid: String, email: String, name: String) {
        applicationUserReference.child(uid).child("userType").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                applicationUserReference.child("userEmail").setValue(email)
                applicationUserReference.child("userName").setValue(name)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
                Log.d("myTag", "Error in uploading user data with doctor data $databaseError")
            }
        })
    }

    fun appointBook(appointment: Appointment) {
        // Generate a unique key for the appointment
        val appointmentKey = appointmentReference.push().key
        appointmentKey?.let { key ->
            // Set the appointment object to the corresponding reference with the generated key
            appointment.appointmentId = appointmentKey
            appointmentReference.child(key).setValue(appointment)
                .addOnSuccessListener {
                    // Appointment booked successfully
                    Log.d("FirebaseClient", "Appointment booked successfully")
                }
                .addOnFailureListener { e ->
                    // Error occurred while booking appointment
                    Log.e("FirebaseClient", "Error booking appointment: $e")
                }
        }
    }

}