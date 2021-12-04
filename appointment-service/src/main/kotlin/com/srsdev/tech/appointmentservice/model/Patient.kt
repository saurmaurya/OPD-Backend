package com.srsdev.tech.appointmentservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.srsdev.tech.appointmentservice.model.enums.Gender
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import java.time.LocalDateTime

class Patient(
    @Id
    var id: String = ObjectId.get().toString(),
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    var name: String,
    var gender: Gender,
    var dateOfBirth: LocalDateTime,
    var mobile: String,
    var identity: Map<String, String>,
)