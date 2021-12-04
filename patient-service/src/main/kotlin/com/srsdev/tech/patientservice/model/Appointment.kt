package com.srsdev.tech.patientservice.model

import com.srsdev.tech.patientservice.model.ClinicForAppointmentAvailability
import com.srsdev.tech.patientservice.model.enums.Status
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "appointment")
data class Appointment(
    @Id
    var id: String = ObjectId.get().toString(),
    var clinic: ClinicForAppointmentAvailability,
    var slot: LocalDateTime,
    var patient: Patient, // add appointment history for patient
    var date: LocalDateTime,
    var status: Status = Status.PENDING
) {
    constructor(
        clinic: ClinicForAppointmentAvailability,
        slot: LocalDateTime,
        patient: Patient,
        date: LocalDateTime
    ) : this(
        id = ObjectId.get().toString(),
        clinic = clinic,
        slot = slot,
        patient = patient,
        date = date,
        status = Status.PENDING
    )

    constructor(
        clinic: ClinicForAppointmentAvailability,
        slot: LocalDateTime,
        patient: Patient,
        date: LocalDateTime,
        status: Status
    ) : this(
        id = ObjectId.get().toString(),
        clinic = clinic,
        slot = slot,
        patient = patient,
        date = date,
        status = status
    )
}