package com.srsdev.tech.doctorservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.srsdev.tech.doctorservice.model.enums.Gender
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "doctor")
data class Doctor(
    @Id val id: String = ObjectId.get().toString(),
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    var name: String,
    var dateOfBirth: LocalDateTime,
    var gender: Gender,
    @DBRef
    var speciality: DoctorSpeciality,
    var licenceNo: String,
    var identity: Map<String, String>,
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    var mobile: String,
    @DBRef
    @JsonIgnore
    var user: User,
)
