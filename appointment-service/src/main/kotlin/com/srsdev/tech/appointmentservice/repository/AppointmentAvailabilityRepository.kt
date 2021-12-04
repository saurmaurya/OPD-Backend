package com.srsdev.tech.appointmentservice.repository

import com.srsdev.tech.appointmentservice.model.AppointmentAvailability
import com.srsdev.tech.appointmentservice.model.dto.ClinicForAppointmentAvailability
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface AppointmentAvailabilityRepository: MongoRepository<AppointmentAvailability, String>{
    fun existsAppointmentAvailabilityByClinic_IdAndDate(clinicId: String, date: LocalDateTime): Boolean
    fun findAppointmentAvailabilitiesByClinic_IdAndDateAfter(clinicId: String, date: LocalDateTime): List<AppointmentAvailability>
    fun findAppointmentAvailabilitiesByClinic_IdAndDate(clinicId: String, date: LocalDateTime): AppointmentAvailability
}