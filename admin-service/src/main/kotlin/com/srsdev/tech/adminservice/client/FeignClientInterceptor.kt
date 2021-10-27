package com.srsdev.tech.adminservice.client

import feign.RequestInterceptor
import feign.RequestTemplate
import feign.okhttp.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Component
class FeignClientInterceptor : RequestInterceptor {

    override fun apply(reqTemplate: RequestTemplate?) {
        if(getBearerTokenHeader()!=null)
            reqTemplate?.header("Authorization", getBearerTokenHeader())
    }

//    @Bean
//    fun client(): OkHttpClient {
//        return OkHttpClient()
//    }

    companion object {
        public fun getBearerTokenHeader(): String? {
            return (RequestContextHolder.getRequestAttributes()
                    as ServletRequestAttributes).request.getHeader("Authorization")
        }
    }
}