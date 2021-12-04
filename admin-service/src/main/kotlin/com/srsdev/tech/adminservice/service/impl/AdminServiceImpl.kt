package com.srsdev.tech.adminservice.service.impl

import com.srsdev.tech.adminservice.exception.DuplicateDataException
import com.srsdev.tech.adminservice.exception.ResourceNotFoundException
import com.srsdev.tech.adminservice.model.DoctorSpeciality
import com.srsdev.tech.adminservice.repository.AdminRepository
import com.srsdev.tech.adminservice.repository.DoctorSpecialityRepository
import com.srsdev.tech.adminservice.service.AdminService
import com.srsdev.tech.adminservice.model.Admin
import org.springframework.stereotype.Service

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository? = null,
    private val docSpecRepository: DoctorSpecialityRepository? = null
) : AdminService {

    override fun addAdmin(newAdmin: Admin): Admin {
        return adminRepository!!.insert(newAdmin)
    }

    override fun addDoctorSpeciality(docSpec: DoctorSpeciality): Boolean {
        if (docSpecRepository!!.existsByName(docSpec.name)) throw DuplicateDataException("Doctor speciality ${docSpec.name} already exists")
        docSpecRepository.insert(docSpec)
        return true
    }

    override fun getDoctorSpeciality(name: String): DoctorSpeciality {
        return docSpecRepository!!.findByName(name)
            ?: throw ResourceNotFoundException("Error: Doctor Speciality $name does not exists")
    }

    override fun getAllDoctorSpeciality(): List<DoctorSpeciality> {
        return docSpecRepository!!.findAll()
    }

    override fun getAdmin(userId: String): Admin {
        return adminRepository!!.findAdminByUser_Id(userId)?: throw ResourceNotFoundException("Admin not found")
    }
}