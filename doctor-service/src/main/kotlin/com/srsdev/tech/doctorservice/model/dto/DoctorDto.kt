package com.srsdev.tech.doctorservice.model.dto

import java.time.LocalDate

data class DoctorDto(
    val username: String,
    val email: String,
    val password: String,
    val name: String,
    val dateOfBirth: LocalDate,
    val gender: String,
    val specialityId: String,
    val licenceNo: String,
    val identity: Map<String, String>,
    val mobile: String,
)