package intelligent_booking.intelligent_booking.exception.controllerAdvice

import intelligent_booking.intelligent_booking.exception.exception.ReservationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ReservationControllerAdvice {
    @ExceptionHandler(ReservationException::class)
    fun reservationExceptionHandle(reservationException: ReservationException): ResponseEntity<*> {
        return ResponseEntity
            .status(reservationException.reservationExceptionMessage.status)
            .body(reservationException.message)
    }
}