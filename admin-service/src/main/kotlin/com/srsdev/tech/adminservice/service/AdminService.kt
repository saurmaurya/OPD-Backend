package com.srsdev.tech.adminservice.service

import com.srsdev.tech.adminservice.model.DoctorSpeciality
import com.srsdev.tech.adminservice.model.Admin


interface AdminService {
    fun addAdmin(newAdmin: Admin): Admin

    fun addDoctorSpeciality(docSpec: DoctorSpeciality): Boolean
    fun getDoctorSpeciality(name: String): DoctorSpeciality
    fun getAllDoctorSpeciality(): List<DoctorSpeciality>

    fun getAdmin(userId: String): Admin
}