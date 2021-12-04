package com.srsdev.tech.patientservice.exception

class DatabaseRepositoryException(msg: String): RuntimeException(msg){
    companion object {
        private const val serialVersionUID = 1L
    }
}