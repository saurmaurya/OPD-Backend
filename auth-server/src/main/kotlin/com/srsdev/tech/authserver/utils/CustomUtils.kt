
package com.srsdev.tech.authserver.utils

//import com.srsdev.tech.doctorservice.com.srsdev.tech.authserver.exception.InvalidRequestException
//import com.srsdev.tech.doctorservice.model.enums.Gender

class CustomUtils {
    companion object {
//        fun statusMapper(status: String): Status {
//            return when (status) {
//                "pending" -> Status.PENDING
//                "approved" -> Status.APPROVED
//                "rejected" -> Status.REJECTED
//                "attended" -> Status.ATTENDED
//                else -> throw InvalidRequestException("Error: Invalid status value. " +
//                        "Choose between pending, approved, rejected and attended")
//            }
//        }

//        fun genderMapper(gender: String): Gender {
//            return when (gender) {
//                "male" -> Gender.MALE
//                "female" -> Gender.FEMALE
//                "transgender" -> Gender.TRANSGENDER
//                else -> {
//                    throw RuntimeException("Error: Invalid gender property $gender" +
//                            "Choose between male, female and transgender")
//                }
//            }
//        }

//        fun slotCreator(
//            startTime: LocalDateTime?,
//            endTime: LocalDateTime?,
//            avgVisitTime: Int
//        ): MutableSet<LocalDateTime> {
//            val avlTimeForSlot = Duration.between(startTime, endTime).toMinutes()
//            if (avlTimeForSlot > 480) throw InvalidRequestException("You cannot open clinic for more than 8 hours in a day")
//            val slots = mutableSetOf<LocalDateTime>()
//            val noOfSlots: Int = (avlTimeForSlot / avgVisitTime).toInt()
//            for (i in 1..noOfSlots) {
//                slots.add(
//                    startTime?.plusMinutes((avgVisitTime.toLong() * i) - 15)
//                        ?: throw InvalidRequestException("Enter valid start time")
//                )
//            }
//            return slots
//        }

//        fun sameDateMatcher(matchDate: LocalDateTime, firstDate: LocalDateTime, secondDate: LocalDateTime): Boolean {
//            return (matchDate.toLocalDate() == firstDate.toLocalDate() &&
//                    matchDate.toLocalDate() == secondDate.toLocalDate())
//        }
    }
}