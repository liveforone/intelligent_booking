package intelligent_booking.intelligent_booking.reservation.dto.request

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateReservation(
    @field:NotNull(message = "타임테이블의 외부 식별자를 입력하세요.")
    var timetableUUID: UUID?,
    @field:NotNull(message = "회원의 외부 식별자를 입력하세요.")
    var memberUUID: UUID?
)
