package com.srsdev.tech.authserver.security

import com.srsdev.tech.authserver.security.jwt.AuthEntryPointJwt
import com.srsdev.tech.authserver.security.jwt.AuthTokenFilter
import com.srsdev.tech.authserver.security.services.UserDetailsServiceImpl
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    var userDetailsService: UserDetailsServiceImpl,
    private val unauthorizedHandler: AuthEntryPointJwt
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }

    @Bean
    fun authenticationJwtTokenFilter(): AuthTokenFilter {
        return AuthTokenFilter()
    }

    @Throws(Exception::class)
    public override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().antMatchers("/api/auth/**").permitAll()
            .antMatchers("/api/admin/**").permitAll()
//            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .antMatchers("/api/doctor/**").permitAll()
//            .antMatchers("/api/doctor/**").hasRole("DOCTOR")
            .antMatchers("/api/patient/**").permitAll()
//            .antMatchers("/api/patient/**").hasRole("PATIENT")
            .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll().antMatchers("/api/doctors/**").permitAll()
            .antMatchers("/api/test/**").permitAll()
            .anyRequest().permitAll()
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }
}