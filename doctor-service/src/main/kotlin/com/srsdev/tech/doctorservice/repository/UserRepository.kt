package com.srsdev.tech.doctorservice.repository
import com.srsdev.tech.doctorservice.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun findByUsername(username: String?): User?
    fun findByEmail(email: String?): User?
    fun existsByUsername(username: String?): Boolean
    fun existsByEmail(email: String?): Boolean
}