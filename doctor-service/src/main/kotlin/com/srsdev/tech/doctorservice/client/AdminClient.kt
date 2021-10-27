package com.srsdev.tech.doctorservice.client

import com.srsdev.tech.doctorservice.model.DoctorSpeciality
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "admin-service")
interface AdminClient {
    @GetMapping("/api/admin/welcome")
    fun welcome(): String

    @GetMapping("/api/admin/doctor-speciality/{name}")
    fun getDoctorSpeciality(@PathVariable name: String): DoctorSpeciality

    @GetMapping("/api/admin/doctor-speciality")
    fun getAllDoctorSpeciality(): List<DoctorSpeciality>
}