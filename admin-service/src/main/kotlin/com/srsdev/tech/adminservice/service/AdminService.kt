package com.srsdev.tech.adminservice.service

import com.srsdev.tech.adminservice.model.DoctorSpeciality

interface AdminService {
    fun addDoctorSpeciality(docSpec: DoctorSpeciality): Boolean
    fun getDoctorSpeciality(name: String): DoctorSpeciality
    fun getAllDoctorSpeciality(): List<DoctorSpeciality>
}