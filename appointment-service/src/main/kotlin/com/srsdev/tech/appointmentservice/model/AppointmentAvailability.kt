package com.srsdev.tech.appointmentservice.model

import com.srsdev.tech.appointmentservice.model.dto.ClinicForAppointmentAvailability
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "appointmentAvailability")
data class AppointmentAvailability(
    @Id
    var id: String=ObjectId.get().toString(),
    var date: LocalDateTime,
    var startTime: LocalDateTime,
    var endTime: LocalDateTime,
    var clinic: ClinicForAppointmentAvailability,
    var slots: MutableSet<LocalDateTime>
)