package com.srsdev.tech.authserver.service

import com.srsdev.tech.authserver.exception.ResourceNotFoundException
import com.srsdev.tech.authserver.models.ERole
import com.srsdev.tech.authserver.models.Role
import com.srsdev.tech.authserver.models.User
import com.srsdev.tech.authserver.models.dto.LoginDto
import com.srsdev.tech.authserver.models.dto.UserRegistrationDto
import com.srsdev.tech.authserver.payload.response.JwtResponse
import com.srsdev.tech.authserver.repository.RoleRepository
import com.srsdev.tech.authserver.repository.UserRepository
import com.srsdev.tech.authserver.security.jwt.JwtUtils
import com.srsdev.tech.authserver.security.services.UserDetailsImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.function.Consumer
import java.util.stream.Collectors

@Service
class UserServiceImpl constructor(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository,
    private var encoder: PasswordEncoder,
    private val roleRepository: RoleRepository,
) : UserService {
    override fun registerUser(
        user: UserRegistrationDto
    ): User {
        if (userRepository.existsByUsername(user.username)) {
            throw IllegalArgumentException("Error: Email already exists")
        }
        if (userRepository.existsByEmail(user.email)) {
            throw IllegalArgumentException("Error: Email already exists")
        }
        val newUser = User(
            user.username,
            user.email,
            encoder.encode(user.password)
        )
        val userRoles: MutableSet<Role> = HashSet()
        user.roles.forEach(
            Consumer { role: String? ->
                when (role) {
                    "admin" -> {
                        val adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        if (adminRole != null) {
                            userRoles.add(adminRole)
                        } else throw ResourceNotFoundException("Error: Role admin is not found.")
                    }
                    "doctor" -> {
                        val doctorRole = roleRepository.findByName(ERole.ROLE_DOCTOR)
                        if (doctorRole != null) {
                            userRoles.add(doctorRole)
                        } else throw ResourceNotFoundException("Error: Role doctor is not found.")
                    }
                    else -> {
                        val userRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                        if (userRole != null) {
                            userRoles.add(userRole)
                        } else throw ResourceNotFoundException("Error: Role Patient is not found.")
                    }
                }
            }
        )
        newUser.roles = userRoles
        if (newUser.roles.any { role ->
                role.id == roleRepository.findByName(ERole.ROLE_ADMIN)!!.id ||
                role.id == roleRepository.findByName(ERole.ROLE_PATIENT)!!.id
        }) {
            newUser.isActive = true
        }
        return userRepository.save(newUser)
    }

    override fun getUserById(id: String): User {
        return userRepository.findById(id).orElseThrow { ResourceNotFoundException("User does not exist ") }
    }

    override fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
            ?: throw ResourceNotFoundException("Error: User $username not found")
    }

    override fun getUserEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw ResourceNotFoundException("User does not exist")
    }

    override fun activateUser(id: String): User {
        val user = this.getUserById(id)
        user.isActive = true
        return userRepository.save(user)
    }

    override fun loginUser(loginDto: LoginDto): JwtResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = authentication?.let { jwtUtils.generateJwtToken(it) }
        val userDetails = authentication?.principal as UserDetailsImpl
        val roles = userDetails.authorities.stream()
            .map { item: GrantedAuthority -> item.authority }
            .collect(Collectors.toList())
        return JwtResponse(
            jwt, userDetails.id, userDetails.username, userDetails.email, roles
        )
    }

    override fun addRole(roles: List<Role>): Boolean {
        roles.forEach { role ->
            if(!roleRepository.existsByName(role.name))
                roleRepository.save(role)
        }
        return true
    }

}