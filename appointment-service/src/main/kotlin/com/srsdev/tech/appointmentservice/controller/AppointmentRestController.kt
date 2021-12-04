package com.srsdev.tech.appointmentservice.controller

import com.srsdev.tech.appointmentservice.client.DoctorClient
import com.srsdev.tech.appointmentservice.client.PatientClient
import com.srsdev.tech.appointmentservice.exception.InvalidRequestException
import com.srsdev.tech.appointmentservice.model.Appointment
import com.srsdev.tech.appointmentservice.model.AppointmentAvailability
import com.srsdev.tech.appointmentservice.model.Clinic
import com.srsdev.tech.appointmentservice.model.dto.AppointmentAvailabilityDto
import com.srsdev.tech.appointmentservice.service.AppointmentService
import com.srsdev.tech.appointmentservice.utils.CustomUtils
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RestController
@RequestMapping("/api/appointment")
class AppointmentRestController(
    private val aptAvlService: AppointmentService,
    private val doctorClient: DoctorClient,
    private val patientClient: PatientClient
) {
    @PostMapping("/availability")
    fun addAvailability(@RequestBody aptAvlDto: AppointmentAvailabilityDto): AppointmentAvailability {
        return aptAvlService.addAppointmentAvailability(aptAvlDto)
    }

    @GetMapping("/availability/{clinicId}")
    fun getAvailability(
        @PathVariable(required = false) clinicId: String,
        @RequestParam(required = false) date: String?
    ): List<AppointmentAvailability> {
        if (date == null) {
            return aptAvlService.getAptAvlByClinic(clinicId)
        }
        val validDate = LocalDateTime.of(LocalDate.parse(date), LocalTime.of(0, 0, 0, 0))
        return listOf(aptAvlService.getAptAvlByClinicAndDate(clinicId, validDate))
    }

    @GetMapping("/clinics")
    @ResponseStatus(HttpStatus.OK)
    fun getAllClinics(
        @RequestParam(required = false) state: String?,
        @RequestParam(required = false) city: String?
    ): List<Clinic> {
        return aptAvlService.getAllClinicForPatient(state, city)
    }

    @PostMapping("/{aptAvlId}")
    fun bookAppointment(
        @PathVariable aptAvlId: String,
        @RequestParam slotTime: String
    ): Appointment {
        val aptAvl = aptAvlService.getAptAvlById(aptAvlId)
        val patient = patientClient.getPatient()
        return aptAvlService.bookAppointment(aptAvl, patient, LocalDateTime.parse(slotTime))
    }

    @GetMapping("/patient")
    fun getAppointmentsForPatient(
        @RequestParam fromDate: String?,
        @RequestParam toDate: String?
    ): List<Appointment> {
        return aptAvlService.getAppointmentsForPatient(patientClient.getPatient().id, fromDate, toDate)
    }

    @PutMapping("/patient/{id}")
    fun updateAppointmentForPatient(
        @PathVariable id: String, @RequestParam slotTime: String,
        @RequestParam aptAvlId: String
    ): Appointment {
        val appointment = aptAvlService.getAppointmentById(id)
        if (appointment.patient.id != patientClient.getPatient().id)
            throw InvalidRequestException("Error: You are not authorized to update this appointment")
        val oldAptAvl = aptAvlService.getAptAvlByClinicAndDate(appointment.clinic.id, appointment.date)
        val newAptAvl = aptAvlService.getAptAvlById(aptAvlId)
        return aptAvlService.updateAppointmentForPatient(
            oldAptAvl,
            newAptAvl,
            appointment,
            LocalDateTime.parse(slotTime)
        )
    }

    @DeleteMapping("/patient/{id}")
    fun deleteAppointmentForPatient(@PathVariable id: String): Boolean {
        val appointment = aptAvlService.getAppointmentById(id)
        if (appointment.patient.id != patientClient.getPatient().id)
            throw InvalidRequestException("Error: You are not authorized to delete this appointment")
        val oldAptAvl = aptAvlService.getAptAvlByClinicAndDate(appointment.clinic.id, appointment.date)
        return aptAvlService.deleteAppointmentForPatient(oldAptAvl, appointment)
    }

    @GetMapping("/doctor")
    fun getAppointmentsForDoctor(
        @RequestParam fromDate: String?,
        @RequestParam toDate: String?
    ): List<Appointment> {
        return aptAvlService.getAppointmentsForPatient(doctorClient.getDoctor().id, fromDate, toDate)
    }

    @PutMapping("/doctor/{id}")
    fun updateAppointmentStatus(@PathVariable id: String, @RequestParam status: String) {
        val appointment = aptAvlService.getAppointmentById(id)
        if (appointment.clinic.doctor.id != doctorClient.getDoctor().id)
            throw InvalidRequestException("Error: You are not authorized to update this appointment")
        val statusMapped = CustomUtils.statusMapper(status)
        aptAvlService.updateAppointmentStatus(appointment, statusMapped)
    }
}