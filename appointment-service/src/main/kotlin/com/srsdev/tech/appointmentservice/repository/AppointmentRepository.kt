package com.srsdev.tech.appointmentservice.repository

import com.srsdev.tech.appointmentservice.model.Appointment
import com.srsdev.tech.appointmentservice.model.Patient
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface AppointmentRepository: MongoRepository<Appointment, String> {
    fun existsByPatient_IdAndClinic_IdAndDate(patientId: String, clinicId: String, date: LocalDateTime): Boolean
    fun findAppointmentsByPatient_Id(patientId: String): List<Appointment>
    fun findAppointmentsByPatient_IdAndDateAfter(patientId: String, fromDate: LocalDateTime): List<Appointment>
    fun findAppointmentsByPatient_IdAndDateAfterAndDateBefore(patientId: String, fromDate: LocalDateTime, toDate: LocalDateTime): List<Appointment>
    fun findAppointmentsByClinic_Doctor_Id(doctorId: String): List<Appointment>
    fun findAppointmentsByClinic_Doctor_IdAndDateAfter(doctorId: String, fromDate: LocalDateTime): List<Appointment>
    fun findAppointmentsByClinic_Doctor_IdAndDateAfterAndDateBefore(doctorId: String, fromDate: LocalDateTime, toDate: LocalDateTime): List<Appointment>
    fun findAppointmentsByAndClinic_Id(clinicId: String): List<Appointment>
    fun findAppointmentsByClinic_IdAndDateAfter(clinicId: String, date: LocalDateTime): List<Appointment>
}