package com.srsdev.tech.doctorservice.exception

class DatabaseRepositoryException(msg: String): RuntimeException(msg){
    companion object {
        private const val serialVersionUID = 1L
    }
}