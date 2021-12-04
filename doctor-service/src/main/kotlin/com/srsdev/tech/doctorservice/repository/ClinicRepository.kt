package com.srsdev.tech.doctorservice.repository

import com.srsdev.tech.doctorservice.model.Clinic
import com.srsdev.tech.doctorservice.model.Doctor
import org.springframework.data.mongodb.repository.MongoRepository

interface ClinicRepository : MongoRepository<Clinic, String> {
    fun findClinicsByDoctor_Id(doctorId:String): List<Clinic>
    fun findClinicsByIsActive(isActive: Boolean): List<Clinic>
}