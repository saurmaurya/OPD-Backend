package com.srsdev.tech.doctorservice.repository

import com.srsdev.tech.doctorservice.model.Doctor
import com.srsdev.tech.doctorservice.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface DoctorRepository: MongoRepository<Doctor, String>{
    fun findByUser(user: User): Doctor?
}