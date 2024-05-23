package com.vcreate.healthok.models

import com.vcreate.healthok.models.enum.UserType

data class ApplicationUser (
    var userId: String = "",
    var userName: String = "",
    var userEmail: String = "",
    var password: String = "",
    var profileUrl: String = "",
    var userType: UserType = UserType.ADMIN
)
