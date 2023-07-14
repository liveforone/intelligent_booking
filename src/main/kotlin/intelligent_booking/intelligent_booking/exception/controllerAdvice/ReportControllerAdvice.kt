package intelligent_booking.intelligent_booking.exception.controllerAdvice

import intelligent_booking.intelligent_booking.exception.exception.ReportException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ReportControllerAdvice {
    @ExceptionHandler(ReportException::class)
    fun reportExceptionHandle(reportException: ReportException): ResponseEntity<*> {
        return ResponseEntity
            .status(reportException.reportExceptionMessage.status)
            .body(reportException.message)
    }
}