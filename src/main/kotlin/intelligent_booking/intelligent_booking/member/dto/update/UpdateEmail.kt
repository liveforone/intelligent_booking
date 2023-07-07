package intelligent_booking.intelligent_booking.member.dto.update

import jakarta.validation.constraints.NotBlank

data class UpdateEmail(
    @field:NotBlank(message = "변경할 이메일을 입력하세요.")
    var newEmail: String?
)
