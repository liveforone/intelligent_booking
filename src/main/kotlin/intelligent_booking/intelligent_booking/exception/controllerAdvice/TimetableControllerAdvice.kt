package intelligent_booking.intelligent_booking.exception.controllerAdvice

import intelligent_booking.intelligent_booking.exception.exception.TimetableException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TimetableControllerAdvice {

    @ExceptionHandler(TimetableException::class)
    fun timetableExceptionHandle(timetableException: TimetableException): ResponseEntity<String> {
        return ResponseEntity
            .status(timetableException.timetableExceptionMessage.status)
            .body(timetableException.message)
    }
}