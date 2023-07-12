package intelligent_booking.intelligent_booking.timetable.dto.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateTimetable(
    @field:NotNull(message = "장소의 uuid(외부식별자)를 입력하세요.")
    var placeUUID: UUID?,
    @field:NotNull(message = "해당 시간대에 예약가능한 사람 수를 입력하세요.")
    @field:Min(1, message = "최소 예약자 수는 1명입니다.")
    var basicCount: Long?,
    @field:NotNull(message = "00시부터 24시까지 예약 가능한 시를 입력하세요.")
    @field:Max(24, message = "입력가능한 최대 시는 24시입니다.")
    @field:Min(0, message = "입력가능한 최소 시는 0시입니다.")
    var reservationHour: Long?,
    @field:NotNull(message = "예약 가능한 분을 입력하세요.")
    @field:Max(60, message = "입력가능한 최대 분은 60분입니다.")
    @field:Min(0, message = "입력가능한 최소 분은 0분입니다.")
    var reservationMinute: Long?,
    @field:NotBlank(message = "설명을 입력하세요.")
    var description: String?
)
