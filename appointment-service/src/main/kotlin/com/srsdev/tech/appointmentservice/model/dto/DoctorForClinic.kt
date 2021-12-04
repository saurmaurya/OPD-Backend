package com.srsdev.tech.appointmentservice.model.dto

import org.bson.types.ObjectId

class DoctorForClinic(
    var id: String = ObjectId.get().toString(),
    var name: String = ""
)