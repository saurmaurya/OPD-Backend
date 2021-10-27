package com.srsdev.tech.doctorservice.model

import com.srsdev.tech.doctorservice.model.dto.DoctorForClinic
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "clinic")
data class Clinic(
    @Id
    var id: String? = null,
    var name: String,
    var description: String,
    var speciality: DoctorSpeciality,
    var isActive: Boolean? = false,
    var doctor: DoctorForClinic,
    var state: String,
    var city: String,
    var pincode: String,
    var address: String
)