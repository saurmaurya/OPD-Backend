package com.srsdev.tech.doctorservice.model

import com.srsdev.tech.doctorservice.model.dto.ClinicForAppointmentAvailability
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "appointmentAvailability")
data class AppointmentAvailability(
    @Id
    var id: String? = null,
    var date: LocalDateTime? = null,
    var startTime: LocalDateTime? = null,
    var endTime: LocalDateTime? = null,
    var clinic: ClinicForAppointmentAvailability? = null,
    var slots: MutableSet<LocalDateTime>? = null
)