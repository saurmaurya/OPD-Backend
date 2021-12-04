package com.srsdev.tech.adminservice.client

import com.srsdev.tech.adminservice.model.User
import com.srsdev.tech.adminservice.model.dto.UserRegistrationDto
import org.springframework.cloud.openfeign.FeignClient
import com.srsdev.tech.adminservice.payload.request.LoginRequest
import com.srsdev.tech.adminservice.payload.response.JwtResponse
import org.springframework.web.bind.annotation.*

@FeignClient(name = "auth-server")
interface AuthClient {

    @PostMapping("/api/auth/register")
    fun register(@RequestBody body: UserRegistrationDto): User

    @PostMapping("/api/auth/login")
    fun login(@RequestBody loginRequest: LoginRequest): JwtResponse

    @GetMapping("/api/auth/user")
    fun getUser(): User

    @PostMapping("/api/auth/role")
    fun addRoles(): Boolean

    @PutMapping("/api/auth/user/activate/{id}")
    fun activateUser(@PathVariable id: String): User
}