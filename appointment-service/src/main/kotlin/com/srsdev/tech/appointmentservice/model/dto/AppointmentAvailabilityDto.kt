package com.srsdev.tech.appointmentservice.model.dto

import com.srsdev.tech.appointmentservice.model.Clinic
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


data class AppointmentAvailabilityDto(
    var date: LocalDateTime,
    var startTime: LocalDateTime,
    var endTime: LocalDateTime,
    var clinic:ClinicForAppointmentAvailability,
    var avgVisitTime: Int // in minutes
)