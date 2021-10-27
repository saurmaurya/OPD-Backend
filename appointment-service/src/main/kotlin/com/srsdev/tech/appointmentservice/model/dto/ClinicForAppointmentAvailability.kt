package com.srsdev.tech.appointmentservice.model.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class ClinicForAppointmentAvailability(
    @Id
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var speciality: Speciality? = null,
    var doctor: Doctor? = null,
    var state: String? = null,
    var city: String? = null,
    var pincode: String? = null,
    var address: String? = null
) {
    data class Doctor(var id: String? = null, var name: String? = null)
    data class Speciality(var id: String? = null, var name: String? = null)
}
