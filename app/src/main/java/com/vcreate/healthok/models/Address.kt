package com.example.healthok.data.model

data class Address(
    val addressLine1: String,
    val addressLine2: String,
    val addressLine3: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String
) {
    override fun toString(): String {
        return "Address(addressLine1='$addressLine1', addressLine2='$addressLine2', addressLine3='$addressLine3', city='$city', state='$state', country='$country', postalCode='$postalCode')"
    }
}
