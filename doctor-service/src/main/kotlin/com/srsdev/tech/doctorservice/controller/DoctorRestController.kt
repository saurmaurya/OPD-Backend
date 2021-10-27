package com.srsdev.tech.doctorservice.controller

import com.srsdev.tech.doctorservice.client.AdminClient
import com.srsdev.tech.doctorservice.client.AppointmentClient
import com.srsdev.tech.doctorservice.client.AuthClient
import com.srsdev.tech.doctorservice.exception.InvalidRequestException
import com.srsdev.tech.doctorservice.model.Clinic
import com.srsdev.tech.doctorservice.model.Doctor
import com.srsdev.tech.doctorservice.model.DoctorSpeciality
import com.srsdev.tech.doctorservice.model.User
import com.srsdev.tech.doctorservice.model.dto.*
import com.srsdev.tech.doctorservice.payload.request.LoginRequest
import com.srsdev.tech.doctorservice.payload.response.JwtResponse
import com.srsdev.tech.doctorservice.response.SuccessResponse
import com.srsdev.tech.doctorservice.service.DoctorService
import com.srsdev.tech.doctorservice.utils.CustomUtils
import io.swagger.v3.oas.annotations.Operation
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.LocalTime

@RestController
@RequestMapping("/api/doctor")
class DoctorRestController(
    private val adminClient: AdminClient,
    private val authClient: AuthClient,
    private val appointmentClient: AppointmentClient,
    private val doctorService: DoctorService,
    private val modelMapper: ModelMapper
) {
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/welcome")
    @ResponseStatus(HttpStatus.OK)
    fun welcome(): String {
        return "Welcome to doctor microservice.\n" + adminClient.welcome()
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getDoctor(): Doctor{
        val user = getAuthUser()
        return doctorService.getDoctorByUser(user)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user")
    fun getAuthUser(): User {
        return authClient.getUser()
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody loginRequest: LoginRequest): JwtResponse{
        return authClient.login(loginRequest)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createDoctor(@RequestBody body: DoctorDto): ResponseEntity<SuccessResponse> {
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

    @GetMapping("clinic/{clinicId}")
    @ResponseStatus(HttpStatus.OK)
    fun getClinicById(@PathVariable clinicId: String): Clinic {
        val doctor = getDoctor()
        val clinic = doctorService.getClinicById(clinicId)
        if (clinic.doctor.id != doctor.id)
            throw InvalidRequestException("Error: Unauthorized to view this clinic")
        return clinic
    }

    @GetMapping("/clinic/self")
    fun getSelfClinics(): List<Clinic>{
        return doctorService.getAllClinicsByDoctor(getDoctor())
    }

    @GetMapping("/clinic")
    fun getAllClinics():List<Clinic>{
        return doctorService.getAllClinics()
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/clinic/inactive")
    fun getAllInactiveClinics():List<Clinic>{
        return doctorService.getAllInactiveClinics()
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    @GetMapping("/clinic/active")
    fun getAllActiveClinics():List<Clinic>{
        return doctorService.getAllActiveClinics()
    }

    @PutMapping("/clinic/activate/{id}")
    fun activateClinic (@PathVariable id: String): Clinic{
        return doctorService.activateClinic(id)
    }

    @PostMapping("/availability")
    fun addAvailability(@RequestBody aptAvlDto: AppointmentAvailabilityDto): ResponseEntity<Any> {
        val clinic = getClinicById(aptAvlDto.clinic.id)
        if(clinic.isActive==false)
            throw InvalidRequestException("Error: Clinic is not active")
        if(clinic.doctor.id != getDoctor().id)
            throw InvalidRequestException("Error: Please enter clinic id associated with you.")
        return appointmentClient.addAvailability(aptAvlDto)
    }

    @Operation(
        summary = "Get list of all doctors",
        description = "Get list of all doctors registered in the system."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("doctors")
    fun getAllDoctors(): List<Doctor> {
        return doctorService.getAllDoctors()
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("doctors/inactive")
    fun getAllInactiveDoctors(): List<Doctor> {
        return doctorService.getAllInactiveDoctors()
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("doctors/active")
    fun getAllActiveDoctors(): Collection<Doctor> {
        return doctorService.getAllActiveDoctors()
    }


    // test
    @GetMapping("/all")
    fun allAccess(): String {
        return "Public Content."
    }

//    @GetMapping("/user")
//    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
//    fun userAccess(): String {
//        return "User Content."
//    }

    @GetMapping("/doc")
    @PreAuthorize("hasRole('DOCTOR')")
    fun doctorAccess(): String {
        return "Doctor Board."
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): String {
        return "Admin Board."
    }
}