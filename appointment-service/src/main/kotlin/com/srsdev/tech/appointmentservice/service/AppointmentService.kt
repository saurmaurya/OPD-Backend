package com.srsdev.tech.appointmentservice.service

import com.srsdev.tech.appointmentservice.model.Appointment
import com.srsdev.tech.appointmentservice.model.AppointmentAvailability
import com.srsdev.tech.appointmentservice.model.Clinic
import com.srsdev.tech.appointmentservice.model.Patient
import com.srsdev.tech.appointmentservice.model.dto.AppointmentAvailabilityDto
import com.srsdev.tech.appointmentservice.model.enums.Status
import java.time.LocalDateTime

interface AppointmentService {
    fun addAppointmentAvailability(aptAvl: AppointmentAvailabilityDto): AppointmentAvailability
    fun getAptAvlById(id: String): AppointmentAvailability
    fun getAptAvlByClinic(clinicId: String): List<AppointmentAvailability>
    fun getAptAvlByClinicAndDate(clinicId: String, date: LocalDateTime): AppointmentAvailability

    fun getAllClinicForPatient(state: String?,city: String?): List<Clinic>

    fun bookAppointment(aptAvl: AppointmentAvailability, patient: Patient, slotTime: LocalDateTime): Appointment
    fun getAppointmentById(id: String): Appointment
    fun updateAppointmentForPatient(oldAptAvl: AppointmentAvailability, newAptAvl: AppointmentAvailability, appointment: Appointment, slotTime: LocalDateTime): Appointment
    fun deleteAppointmentForPatient(oldAptAvl: AppointmentAvailability, appointment: Appointment): Boolean
    fun updateAppointmentStatus(appointment: Appointment, status: Status): Appointment
    fun getAppointmentsForPatient(patientId: String, fromDate: String?, toDate: String?): List<Appointment>
    fun getAppointmentsForDoctor(doctorId: String, fromDate: String?, toDate: String?): List<Appointment>
}