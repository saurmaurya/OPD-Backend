package com.srsdev.tech.adminservice.model.dto

import java.time.LocalDate

data class AdminDto(
    val username: String,
    val email: String,
    val password: String,
    val name: String,
    val dateOfBirth: LocalDate,
    val gender: String,
    val mobile: String,
    var secretKey: String
)