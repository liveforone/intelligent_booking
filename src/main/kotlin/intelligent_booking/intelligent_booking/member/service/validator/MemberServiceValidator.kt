package intelligent_booking.intelligent_booking.member.service.validator

import intelligent_booking.intelligent_booking.exception.exception.MemberException
import intelligent_booking.intelligent_booking.exception.message.MemberExceptionMessage
import intelligent_booking.intelligent_booking.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MemberServiceValidator @Autowired constructor(
    private val memberRepository: MemberRepository
) {

    fun validateDuplicateEmail(email: String) {
        check(memberRepository.findIdByEmailNullableForValidate(email) == null) {
            throw MemberException(MemberExceptionMessage.DUPLICATE_EMAIL)
        }
    }
}