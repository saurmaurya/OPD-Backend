package com.srsdev.tech.appointmentservice.client

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Component
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