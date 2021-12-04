package com.srsdev.tech.patientservice.model

import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class AppointmentAvailability(
    @Id
    var id: String? = null,
    var date: LocalDateTime? = null,
    var startTime: LocalDateTime? = null,
    var endTime: LocalDateTime? = null,
    var clinic: ClinicForAppointmentAvailability? = null,
    var slots: MutableSet<LocalDateTime>? = null
)