package com.srsdev.tech.doctorservice.model.dto

import java.time.LocalDateTime
import java.time.LocalTime


data class AppointmentAvailabilityDto(
    var date: LocalDateTime,
    var startTime: LocalDateTime,
    var endTime: LocalDateTime,
    var clinic: ClinicForAppointmentAvailability,
    var avgVisitTime: Int // in minutes
)