package com.srsdev.tech.adminservice.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DBRef
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import com.fasterxml.jackson.annotation.JsonIgnore
import com.srsdev.tech.adminservice.model.enums.Gender
import java.time.LocalDateTime

@Document(collection = "admin")
data class Admin (
    @Id
    var id: String = ObjectId.get().toString(),
    var name: String,
    var dateOfBirth: LocalDateTime,
    var gender: Gender,
    var mobile: String,
    @DBRef
    @JsonIgnore
    var user: User,
)