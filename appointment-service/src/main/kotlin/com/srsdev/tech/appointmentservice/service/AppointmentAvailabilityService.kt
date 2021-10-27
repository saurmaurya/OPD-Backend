package com.srsdev.tech.appointmentservice.service

import com.srsdev.tech.appointmentservice.model.AppointmentAvailability
import com.srsdev.tech.appointmentservice.model.dto.AppointmentAvailabilityDto
import com.srsdev.tech.appointmentservice.model.dto.ClinicForAppointmentAvailability
import java.time.LocalDateTime

interface AppointmentAvailabilityService {
    fun addAppointmentAvailability(aptAvl: AppointmentAvailabilityDto): AppointmentAvailability
    fun getAptAvlById(id: String): AppointmentAvailability
    fun getAptAvlByClinic(clinic: ClinicForAppointmentAvailability): Collection<AppointmentAvailability>
    fun getAptAvlByClinicAndDate(clinic: ClinicForAppointmentAvailability, date: LocalDateTime): Collection<AppointmentAvailability>
}