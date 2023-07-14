package intelligent_booking.intelligent_booking.report.controller.response

import org.springframework.http.ResponseEntity

object ReportResponse {
    private const val REPORT_SUCCESS = "신고를 성공적으로 완료하였습니다."
    fun reportSuccess() = ResponseEntity.ok(REPORT_SUCCESS)
}