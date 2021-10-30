package com.globo.businesscontrol

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.http.HttpStatus
import javax.persistence.EntityNotFoundException
import org.springframework.web.bind.annotation.ResponseBody
import java.lang.RuntimeException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class HttpErrorExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(
        EntityNotFoundException::class
    )
    @ResponseBody
    fun tratarErroRecursoNaoEncontrado(e: RuntimeException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    }
}