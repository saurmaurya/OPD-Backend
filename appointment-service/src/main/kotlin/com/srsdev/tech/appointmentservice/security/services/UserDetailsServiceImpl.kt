package com.srsdev.tech.appointmentservice.security.services

import com.srsdev.tech.appointmentservice.model.User
import com.srsdev.tech.appointmentservice.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(
    var userRepository: UserRepository
) : UserDetailsService {

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetailsImpl? {
        val user: User = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException(
                "User Not Found with username: $username"
            )

        return user.let { UserDetailsImpl.build(it) }
    }
}