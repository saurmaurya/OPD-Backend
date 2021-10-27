package com.srsdev.tech.doctorservice.client

import com.srsdev.tech.doctorservice.model.dto.AppointmentAvailabilityDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "appointment-service")
interface AppointmentClient {
    @GetMapping("/api/admin/welcome")
    fun welcome(): String

    @PostMapping("/api/appointment/availability")
    fun addAvailability(@RequestBody aptAvlDto: AppointmentAvailabilityDto): ResponseEntity<Any>
}