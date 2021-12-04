package com.srsdev.tech.patientservice.client

import feign.Contract
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.cloud.openfeign.support.SpringMvcContract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Configuration
class FeignClientInterceptor : RequestInterceptor {

    override fun apply(reqTemplate: RequestTemplate?) {
        if(getBearerTokenHeader()!=null)
            reqTemplate?.header("Authorization", getBearerTokenHeader())
    }
//    @Bean
//    fun feignContract(): Contract{
//        return SpringMvcContract()
//    }

    companion object {
        public fun getBearerTokenHeader(): String? {
            return (RequestContextHolder.getRequestAttributes()
                    as ServletRequestAttributes).request.getHeader("Authorization")
        }
    }
}