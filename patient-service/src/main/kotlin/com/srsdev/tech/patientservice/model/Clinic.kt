package com.srsdev.tech.patientservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "clinic")
data class Clinic(
    @Id
    var id: String? = null,
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