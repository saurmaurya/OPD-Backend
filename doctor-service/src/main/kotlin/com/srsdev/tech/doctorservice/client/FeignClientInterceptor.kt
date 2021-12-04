package com.srsdev.tech.doctorservice.client

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Configuration
class FeignClientInterceptor : RequestInterceptor {

    override fun apply(reqTemplate: RequestTemplate?) {
        if(getBearerTokenHeader()!=null)
            reqTemplate?.header("Authorization", getBearerTokenHeader())
    }

    companion object {
        public fun getBearerTokenHeader(): String? {
            return (RequestContextHolder.getRequestAttributes()
                    as ServletRequestAttributes).request.getHeader("Authorization")
        }
    }
}