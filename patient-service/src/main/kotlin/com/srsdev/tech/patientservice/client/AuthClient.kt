package com.srsdev.tech.patientservice.client

import com.srsdev.tech.patientservice.model.User
import com.srsdev.tech.patientservice.model.dto.UserRegistrationDto
import com.srsdev.tech.patientservice.payload.request.LoginRequest
import com.srsdev.tech.patientservice.payload.response.JwtResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "auth-server")
interface AuthClient {
    @PostMapping("/api/auth/login")
    fun login(@RequestBody loginRequest: LoginRequest): JwtResponse

    @PostMapping("/api/auth/register")
    fun register(@RequestBody body: UserRegistrationDto): User

    @GetMapping("/api/auth/user")
    fun getUser(): User
}