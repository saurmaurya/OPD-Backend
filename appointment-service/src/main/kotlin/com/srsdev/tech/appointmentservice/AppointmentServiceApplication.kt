package com.srsdev.tech.appointmentservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
class AppointmentServiceApplication

fun main(args: Array<String>) {
    runApplication<AppointmentServiceApplication>(*args)
}
