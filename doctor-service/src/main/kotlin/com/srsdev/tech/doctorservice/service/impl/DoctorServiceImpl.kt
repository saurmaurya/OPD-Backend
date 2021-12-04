package com.srsdev.tech.doctorservice.service.impl

import com.srsdev.tech.doctorservice.exception.DatabaseRepositoryException
import com.srsdev.tech.doctorservice.exception.InvalidRequestException
import com.srsdev.tech.doctorservice.exception.ResourceNotFoundException
import com.srsdev.tech.doctorservice.model.Clinic
import com.srsdev.tech.doctorservice.model.Doctor
import com.srsdev.tech.doctorservice.model.User
import com.srsdev.tech.doctorservice.repository.ClinicRepository
import com.srsdev.tech.doctorservice.repository.DoctorRepository
import com.srsdev.tech.doctorservice.service.DoctorService
import org.springframework.stereotype.Service

@Service
class DoctorServiceImpl(
    private val doctorRepository: DoctorRepository? = null,
    private val clinicRepository: ClinicRepository? = null
) : DoctorService {
    override fun addDoctor(doctor: Doctor): Boolean {
        doctorRepository?.insert(doctor) ?: throw DatabaseRepositoryException("Error: DB Error! Cannot create doctor")
        return true
    }

    override fun getDoctor(docId: String): Doctor {
        return doctorRepository!!.findById(docId)
            .orElseThrow { ResourceNotFoundException("Doctor with id $docId doesn't exists!!!") }
    }

    override fun getDoctorByUser(user: User): Doctor {
        return doctorRepository!!.findByUser(user) ?: throw ResourceNotFoundException("Doctor does not exist ")
    }

    override fun getAllDoctors(): List<Doctor> {
        return doctorRepository!!.findAll()
    }

    override fun getAllInactiveDoctors(): List<Doctor> {
        return getAllDoctors().filter { doctor ->
            !(doctor.user.isActive)
        }
    }

    override fun getAllActiveDoctors(): List<Doctor> {
        return getAllDoctors().filter { doctor -> doctor.user.isActive }
    }

    override fun addClinic(clinic: Clinic): Clinic {
        return clinicRepository?.insert(clinic)
            ?: throw DatabaseRepositoryException("Error: Clinic registration DB error.")
    }

    override fun getClinicById(id: String): Clinic {
        return clinicRepository?.findById(id)?.get() ?: throw ResourceNotFoundException("Clinic not found")
    }

    override fun getAllClinicsByDoctor(doctorId: String): List<Clinic> {
        return clinicRepository!!.findClinicsByDoctor_Id(doctorId)
    }

    override fun getAllClinics(): List<Clinic> {
        return clinicRepository!!.findAll()
    }

    override fun getAllInactiveClinics(): List<Clinic> {
        return getAllClinics().filter { it.isActive == false }
    }

    override fun getAllActiveClinics(): List<Clinic> {
        return getAllClinics().filter { it.isActive == true }
    }

    override fun activateClinic(clinicId: String): Clinic {
        when {
            clinicRepository!!.existsById(clinicId) -> {
                val clinic = getClinicById(clinicId)
                clinic.isActive = true
                return clinicRepository.save(clinic)
            }
            else -> {
                throw InvalidRequestException("Error: unable to retrieve clinic")
            }
        }
    }
}