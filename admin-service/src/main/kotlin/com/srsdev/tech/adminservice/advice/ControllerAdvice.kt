package com.srsdev.tech.adminservice.advice

import com.srsdev.tech.adminservice.exception.DatabaseRepositoryException
import com.srsdev.tech.adminservice.exception.DuplicateDataException
import com.srsdev.tech.adminservice.exception.InvalidRequestException
import com.srsdev.tech.adminservice.exception.ResourceNotFoundException
import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
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

    @ExceptionHandler(FeignException::class)
    fun handleFeignClientStatusException(e: FeignException): String{
        if(e.status()==-1) return "Please try again!!!"
        return e.contentUTF8().toString()
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidRequestException(exception: InvalidRequestException):String{
        return exception.message.toString()
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    fun handleDatabaseRepositoryException(exception: DatabaseRepositoryException): String {
        return exception.message.toString()
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    fun handleDuplicateDataException(exception: DuplicateDataException): String {
        return exception.message.toString()
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidFieldException(exception: MethodArgumentNotValidException):String {
        val errorMessage=StringBuilder()
        val bindingResult = exception.bindingResult
        val errors : List<FieldError> = bindingResult.fieldErrors
        errors.forEachIndexed{index,error ->errorMessage.append("${index+1}) ${error.defaultMessage} \n")}
        return errorMessage.toString()
    }
}