package com.srsdev.tech.authserver.repository
import com.srsdev.tech.authserver.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun findByUsername(username: String?): User?
    fun findByEmail(email: String?): User?
    fun existsByUsername(username: String?): Boolean
    fun existsByEmail(email: String?): Boolean
}