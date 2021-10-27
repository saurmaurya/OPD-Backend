package com.srsdev.tech.adminservice.controller

import com.srsdev.tech.adminservice.client.AuthClient
import com.srsdev.tech.adminservice.client.DoctorClient
import com.srsdev.tech.adminservice.model.Clinic
import com.srsdev.tech.adminservice.model.Doctor
import com.srsdev.tech.adminservice.model.DoctorSpeciality
import com.srsdev.tech.adminservice.model.User
import com.srsdev.tech.adminservice.model.dto.DoctorSpecialityDto
import com.srsdev.tech.adminservice.service.AdminService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminRestController(
    private val adminService: AdminService? = null,
    private val doctorClient: DoctorClient,
    private val authClient: AuthClient
) {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/welcome")
    @ResponseStatus(HttpStatus.OK)
    fun welcome(): String {
        return "Welcome to admin microservice"
    }


    @PostMapping("/doctor-speciality")
    @ResponseStatus(HttpStatus.CREATED)
    fun addDoctorSpeciality(@RequestBody body: DoctorSpecialityDto): Boolean {
        val doctorSpec = DoctorSpeciality(body.name)
        return adminService!!.addDoctorSpeciality(doctorSpec)
    }

    @GetMapping("/doctor-speciality/{name}")
    @ResponseStatus(HttpStatus.OK)
    fun getDoctorSpeciality(@PathVariable name: String): DoctorSpeciality {
        return adminService!!.getDoctorSpeciality(name)
    }

    @GetMapping("/doctor-speciality")
    fun getAllDoctorSpeciality(): List<DoctorSpeciality>{
        return adminService!!.getAllDoctorSpeciality()
    }

    @GetMapping("/doctors")
    fun getAllDoctors(): List<Doctor>{
        return doctorClient.getAllDoctors()
    }

    @GetMapping("/doctors/inactive")
    fun getAllInactiveDoctors(): List<Doctor>{
        return doctorClient.getAllInactiveDoctors()
    }

    @GetMapping("/doctors/active")
    fun getAllActiveDoctors(): List<Doctor>{
        return doctorClient.getAllActiveDoctors()
    }

    @PutMapping("doctor/activate/{id}")
    fun activateUser(@PathVariable id: String): User {
        return authClient.activateUser(id)
    }

    @GetMapping("/clinics")
    fun getAllClinics(): List<Clinic>{
        return doctorClient.getAllClinics()
    }

    @GetMapping("/clinics/inactive")
    fun getAllInactiveClinics(): List<Clinic>{
        return doctorClient.getAllInactiveClinics()
    }

    @GetMapping("/clinics/active")
    fun getAllActiveClinics(): List<Clinic>{
        return doctorClient.getAllActiveClinics()
    }

    @PutMapping("clinic/activate/{id}")
    fun activateClinic(@PathVariable id: String): Clinic {
        return doctorClient.activateClinic(id)
    }
}