package com.srsdev.tech.appointmentservice.controller

import com.srsdev.tech.appointmentservice.model.AppointmentAvailability
import com.srsdev.tech.appointmentservice.model.dto.AppointmentAvailabilityDto
import com.srsdev.tech.appointmentservice.service.AppointmentAvailabilityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/appointment")
class AppointmentRestController(
    private val aptAvlService: AppointmentAvailabilityService
) {
    @PostMapping("/availability")
    fun addAvailability(@RequestBody aptAvlDto: AppointmentAvailabilityDto): AppointmentAvailability {
        return aptAvlService.addAppointmentAvailability(aptAvlDto)
    }
}