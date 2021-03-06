package com.srsdev.tech.adminservice.client

import com.srsdev.tech.adminservice.model.Clinic
import com.srsdev.tech.adminservice.model.Doctor
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@FeignClient(name = "doctor-service")
interface DoctorClient {
    @GetMapping("/api/doctor/doctorUser/{docId}")
    fun getDoctorUserId(@PathVariable docId: String): String

    @GetMapping("/api/doctor/all")
    fun getAllDoctors(): List<Doctor>

    @GetMapping("/api/doctor/inactive")
    fun getAllInactiveDoctors(): List<Doctor>

    @GetMapping("/api/doctor/active")
    fun getAllActiveDoctors(): List<Doctor>

    @GetMapping("/api/doctor/clinics")
    fun getAllClinics(): List<Clinic>

    @GetMapping("/api/doctor/clinics/inactive")
    fun getAllInactiveClinics(): List<Clinic>

    @GetMapping("/api/doctor/clinics/active")
    fun getAllActiveClinics(): List<Clinic>

    @PutMapping("/api/doctor/clinic/activate/{id}")
    fun activateClinic(@PathVariable id: String): Clinic
}