package com.srsdev.tech.appointmentservice.model

import com.srsdev.tech.appointmentservice.model.dto.DoctorForClinic
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Clinic(
    @Id
    var id: String=ObjectId.get().toString(),
    var name: String,
    var description: String,
    var speciality: DoctorSpeciality,
    var isActive: Boolean,
    var doctor: DoctorForClinic,
    var state: String,
    var city: String,
    var pincode: String,
    var address: String
)