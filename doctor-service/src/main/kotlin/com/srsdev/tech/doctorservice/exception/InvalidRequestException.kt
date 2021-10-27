package com.srsdev.tech.doctorservice.exception

class InvalidRequestException(msg: String): RuntimeException(msg) {
    companion object {
        private const val serialVersionUID = 1L
    }
}