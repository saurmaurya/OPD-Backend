package com.srsdev.tech.patientservice.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed

data class DoctorSpeciality(
    var id: String = ObjectId.get().toString(),
    var name: String
) {
    constructor(name: String) : this(id = ObjectId.get().toString(), name = name)
}
