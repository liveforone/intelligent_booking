package intelligent_booking.intelligent_booking.member.dto.response

import intelligent_booking.intelligent_booking.member.domain.Role
import java.util.UUID

data class MemberInfo(
    val uuid: UUID,
    val auth: Role,
    val email: String,
    val report: Long
)