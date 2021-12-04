package com.srsdev.tech.doctorservice.controller

import com.srsdev.tech.doctorservice.client.AdminClient
import com.srsdev.tech.doctorservice.client.AppointmentClient
import com.srsdev.tech.doctorservice.client.AuthClient
import com.srsdev.tech.doctorservice.exception.InvalidRequestException
import com.srsdev.tech.doctorservice.model.*
import com.srsdev.tech.doctorservice.model.dto.*
import com.srsdev.tech.doctorservice.payload.request.LoginRequest
import com.srsdev.tech.doctorservice.payload.response.JwtResponse
import com.srsdev.tech.doctorservice.response.SuccessResponse
import com.srsdev.tech.doctorservice.service.DoctorService
import com.srsdev.tech.doctorservice.utils.CustomUtils
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@CrossOrigin
@RestController
@RequestMapping("/api/doctor")
class DoctorRestController(
    private val adminClient: AdminClient,
    private val authClient: AuthClient,
    private val appointmentClient: AppointmentClient,
    private val doctorService: DoctorService,
) {
    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/welcome")
    @ResponseStatus(HttpStatus.OK)
    fun welcome(): String {
        return "Welcome to doctor microservice.\n" + adminClient.welcome()
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getDoctor(): Doctor {
        val user = getUser()
        return doctorService.getDoctorByUser(user)
    }

    @GetMapping("/doctorUser/{docId}")
    @ResponseStatus(HttpStatus.OK)
    fun getDoctorUserId(@PathVariable docId: String): String {
        val doctor = doctorService.getDoctor(docId)
        return doctor.user.id
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody loginRequest: LoginRequest): JwtResponse {
        return authClient.login(loginRequest)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addDoctor(@RequestBody body: DoctorDto): ResponseEntity<SuccessResponse> {
        val speciality = adminClient.getDoctorSpeciality(body.specialityId)
        val userData = UserRegistrationDto(body.username, body.email, body.password, setOf("doctor"))
        val user = authClient.register(userData)
        val dateOfBirth = LocalDateTime.of(body.dateOfBirth, LocalTime.of(0, 0, 0, 0))
        val doctor = Doctor(
            name = body.name,
            dateOfBirth = dateOfBirth,
            gender = CustomUtils.genderMapper(body.gender),
            speciality = speciality,
            licenceNo = body.licenceNo,
            identity = body.identity,
            mobile = body.mobile,
            user = user
        )
        doctorService.addDoctor(doctor)
        return ResponseEntity.ok(SuccessResponse("success"))
    }

    @GetMapping("/user")
    fun getUser(): User{
        return authClient.getUser()
    }

    @GetMapping("/doctor-speciality")
    @CrossOrigin(origins = ["http://localhost:3000"])
    fun getAllDoctorSpeciality(): List<DoctorSpeciality> {
        return adminClient.getAllDoctorSpeciality()
    }

    @PostMapping("/clinic")
    @ResponseStatus(HttpStatus.CREATED)
    fun addClinic(@RequestBody clinicBody: ClinicRegisterDto): Clinic {
        val doctor = getDoctor()
        val speciality = doctor.speciality
        val clinic = Clinic(
            name = clinicBody.name,
            description = clinicBody.description,
            speciality = speciality,
            isActive = false,
            doctor = DoctorForClinic(doctor.id, doctor.name),
            state = clinicBody.state,
            city = clinicBody.city,
            pincode = clinicBody.pincode,
            address = clinicBody.address
        )
        return doctorService.addClinic(clinic)
    }

    @GetMapping("/clinic/{clinicId}")
    @ResponseStatus(HttpStatus.OK)
    fun getClinicById(@PathVariable clinicId: String): Clinic {
        val doctor = getDoctor()
        val clinic = doctorService.getClinicById(clinicId)
        if (clinic.doctor.id != doctor.id)
            throw InvalidRequestException("Error: Unauthorized to view this clinic")
        return clinic
    }

    @GetMapping("/clinic/self")
    fun getSelfClinics(): List<Clinic> {
        return doctorService.getAllClinicsByDoctor(getDoctor().id)
    }

    @GetMapping("/clinics")
    fun getAllClinics(): List<Clinic> {
        return doctorService.getAllClinics()
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/clinics/inactive")
    fun getAllInactiveClinics(): List<Clinic> {
        return doctorService.getAllInactiveClinics()
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    @GetMapping("/clinics/active")
    fun getAllActiveClinics(): List<Clinic> {
        return doctorService.getAllActiveClinics()
    }

    @PutMapping("/clinic/activate/{id}")
    fun activateClinic(@PathVariable id: String): Clinic {
        return doctorService.activateClinic(id)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    fun getAllDoctors(): List<Doctor> {
        return doctorService.getAllDoctors()
    }

    @PostMapping("/availability")
    fun addAvailability(@RequestBody aptAvlDto: AppointmentAvailabilityDto): ResponseEntity<Any> {
        val clinic = getClinicById(aptAvlDto.clinic.id)
        if (clinic.isActive == false)
            throw InvalidRequestException("Error: Clinic is not active")
        if (clinic.doctor.id != getDoctor().id)
            throw InvalidRequestException("Error: Please enter clinic id associated with you.")
        println(aptAvlDto.startTime)
        println(aptAvlDto.endTime)
        return appointmentClient.addAvailability(aptAvlDto)
    }

    @GetMapping("/availability/{clinicId}")
    fun getAvailability(
        @PathVariable clinicId: String,
        @RequestParam(required = false) date: LocalDate?
    ): List<AppointmentAvailability> {
        val clinic = doctorService.getClinicById(clinicId)
        if (clinic.doctor.id != getDoctor().id) throw InvalidRequestException("Error: This clinic is not associated with you.")
        return appointmentClient.getAvailability(clinicId, date.toString())
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/inactive")
    fun getAllInactiveDoctors(): List<Doctor> {
        return doctorService.getAllInactiveDoctors()
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/active")
    fun getAllActiveDoctors(): Collection<Doctor> {
        return doctorService.getAllActiveDoctors()
    }

    @GetMapping("/doc")
//    @PreAuthorize("hasRole('DOCTOR')")
    fun doctorAccess(): String {
        return "Doctor Board."
    }

    @GetMapping("/admin")
//    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): String {
        return "Admin Board."
    }
}