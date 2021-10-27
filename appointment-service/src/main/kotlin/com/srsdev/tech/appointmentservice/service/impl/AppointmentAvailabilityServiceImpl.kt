package com.srsdev.tech.appointmentservice.service.impl

import com.srsdev.tech.appointmentservice.exception.DuplicateDataException
import com.srsdev.tech.appointmentservice.exception.InvalidRequestException
import com.srsdev.tech.appointmentservice.exception.ResourceNotFoundException
import com.srsdev.tech.appointmentservice.model.AppointmentAvailability
import com.srsdev.tech.appointmentservice.model.dto.AppointmentAvailabilityDto
import com.srsdev.tech.appointmentservice.model.dto.ClinicForAppointmentAvailability
import com.srsdev.tech.appointmentservice.repository.AppointmentAvailabilityRepository
import com.srsdev.tech.appointmentservice.service.AppointmentAvailabilityService
import com.srsdev.tech.appointmentservice.utils.CustomUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class AppointmentAvailabilityServiceImpl(
    private val aptAvlRepo: AppointmentAvailabilityRepository?=null,
) : AppointmentAvailabilityService {
    override fun addAppointmentAvailability(
        aptAvl: AppointmentAvailabilityDto,
    ): AppointmentAvailability {
        val aptAvlObj = AppointmentAvailability(
            date = LocalDateTime.of(aptAvl.date, LocalTime.of(0, 0, 0, 0)),
            startTime = LocalDateTime.of(
                aptAvl.date,
                LocalTime.of(
                    aptAvl.startTime.split(":")[0].toInt() + 0,
                    aptAvl.startTime.split(":")[1].toInt() + 0, 0, 0
                )
            ),
            endTime = LocalDateTime.of(
                aptAvl.date,
                LocalTime.of(
                    aptAvl.endTime.split(":")[0].toInt() + 0,
                    aptAvl.endTime.split(":")[1].toInt() + 0, 0, 0
                )
            ),
            clinic = aptAvl.clinic,
        )
        if (aptAvlRepo!!.existsAppointmentAvailabilityByClinicAndDate(
                aptAvlObj.clinic ?: throw InvalidRequestException("Clinic is not available"),
                aptAvlObj.date ?: throw InvalidRequestException("Date is not available")
            )
        ) throw DuplicateDataException("Appointment for clinic ${aptAvl.clinic.name} on ${aptAvl.date} already exist")
        if (aptAvlObj.date!!.isBefore(LocalDateTime.now()))
            throw InvalidRequestException("Date must be ahead of today date")
        if (!CustomUtils.sameDateMatcher(aptAvlObj.date!!, aptAvlObj.startTime!!, aptAvlObj.endTime!!))
            throw InvalidRequestException("Slot Date must match with appointment date")
        aptAvlObj.slots = CustomUtils.slotCreator(
            aptAvlObj.startTime!!, aptAvlObj.endTime!!, aptAvl.avgVisitTime
        )
        return aptAvlRepo.save(aptAvlObj)
    }

    override fun getAptAvlById(id: String): AppointmentAvailability {
        return aptAvlRepo!!.findById(id)
            .orElseThrow { ResourceNotFoundException("Appointment Availability for $id not available") }
    }

    override fun getAptAvlByClinic(clinic: ClinicForAppointmentAvailability): Collection<AppointmentAvailability> {
        val aptAvlByClinic = aptAvlRepo!!.findAppointmentAvailabilitiesByClinic(clinic)
        if (aptAvlByClinic.isEmpty())
            throw ResourceNotFoundException("No appointment available for clinic ${clinic.name}")
        return aptAvlByClinic
    }

    override fun getAptAvlByClinicAndDate(
        clinic: ClinicForAppointmentAvailability,
        date: LocalDateTime
    ): Collection<AppointmentAvailability> {
        val aptAvlByClinic = aptAvlRepo!!.findAppointmentAvailabilitiesByClinicAndDate(clinic, date)
        if (aptAvlByClinic.isEmpty())
            throw ResourceNotFoundException("No appointment available for clinic ${clinic.name} on date $date")
        return aptAvlByClinic
    }
}