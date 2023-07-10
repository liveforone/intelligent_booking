package intelligent_booking.intelligent_booking.member.repository

import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.member.dto.response.MemberInfo
import java.util.UUID

interface MemberCustomRepository {
    fun findIdByEmailNullableForValidate(email: String): Long?
    fun findOneByEmail(email: String): Member
    fun findOneByUuid(uuid: UUID): Member
    fun findOneDtoByUuid(uuid: UUID): MemberInfo
}