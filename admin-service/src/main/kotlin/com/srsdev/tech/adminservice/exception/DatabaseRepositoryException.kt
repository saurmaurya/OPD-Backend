package com.srsdev.tech.adminservice.exception

class DatabaseRepositoryException(msg: String): RuntimeException(msg){
    companion object {
        private const val serialVersionUID = 1L
    }
}