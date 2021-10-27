package com.srsdev.tech.adminservice.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "doctorSpeciality")
data class DoctorSpeciality(
    @Id
    var id: String = ObjectId.get().toString(),
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    var name: String
) {
    constructor(name: String) : this(id = ObjectId.get().toString(), name = name)
}