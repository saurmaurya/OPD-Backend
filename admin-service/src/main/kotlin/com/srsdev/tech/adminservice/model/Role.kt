package com.srsdev.tech.adminservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "role")
data class Role(
    @Id
    var id: String? = null,
    var name: ERole
)