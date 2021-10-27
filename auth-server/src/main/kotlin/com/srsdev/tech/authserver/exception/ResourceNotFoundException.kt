package com.srsdev.tech.authserver.exception

class ResourceNotFoundException(msg: String): RuntimeException(msg){
    companion object {
        private const val serialVersionUID = 1L
    }
}