package com.srsdev.tech.adminservice.repository

import com.srsdev.tech.adminservice.model.DoctorSpeciality
import org.springframework.data.mongodb.repository.MongoRepository

interface DoctorSpecialityRepository: MongoRepository<DoctorSpeciality, String>{
    fun existsByName(name: String): Boolean
    fun findByName(name: String): DoctorSpeciality?
}