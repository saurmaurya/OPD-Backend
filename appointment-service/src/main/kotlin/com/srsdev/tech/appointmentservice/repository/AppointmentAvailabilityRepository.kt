package com.srsdev.tech.appointmentservice.repository

import com.srsdev.tech.appointmentservice.model.AppointmentAvailability
import com.srsdev.tech.appointmentservice.model.dto.ClinicForAppointmentAvailability
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface AppointmentAvailabilityRepository: MongoRepository<AppointmentAvailability, String>{
    fun findAppointmentAvailabilitiesByClinicAndDate(clinic: ClinicForAppointmentAvailability, date: LocalDateTime): Collection<AppointmentAvailability>
    fun findAppointmentAvailabilitiesByClinic(clinic: ClinicForAppointmentAvailability): Collection<AppointmentAvailability>
    fun existsAppointmentAvailabilityByClinicAndDate(clinic: ClinicForAppointmentAvailability, date: LocalDateTime): Boolean
}