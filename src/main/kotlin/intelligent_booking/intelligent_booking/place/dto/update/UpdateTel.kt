package intelligent_booking.intelligent_booking.place.dto.update

import jakarta.validation.constraints.NotBlank

data class UpdateTel(
    @field:NotBlank(message = "변경할 전화번호를 입력하세요.")
    var tel: String?
)
