package com.srsdev.tech.adminservice.payload.request

data class LoginRequest(
    var username: String,
    var password: String
)