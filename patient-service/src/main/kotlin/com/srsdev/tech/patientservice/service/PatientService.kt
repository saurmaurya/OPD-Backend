package com.srsdev.tech.patientservice.service

import com.srsdev.tech.patientservice.model.Patient

interface PatientService {
    fun addPatient(patient: Patient): Patient
    fun getPatientByUser(userId: String): Patient
}