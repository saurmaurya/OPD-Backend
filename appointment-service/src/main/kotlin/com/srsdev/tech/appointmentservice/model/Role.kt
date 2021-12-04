package com.srsdev.tech.appointmentservice.model

import com.srsdev.tech.appointmentservice.model.enums.ERole
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "role")
data class Role(
    @Id
    var id: String=ObjectId.get().toString(),
    var name: ERole
)