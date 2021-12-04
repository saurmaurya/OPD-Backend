package com.srsdev.tech.adminservice.controller

import com.srsdev.tech.adminservice.client.AuthClient
import com.srsdev.tech.adminservice.client.DoctorClient
import com.srsdev.tech.adminservice.model.Clinic
import com.srsdev.tech.adminservice.model.Doctor
import com.srsdev.tech.adminservice.utils.CustomUtils
import com.srsdev.tech.adminservice.model.Admin
import com.srsdev.tech.adminservice.model.DoctorSpeciality
import com.srsdev.tech.adminservice.model.User
import com.srsdev.tech.adminservice.model.dto.DoctorSpecialityDto
import com.srsdev.tech.adminservice.model.dto.UserRegistrationDto
import com.srsdev.tech.adminservice.model.dto.AdminDto
import com.srsdev.tech.adminservice.service.AdminService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import com.srsdev.tech.adminservice.exception.InvalidRequestException
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Value
import com.srsdev.tech.adminservice.payload.request.LoginRequest
import com.srsdev.tech.adminservice.payload.response.JwtResponse
import com.srsdev.tech.adminservice.response.SuccessResponse
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime
import java.time.LocalTime

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
class AdminRestController(
    private val adminService: AdminService? = null,
    private val doctorClient: DoctorClient,
    private val authClient: AuthClient,
    @Value("\${admin-service.app.adminRegSecret}")
    private val adminRegSecret: String
) {
    @GetMapping("/welcome")
    @ResponseStatus(HttpStatus.OK)
    fun welcome(): String {
        return "Welcome to admin microservice"
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody loginRequest: LoginRequest): JwtResponse {
        return authClient.login(loginRequest)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addAdmin(@RequestBody body: AdminDto): ResponseEntity<SuccessResponse> {
        if (adminRegSecret != body.secretKey) throw InvalidRequestException("Secret key invalid")
        val userData = UserRegistrationDto(body.username, body.email, body.password, setOf("admin"))
        val user = authClient.register(userData)
        val dateOfBirth = LocalDateTime.of(body.dateOfBirth, LocalTime.of(0, 0, 0, 0))
        val admin = Admin(
            name = body.name,
            dateOfBirth = dateOfBirth,
            gender = CustomUtils.genderMapper(body.gender),
            mobile = body.mobile,
            user = user
        )
        adminService!!.addAdmin(admin)
        return ResponseEntity.ok(SuccessResponse("success"))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/role")
    fun addRoles(): Boolean{
        return authClient.addRoles()
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user")
    fun getUser(): User{
        return authClient.getUser()
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getAdmin(): Admin{
        val user = getUser()
        return adminService!!.getAdmin(user.id)
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/doctor/activate/{id}")
    fun activateUser(@PathVariable id: String): User {
        println(id)
        val userId = doctorClient.getDoctorUserId(id)
        println(userId)
        return authClient.activateUser(userId)
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/clinic/activate/{id}")
    fun activateClinic(@PathVariable id: String): Clinic {
        return doctorClient.activateClinic(id)
    }
}