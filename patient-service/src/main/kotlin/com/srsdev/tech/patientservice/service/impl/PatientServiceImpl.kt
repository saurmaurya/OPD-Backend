package com.srsdev.tech.patientservice.service.impl

import com.srsdev.tech.patientservice.exception.ResourceNotFoundException
import com.srsdev.tech.patientservice.model.Patient
import com.srsdev.tech.patientservice.repository.PatientRepository
import com.srsdev.tech.patientservice.service.PatientService
import org.springframework.stereotype.Service

@Service
class PatientServiceImpl(
    private val patientRepository: PatientRepository
): PatientService {
    override fun addPatient(patient: Patient): Patient {
        return patientRepository.insert(patient)
    }

    override fun getPatientByUser(userId: String): Patient {
        return patientRepository.findPatientByUser_Id(userId)
            ?: throw ResourceNotFoundException("Error: Patient not found")
    }
}