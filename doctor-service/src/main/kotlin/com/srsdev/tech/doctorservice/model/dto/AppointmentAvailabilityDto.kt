package com.srsdev.tech.doctorservice.model.dto

import java.time.LocalDate


data class AppointmentAvailabilityDto(
    var date: LocalDate,
    var startTime: String,
    var endTime: String,
    var clinic: ClinicForAppointmentAvailability,
    var avgVisitTime: Int // in minutes
)