package intelligent_booking.intelligent_booking.exception.controllerAdvice

import intelligent_booking.intelligent_booking.exception.exception.BindingException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun duplicateEntityValueExceptionHandle(): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("데이터 베이스 무결성 조건을 위반하였습니다.")
    }

    @ExceptionHandler(BindingException::class)
    fun bindingExceptionHandle(bindingException: BindingException): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(bindingException.message)
    }
}