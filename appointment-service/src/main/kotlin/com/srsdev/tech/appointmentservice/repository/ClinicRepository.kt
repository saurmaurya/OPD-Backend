package com.srsdev.tech.appointmentservice.repository

import com.srsdev.tech.appointmentservice.model.Clinic
import org.springframework.data.mongodb.repository.MongoRepository

interface ClinicRepository : MongoRepository<Clinic, String> {
    fun findClinicsByIsActive(isActive: Boolean): List<Clinic>
    fun findClinicsByIsActiveAndState(isActive: Boolean, state: String): List<Clinic>
    fun findClinicsByIsActiveAndStateAndCity(isActive: Boolean, state: String, city: String): List<Clinic>
}