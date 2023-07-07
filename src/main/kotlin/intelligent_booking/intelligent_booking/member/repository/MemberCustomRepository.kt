package intelligent_booking.intelligent_booking.member.repository

import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.member.dto.response.MemberInfo

interface MemberCustomRepository {
    fun findIdByEmailNullableForValidate(email: String): Long?
    fun findOneByEmail(email: String): Member
    fun findOneByIdentity(identity: String): Member
    fun findOneDtoByIdentity(identity: String): MemberInfo
}