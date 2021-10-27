package com.srsdev.tech.appointmentservice.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed

data class DoctorSpeciality(
    @Id
    var id: String = ObjectId.get().toString(),
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    var name: String
) {
    constructor(name: String) : this(id = ObjectId.get().toString(), name = name)
}
