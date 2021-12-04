package com.srsdev.tech.adminservice.utils

import com.srsdev.tech.adminservice.model.enums.Gender

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