package intelligent_booking.intelligent_booking.exception.controllerAdvice

import intelligent_booking.intelligent_booking.exception.exception.ReviewException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ReviewControllerAdvice {
    @ExceptionHandler(ReviewException::class)
    fun reviewExceptionHandle(reviewException: ReviewException): ResponseEntity<*> {
        return ResponseEntity
            .status(reviewException.reviewExceptionMessage.status)
            .body(reviewException.message)
    }
}