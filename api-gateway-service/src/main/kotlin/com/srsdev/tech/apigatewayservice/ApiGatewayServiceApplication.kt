package com.srsdev.tech.apigatewayservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ApiGatewayServiceApplication

fun main(args: Array<String>) {
    runApplication<ApiGatewayServiceApplication>(*args)
}
