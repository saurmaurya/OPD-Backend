package com.srsdev.tech.doctorservice.model.dto

import com.srsdev.tech.doctorservice.model.DoctorSpeciality

data class ClinicRegisterDto(
    var name: String,
    var description: String,
    var speciality: DoctorSpeciality,
    var state: String,
    var city: String,
    var pincode: String,
    var address: String
)