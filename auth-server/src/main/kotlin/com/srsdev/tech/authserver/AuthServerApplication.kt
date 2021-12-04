package com.srsdev.tech.authserver

import com.srsdev.tech.authserver.models.ERole
import com.srsdev.tech.authserver.models.Role
import com.srsdev.tech.authserver.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class AuthServerApplication(
    private val userService: UserService
): CommandLineRunner{
    override fun run(vararg args: String?) {
        val role = mutableListOf<Role>()
        role.add(Role(name = ERole.ROLE_ADMIN))
        role.add(Role(name = ERole.ROLE_PATIENT))
        role.add(Role(name = ERole.ROLE_DOCTOR))
        userService.addRole(role)
    }

}

fun main(args: Array<String>) {
    runApplication<AuthServerApplication>(*args)
}
