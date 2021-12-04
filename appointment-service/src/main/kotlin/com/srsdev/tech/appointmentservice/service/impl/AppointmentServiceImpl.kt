package com.srsdev.tech.appointmentservice.service.impl

import com.srsdev.tech.appointmentservice.exception.DuplicateEntryException
import com.srsdev.tech.appointmentservice.exception.InvalidRequestException
import com.srsdev.tech.appointmentservice.exception.ResourceNotFoundException
import com.srsdev.tech.appointmentservice.model.Appointment
import com.srsdev.tech.appointmentservice.model.AppointmentAvailability
import com.srsdev.tech.appointmentservice.model.Clinic
import com.srsdev.tech.appointmentservice.model.Patient
import com.srsdev.tech.appointmentservice.model.dto.AppointmentAvailabilityDto
import com.srsdev.tech.appointmentservice.model.enums.Status
import com.srsdev.tech.appointmentservice.repository.AppointmentAvailabilityRepository
import com.srsdev.tech.appointmentservice.repository.AppointmentRepository
import com.srsdev.tech.appointmentservice.repository.ClinicRepository
import com.srsdev.tech.appointmentservice.service.AppointmentService
import com.srsdev.tech.appointmentservice.utils.CustomUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AppointmentServiceImpl(
    private val aptAvlRepo: AppointmentAvailabilityRepository,
    private val clinicRepository: ClinicRepository,
    private val appointmentRepository: AppointmentRepository
) : AppointmentService {

    override fun addAppointmentAvailability(
        aptAvl: AppointmentAvailabilityDto,
    ): AppointmentAvailability {
        if (aptAvlRepo.existsAppointmentAvailabilityByClinic_IdAndDate(
                aptAvl.clinic.id,
                aptAvl.date
            )
        ) throw DuplicateEntryException("Appointment for clinic ${aptAvl.clinic.name} on ${aptAvl.date} already exist")
        if (aptAvl.date.isBefore(LocalDateTime.now()))
            throw InvalidRequestException("Date must be ahead of today date")
        if (!CustomUtils.sameDateMatcher(aptAvl.date, aptAvl.startTime, aptAvl.endTime))
            throw InvalidRequestException("Error: Kindly provide slot date equal to booking date")
        val aptAvlObj = AppointmentAvailability(
            date = aptAvl.date,
            startTime = aptAvl.startTime,
            endTime = aptAvl.endTime,
            clinic = aptAvl.clinic,
            slots = CustomUtils.slotCreator(
                aptAvl.startTime,
                aptAvl.endTime,
                aptAvl.avgVisitTime
            )
        )
        return aptAvlRepo.save(aptAvlObj)
    }

    override fun getAptAvlById(id: String): AppointmentAvailability {
        val aptAvl = aptAvlRepo.findById(id)
            .orElseThrow { ResourceNotFoundException("Appointment Availability for $id not available") }
        if (!aptAvl.date.isAfter(LocalDateTime.now()))
            throw InvalidRequestException("Error: Kindly provide future dates only")
        return aptAvl
    }

    override fun getAptAvlByClinic(clinicId: String): List<AppointmentAvailability> {
        return aptAvlRepo.findAppointmentAvailabilitiesByClinic_IdAndDateAfter(
            clinicId,
            LocalDateTime.now()
        )
    }

    override fun getAptAvlByClinicAndDate(
        clinicId: String,
        date: LocalDateTime
    ): AppointmentAvailability {
        if (!date.isAfter(LocalDateTime.now()))
            throw InvalidRequestException("Error: Kindly provide date after ${LocalDateTime.now()}")
        return aptAvlRepo.findAppointmentAvailabilitiesByClinic_IdAndDate(clinicId, date)
    }

    override fun getAllClinicForPatient(state: String?, city: String?): List<Clinic> {
        if (state != null && city != null) {
            return clinicRepository.findClinicsByIsActiveAndStateAndCity(true, state, city)
        } else if (state != null && city == null)
            return clinicRepository.findClinicsByIsActiveAndState(true, state)
        return clinicRepository.findClinicsByIsActive(true)
    }

    override fun bookAppointment(
        aptAvl: AppointmentAvailability,
        patient: Patient,
        slotTime: LocalDateTime
    ): Appointment {
        if (!aptAvl.date.isAfter(LocalDateTime.now()))
            throw InvalidRequestException("Error: Kindly choose availability for future dates only")
        if (appointmentRepository.existsByPatient_IdAndClinic_IdAndDate(
                patient.id, aptAvl.clinic.id, aptAvl.date
            )
        ) throw DuplicateEntryException("Appointment for this date already exist")
        if (!aptAvl.slots.contains(slotTime))
            throw ResourceNotFoundException("Slot $slotTime not found")
        val appointment = Appointment(
            aptAvl.clinic, slotTime, patient, aptAvl.date
        )
        aptAvl.slots.remove(slotTime)
        aptAvlRepo.save(aptAvl)
        appointment.slot = slotTime
        aptAvl.slots.remove(slotTime)
        aptAvlRepo.save(aptAvl)
        return appointmentRepository.insert(appointment)
    }

    override fun getAppointmentById(id: String): Appointment {
        return appointmentRepository.findById(id).orElseThrow { ResourceNotFoundException("Appointment not found") }
    }

    override fun updateAppointmentForPatient(
        oldAptAvl: AppointmentAvailability,
        newAptAvl: AppointmentAvailability,
        appointment: Appointment,
        slotTime: LocalDateTime
    ): Appointment {
        if (!newAptAvl.slots.contains(slotTime))
            throw InvalidRequestException("Slot $slotTime not found")
        oldAptAvl.slots.add(appointment.slot)
        newAptAvl.slots.remove(slotTime)
        aptAvlRepo.save(oldAptAvl)
        aptAvlRepo.save(newAptAvl)
        appointment.slot = slotTime
        appointment.date = newAptAvl.date
        appointment.status = Status.PENDING
        return appointmentRepository.save(appointment)
    }

    override fun deleteAppointmentForPatient(oldAptAvl: AppointmentAvailability, appointment: Appointment): Boolean {
        if (!appointment.date.isAfter(LocalDateTime.now()))
            throw InvalidRequestException("Error: You can only delete future appointments")
        oldAptAvl.slots.add(appointment.slot)
        aptAvlRepo.save(oldAptAvl)
        appointmentRepository.delete(appointment)
        return true
    }

    override fun updateAppointmentStatus(appointment: Appointment, status: Status): Appointment {
        if (status == Status.REJECTED) {
            val oldAptAvl = aptAvlRepo.findAppointmentAvailabilitiesByClinic_IdAndDate(
                appointment.clinic.id, appointment.date
            )
            oldAptAvl.slots.add(appointment.slot)
            aptAvlRepo.save(oldAptAvl)
        }
        appointment.status = status
        return appointmentRepository.save(appointment)
    }

    override fun getAppointmentsForPatient(patientId: String, fromDate: String?, toDate: String?): List<Appointment> {
        if (fromDate != null && toDate != null)
            return appointmentRepository.findAppointmentsByPatient_IdAndDateAfterAndDateBefore(
                patientId,
                LocalDateTime.parse(fromDate), LocalDateTime.parse(toDate)
            )
        if (fromDate != null && toDate == null)
            return appointmentRepository.findAppointmentsByPatient_IdAndDateAfter(
                patientId,
                LocalDateTime.parse(fromDate)
            )
        return appointmentRepository.findAppointmentsByPatient_Id(patientId)

    }

    override fun getAppointmentsForDoctor(doctorId: String, fromDate: String?, toDate: String?): List<Appointment> {
        if (fromDate != null && toDate != null)
            return appointmentRepository.findAppointmentsByClinic_Doctor_IdAndDateAfterAndDateBefore(
                doctorId,
                LocalDateTime.parse(fromDate), LocalDateTime.parse(toDate)
            )
        if (fromDate != null && toDate == null)
            return appointmentRepository.findAppointmentsByClinic_Doctor_IdAndDateAfter(
                doctorId,
                LocalDateTime.parse(fromDate)
            )
        return appointmentRepository.findAppointmentsByClinic_Doctor_Id(doctorId)

    }
}