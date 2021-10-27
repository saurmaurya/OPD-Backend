package com.srsdev.tech.adminservice.client

import com.srsdev.tech.adminservice.model.Clinic
import com.srsdev.tech.adminservice.model.Doctor
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "doctor-service")
interface DoctorClient {
    @GetMapping("/api/doctor/doctors")
    fun getAllDoctors(): List<Doctor>

    @GetMapping("/api/doctor/doctors/inactive")
    fun getAllInactiveDoctors(): List<Doctor>

    @GetMapping("/api/doctor/doctors/active")
    fun getAllActiveDoctors(): List<Doctor>

    @GetMapping("/api/doctor/clinics")
    fun getAllClinics(): List<Clinic>

    @GetMapping("/api/doctor/clinics/inactive")
    fun getAllInactiveClinics(): List<Clinic>

    @GetMapping("/api/doctor/clinics/active")
    fun getAllActiveClinics(): List<Clinic>

    @GetMapping("/api/doctor/clinic/activate/{id}")
    fun activateClinic(@PathVariable id: String): Clinic
}