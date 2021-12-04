package com.srsdev.tech.appointmentservice.model.dto

import com.srsdev.tech.appointmentservice.model.enums.Gender
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class PatientForAppointment(
    var id: String= ObjectId.get().toString(),
    var name: String,
    var gender: Gender,
    var dateOfBirth: LocalDateTime,
    var mobile: String
)