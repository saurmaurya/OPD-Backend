package com.srsdev.tech.doctorservice.payload.request

data class LoginRequest(
    var username: String,
    var password: String
)