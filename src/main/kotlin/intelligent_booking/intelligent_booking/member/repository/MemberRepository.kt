package intelligent_booking.intelligent_booking.member.repository

import intelligent_booking.intelligent_booking.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>, MemberCustomRepository