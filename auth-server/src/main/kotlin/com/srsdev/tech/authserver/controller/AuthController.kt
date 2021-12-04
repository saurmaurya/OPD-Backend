package com.srsdev.tech.authserver.controller

import com.srsdev.tech.authserver.exception.UserNotLoggedInException
import com.srsdev.tech.authserver.models.ERole
import com.srsdev.tech.authserver.models.Role
import com.srsdev.tech.authserver.models.User
import com.srsdev.tech.authserver.models.dto.UserRegistrationDto
import com.srsdev.tech.authserver.payload.request.LoginRequest
import com.srsdev.tech.authserver.payload.response.JwtResponse
import com.srsdev.tech.authserver.security.jwt.JwtUtils
import com.srsdev.tech.authserver.security.services.UserDetailsImpl
import com.srsdev.tech.authserver.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val userService: UserService
) {
    @Operation(
        summary = "Login to the system",
        description = "REST endpoint that log user into the system and returns the user with token type and token string",
    )
    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest?): JwtResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest?.username,
                loginRequest?.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = authentication?.let { jwtUtils.generateJwtToken(it) }
        val userDetails = authentication?.principal as UserDetailsImpl
        val roles = userDetails.authorities.stream()
            .map { item: GrantedAuthority -> item.authority }
            .collect(Collectors.toList())
        return JwtResponse(
            jwt,
            userDetails.id,
            userDetails.username,
            userDetails.email,
            roles
        )
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    fun register(@RequestBody body: UserRegistrationDto): User {
        return userService.registerUser(body)
    }

    @PostMapping("/role")
    fun addRoles(): Boolean {
        val role = mutableListOf<Role>()
        role.add(Role(name = ERole.ROLE_ADMIN))
        role.add(Role(name = ERole.ROLE_PATIENT))
        role.add(Role(name = ERole.ROLE_DOCTOR))
        userService.addRole(role)
        return true
    }

    @GetMapping("/user")
    fun getUser(): User {
        val username = SecurityContextHolder.getContext().authentication.name
        if (username == "anonymousUser")
            throw UserNotLoggedInException("Log in first")
        return userService.getUserByUsername(username)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("user/activate/{id}")
    fun activateUser(@PathVariable id: String): User {
        return userService.activateUser(id)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/greet/admin")
    fun greetingAdmin(): ResponseEntity<String> {
        return ResponseEntity("Welcome, you have ADMIN role", HttpStatus.OK)
    }

    @PreAuthorize("hasRole('ANONYMOUS')")
    @GetMapping("/greet/anonymous")
    fun greetingAnonymous(): ResponseEntity<String> {
        return ResponseEntity("Welcome, you have USER and ADMIN role", HttpStatus.OK)
    }
}