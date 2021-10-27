package com.srsdev.tech.authserver.advice

import com.srsdev.tech.authserver.exception.ResourceNotFoundException
import com.srsdev.tech.authserver.exception.UserNotLoggedInException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    fun handleResourceNotFoundException(exception: ResourceNotFoundException): String {
        return exception.message.toString()
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUserNotLoggedInException(exception: UserNotLoggedInException): String{
        return exception.message.toString()
    }
}