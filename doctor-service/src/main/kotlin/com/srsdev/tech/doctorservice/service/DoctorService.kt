package com.srsdev.tech.doctorservice.service

import com.srsdev.tech.doctorservice.model.Clinic
import com.srsdev.tech.doctorservice.model.Doctor
import com.srsdev.tech.doctorservice.model.User

interface DoctorService {
    fun addDoctor(doctor: Doctor): Boolean
    fun getDoctor(docId: String): Doctor
    fun getDoctorByUser(user: User): Doctor
    fun getAllDoctors(): List<Doctor>
    fun getAllInactiveDoctors(): List<Doctor>
    fun getAllActiveDoctors(): List<Doctor>

    fun addClinic(clinic: Clinic): Clinic
    fun getClinicById(id: String): Clinic
    fun getAllClinicsByDoctor(doctorId: String): List<Clinic>
    fun getAllClinics(): List<Clinic>
    fun getAllInactiveClinics(): List<Clinic>
    fun getAllActiveClinics(): List<Clinic>
    fun activateClinic(clinicId: String): Clinic
}
