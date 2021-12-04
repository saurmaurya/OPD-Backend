package com.srsdev.tech.patientservice.repository

import com.srsdev.tech.patientservice.model.Patient
import org.springframework.data.mongodb.repository.MongoRepository

interface PatientRepository: MongoRepository<Patient, String> {
    fun findPatientByUser_Id(userId: String): Patient?
}