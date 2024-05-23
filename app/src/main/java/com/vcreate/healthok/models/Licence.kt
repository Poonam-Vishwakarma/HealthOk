package com.example.healthok.data.model

import com.example.healthok.data.model.Address
import java.util.Date

data class Licence(
    val licenceNo: String,
    val dateOfIssue: Date,
    val dateOfExpire: Date,
    val issuingAuthority: String,
    val addressOfIssuingAuthority: Address
) {

    override fun toString(): String {
        return "Licence(licenceNo='$licenceNo', dateOfIssue=$dateOfIssue, dateOfExpire=$dateOfExpire, issuingAuthority='$issuingAuthority', addressOfIssuingAuthority=$addressOfIssuingAuthority)"
    }
}
