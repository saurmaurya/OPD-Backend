package com.srsdev.tech.doctorservice.model

import com.srsdev.tech.doctorservice.model.enums.ERole
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "role")
data class Role(
    @Id
    var id: String? = null,
    var name: ERole
)