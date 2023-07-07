package intelligent_booking.intelligent_booking.exception.controllerAdvice

import intelligent_booking.intelligent_booking.exception.exception.PlaceException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PlaceControllerAdvice {
    @ExceptionHandler(PlaceException::class)
    fun placeExceptionHandle(placeException: PlaceException): ResponseEntity<String> {
        return ResponseEntity
            .status(placeException.placeExceptionMessage.status)
            .body(placeException.message)
    }
}