package com.srsdev.tech.adminservice.exception

class ResourceNotFoundException(msg: String): RuntimeException(msg){
    companion object {
        private const val serialVersionUID = 1L
    }
}