package com.srsdev.tech.patientservice.controller

import com.srsdev.tech.patientservice.client.AppointmentClient
import com.srsdev.tech.patientservice.client.AuthClient
import com.srsdev.tech.patientservice.model.*
import com.srsdev.tech.patientservice.model.dto.PatientRegistrationDto
import com.srsdev.tech.patientservice.model.dto.UserRegistrationDto
import com.srsdev.tech.patientservice.payload.request.LoginRequest
import com.srsdev.tech.patientservice.payload.response.JwtResponse
import com.srsdev.tech.patientservice.service.PatientService
import com.srsdev.tech.patientservice.utils.CustomUtils
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@CrossOrigin
@RestController
@RequestMapping("/api/patient")
class PatientRestController(
    private val authClient: AuthClient,
    private val appointmentClient: AppointmentClient,
    private val patientService: PatientService
) {
    @GetMapping("/welcome")
    @ResponseStatus(HttpStatus.OK)
    fun welcome(): String {
        return "Welcome to patient microservice."
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user")
    fun getUser(): User {
        return authClient.getUser()
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getPatient(): Patient {
        val user = getUser()
        return patientService.getPatientByUser(user.id!!)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addPatient(@RequestBody body: PatientRegistrationDto): Patient {
        val userData = UserRegistrationDto(body.username, body.email, body.password, setOf("patient"))
        val user = authClient.register(userData)
        val dateOfBirth = LocalDateTime.of(body.dateOfBirth, LocalTime.of(0, 0, 0, 0))
        val patient = Patient(
            name = body.name, gender = CustomUtils.genderMapper(body.gender), dateOfBirth = dateOfBirth,
            mobile = body.mobile, identity = body.identity, user = user
        )
        return patientService.addPatient(patient)
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody loginRequest: LoginRequest): JwtResponse {
        return authClient.login(loginRequest)
    }

    @GetMapping("/clinics")
    fun getAllClinics(
        @RequestParam(required = false) state: String?,
        @RequestParam(required = false) city: String?
    ): List<Clinic> {
        return appointmentClient.getAllClinics(state, city)
    }

    @GetMapping("/availability/{clinicId}")
    fun fetchAvailability(
        @PathVariable clinicId: String,
        @RequestParam(required = false) date: LocalDate?
    ): List<AppointmentAvailability> {
        return appointmentClient.getAvailability(clinicId, date.toString())
    }

    @PostMapping("/appointment/{aptAvlId}")
    fun bookAppointment(
        @PathVariable aptAvlId: String,
        @RequestParam(required = false) slotTime: LocalDateTime
    ): Appointment {
        return appointmentClient.bookAppointment(aptAvlId, slotTime.toString())
    }

    @GetMapping("/appointment")
    fun getAppointmentsForPatient(
        @RequestParam fromDate: String?,
    @RequestParam toDate: String?
    ): List<Appointment> {
        return appointmentClient.getAppointmentsForPatient(fromDate, toDate)
    }

    @PutMapping("/appointment/{id}")
    fun updateAppointmentSlot(
        @PathVariable id: String, @RequestParam slotTime: LocalDateTime, @RequestParam aptAvlId: String
    ): Appointment {
        return appointmentClient.updateAppointmentForPatient(id, slotTime.toString(), aptAvlId)
    }
}