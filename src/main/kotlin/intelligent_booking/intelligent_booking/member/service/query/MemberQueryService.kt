package intelligent_booking.intelligent_booking.member.service.query

import intelligent_booking.intelligent_booking.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class MemberQueryService @Autowired constructor(
    private val memberRepository: MemberRepository
) {
    fun getOneByUUID(uuid: UUID) = memberRepository.findOneDtoByUUID(uuid)
}