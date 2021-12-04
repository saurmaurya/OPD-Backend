package com.srsdev.tech.appointmentservice.model.dto

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class ClinicForAppointmentAvailability(
    @Id
    var id: String = ObjectId.get().toString(),
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
