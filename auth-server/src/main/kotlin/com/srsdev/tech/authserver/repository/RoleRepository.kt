package com.srsdev.tech.authserver.repository

import com.srsdev.tech.authserver.models.ERole
import com.srsdev.tech.authserver.models.Role
import org.springframework.data.mongodb.repository.MongoRepository

interface RoleRepository : MongoRepository<Role, String> {
    fun findByName(name: ERole?): Role?
    fun existsByName(name: ERole): Boolean
}