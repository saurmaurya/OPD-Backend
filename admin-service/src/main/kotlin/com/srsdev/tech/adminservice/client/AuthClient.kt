package com.srsdev.tech.adminservice.client

import com.srsdev.tech.adminservice.model.User
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "auth-server")
interface AuthClient {
    @PutMapping("/api/auth/user/activate/{id}")
    fun activateUser(@PathVariable id: String): User
}