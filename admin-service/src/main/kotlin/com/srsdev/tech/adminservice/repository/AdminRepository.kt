package com.srsdev.tech.adminservice.repository

import com.srsdev.tech.adminservice.model.Admin
import org.springframework.data.mongodb.repository.MongoRepository

interface AdminRepository: MongoRepository<Admin, String>{
    fun findAdminByUser_Id(userId: String): Admin?
}