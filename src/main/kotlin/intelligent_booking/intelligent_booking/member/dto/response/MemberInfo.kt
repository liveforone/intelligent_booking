package intelligent_booking.intelligent_booking.member.dto.response

import intelligent_booking.intelligent_booking.member.domain.Role

data class MemberInfo(
    val id: Long?,
    val email: String,
    val auth: Role
)