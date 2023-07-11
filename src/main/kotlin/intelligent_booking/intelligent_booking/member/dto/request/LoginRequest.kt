package intelligent_booking.intelligent_booking.member.dto.request

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "이메일을 입력하세요.")
    val email:String?,
    @field:NotBlank(message = "비밀번호를 입력하세요.")
    val pw:String?
)
