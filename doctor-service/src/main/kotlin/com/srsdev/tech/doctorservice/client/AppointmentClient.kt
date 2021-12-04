package com.srsdev.tech.doctorservice.client

import com.srsdev.tech.doctorservice.model.AppointmentAvailability
import com.srsdev.tech.doctorservice.model.Clinic
import com.srsdev.tech.doctorservice.model.dto.AppointmentAvailabilityDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@FeignClient(name = "appointment-service")
interface AppointmentClient {
    @GetMapping("/api/admin/welcome")
    fun welcome(): String

    @PostMapping("/api/appointment/availability")
    fun addAvailability(@RequestBody aptAvlDto: AppointmentAvailabilityDto): ResponseEntity<Any>

    @GetMapping("/api/appointment/availability/{clinicId}")
    fun getAvailability(
        @PathVariable clinicId: String,
        @RequestParam(required = false) date: String?
        ): List<AppointmentAvailability>

}