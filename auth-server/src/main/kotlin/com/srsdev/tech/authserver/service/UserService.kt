package com.srsdev.tech.authserver.service

import com.srsdev.tech.authserver.models.Role
import com.srsdev.tech.authserver.models.User
import com.srsdev.tech.authserver.models.dto.LoginDto
import com.srsdev.tech.authserver.models.dto.UserRegistrationDto
import com.srsdev.tech.authserver.payload.response.JwtResponse

interface UserService {
    fun registerUser(user: UserRegistrationDto): User
    fun getUserById(id: String): User
    fun getUserByUsername(username: String): User
    fun getUserEmail(email: String): User
    fun activateUser(id: String): User
    fun loginUser(loginDto: LoginDto): JwtResponse
    fun addRole(roles: List<Role>): Boolean
}