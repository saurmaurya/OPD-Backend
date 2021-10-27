package com.srsdev.tech.appointmentservice.model

import com.srsdev.tech.appointmentservice.model.dto.DoctorForClinic
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Clinic(
    @Id
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    @DBRef
    var speciality: DoctorSpeciality? = null,
    var isActive: Boolean? = false,
    var doctor: DoctorForClinic? = null,
    var state: String? = null,
    var city: String? = null,
    var pincode: String? = null,
    var address: String? = null
)