package com.srsdev.tech.patientservice.exception

class NoResourceFoundException(msg: String): RuntimeException(msg) {
    companion object {
        private const val serialVersionUID = 1L
    }
}