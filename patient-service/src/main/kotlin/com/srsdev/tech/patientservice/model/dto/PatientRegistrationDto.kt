package com.srsdev.tech.patientservice.model.dto

import java.time.LocalDate

class PatientRegistrationDto(
    var username: String,
    var email: String,
    var password: String,
    var name: String,
    var dateOfBirth: LocalDate,
    var gender: String,
    var mobile: String,
    var identity: Map<String, String>
)