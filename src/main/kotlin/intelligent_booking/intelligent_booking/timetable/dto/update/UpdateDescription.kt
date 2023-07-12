package intelligent_booking.intelligent_booking.timetable.dto.update

import jakarta.validation.constraints.NotBlank

data class UpdateDescription(
    @field:NotBlank(message = "변경할 설명을 입력하세요.")
    var description: String?
)
