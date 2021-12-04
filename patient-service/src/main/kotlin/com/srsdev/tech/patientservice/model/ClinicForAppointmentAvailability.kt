package com.srsdev.tech.patientservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class ClinicForAppointmentAvailability(
    @Id
    var id: String,
    var name: String,
    var description: String,
    var speciality: Speciality,
    var doctor: Doctor,
    var state: String,
    var city: String,
    var pincode: String,
    var address: String
) {
    data class Doctor(var id: String, var name: String)
    data class Speciality(var id: String, var name: String)
}
