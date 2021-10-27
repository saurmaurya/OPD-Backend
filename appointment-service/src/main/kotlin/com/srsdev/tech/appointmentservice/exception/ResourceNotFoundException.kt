package com.srsdev.tech.appointmentservice.exception


import java.lang.RuntimeException

class ResourceNotFoundException(msg: String) : RuntimeException(msg) {
    companion object {
        private const val serialVersionUID = 1L
    }
}