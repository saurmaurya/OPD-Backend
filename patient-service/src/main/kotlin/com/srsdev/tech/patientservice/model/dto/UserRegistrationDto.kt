package com.srsdev.tech.patientservice.model.dto

data class UserRegistrationDto(
    var username: String,
    var email: String,
    var password: String,
    var roles: Set<String>
)