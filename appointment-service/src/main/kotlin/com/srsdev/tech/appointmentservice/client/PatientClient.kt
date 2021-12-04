package com.srsdev.tech.appointmentservice.client

import com.srsdev.tech.appointmentservice.model.Patient
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "patient-service")
interface PatientClient {
    @GetMapping("/api/patient")
    fun getPatient(): Patient
}