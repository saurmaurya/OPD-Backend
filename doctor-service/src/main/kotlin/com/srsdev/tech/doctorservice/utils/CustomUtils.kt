package com.srsdev.tech.doctorservice.utils

//import com.srsdev.tech.doctorservice.com.srsdev.tech.authserver.exception.InvalidRequestException
import com.srsdev.tech.doctorservice.model.enums.Gender

class CustomUtils {
    companion object {

        fun genderMapper(gender: String): Gender {
            return when (gender) {
                "male" -> Gender.MALE
                "female" -> Gender.FEMALE
                "transgender" -> Gender.TRANSGENDER
                else -> {
                    throw RuntimeException("Error: Invalid gender property $gender" +
                            "Choose between male, female and transgender")
                }
            }
        }
        
    }
}