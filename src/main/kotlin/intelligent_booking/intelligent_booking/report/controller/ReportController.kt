package intelligent_booking.intelligent_booking.report.controller

import intelligent_booking.intelligent_booking.logger
import intelligent_booking.intelligent_booking.report.controller.constant.ReportControllerLog
import intelligent_booking.intelligent_booking.report.controller.constant.ReportUrl
import intelligent_booking.intelligent_booking.report.controller.response.ReportResponse
import intelligent_booking.intelligent_booking.report.dto.ReportRequest
import intelligent_booking.intelligent_booking.report.eventHandler.ReportEventHandler
import intelligent_booking.intelligent_booking.validator.validateBinding
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReportController @Autowired constructor(
    private val reportEventHandler: ReportEventHandler
) {
    @PostMapping(ReportUrl.REPORT)
    fun report(
        @RequestBody @Valid reportRequest: ReportRequest,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        reportEventHandler.reportMember(reportRequest)
        logger().info(ReportControllerLog.REPORT_SUCCESS.log)

        return ReportResponse.reportSuccess()
    }
}