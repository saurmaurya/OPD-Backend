package com.srsdev.tech.patientservice.client

import com.srsdev.tech.patientservice.model.Appointment
import com.srsdev.tech.patientservice.model.AppointmentAvailability
import com.srsdev.tech.patientservice.model.Clinic
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(name = "appointment-service")
interface AppointmentClient {
    @GetMapping("/api/appointment/clinics")
    fun getAllClinics(
        @RequestParam(required = false) state: String?,
        @RequestParam(required = false) city: String?
    ): List<Clinic>

    @GetMapping("/api/appointment/availability/{clinicId}")
    fun getAvailability(
        @PathVariable clinicId: String,
        @RequestParam(required = false) date: String?
    ): List<AppointmentAvailability>

    @PostMapping("/api/appointment/{aptAvlId}")
    fun bookAppointment(
        @PathVariable aptAvlId: String,
        @RequestParam slotTime: String
    ): Appointment

    @GetMapping("/api/appointment/patient")
    fun getAppointmentsForPatient(
        @RequestParam fromDate: String?,
        @RequestParam toDate: String?
    ): List<Appointment>

    @PutMapping("/api/appointment/patient/{id}")
    fun updateAppointmentForPatient(
        @PathVariable id: String,
        @RequestParam slotTime: String,
        @RequestParam aptAvlId: String
    ): Appointment
}