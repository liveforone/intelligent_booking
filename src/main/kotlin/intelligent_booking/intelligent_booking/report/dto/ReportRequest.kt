package intelligent_booking.intelligent_booking.report.dto

import jakarta.validation.constraints.NotNull
import java.util.*

data class ReportRequest(
    @field:NotNull(message = "예약의 외부식별자를 입력하세요.")
    var reservationUUID: UUID?,
    @field:NotNull(message = "회원의 외부식별자를 입력하세요.")
    var memberUUID: UUID?,
    @field:NotNull(message = "타임테이블의 외부식별자를 입력하세요.")
    var timetableUUID: UUID?
)
