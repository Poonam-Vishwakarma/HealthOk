package com.vcreate.healthok.models

data class Appointment(
    var appointmentId: String = "",
    val userId: String = "",
    val doctorId: String = "",
    val appointmentData: String = "",
    val appointmentTime: String = "",
)
