package com.srsdev.tech.appointmentservice.client

import com.srsdev.tech.appointmentservice.model.Clinic
import com.srsdev.tech.appointmentservice.model.Doctor
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "doctor-service")
interface DoctorClient {
    @GetMapping("/api/doctor/clinic/{id}")
    fun getClinicById(@PathVariable id: String): Clinic

    @GetMapping("/api/doctor")
    fun getDoctor(): Doctor
}