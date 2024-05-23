package com.vcreate.healthok.models

import com.example.healthok.data.model.Address
import com.example.healthok.data.model.Licence

data class Doctor(
    var doctorId: String = "",
    var doctorName: String = "",
    var doctorPhoneNumber: String = "",
    var email: String = "",
    var about: String = "",
    var dob: String = "",
    var gender: String = "",
    var height: String = "",
    var profilePhoto: String = "",
    var qualification: String = "",
    var rating: Double = 0.0,
    var speciality: String = "",
    var weight: String = "",
    var address: String = "",
    var available: Boolean = false,
    var experience: String = ""
)  {

}