package intelligent_booking.intelligent_booking.member.service.command

import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.member.domain.Role
import intelligent_booking.intelligent_booking.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService @Autowired constructor(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        return createUserDetails(member = memberRepository.findOneByEmail(email))
    }

    private fun createUserDetails(member: Member): UserDetails {
        return when (member.auth) {
            Role.ADMIN -> { createAdmin(member) }
            Role.PRESIDENT -> { createPresident(member) }
            else -> { createMember(member) }
        }
    }

    private fun createAdmin(member: Member): UserDetails {
        return User.builder()
            .username(member.uuid.toString())
            .password(member.password)
            .roles(Role.ADMIN.name)
            .build()
    }

    private fun createPresident(member: Member): UserDetails {
        return User.builder()
            .username(member.uuid.toString())
            .password(member.password)
            .roles(Role.PRESIDENT.name)
            .build()
    }

    private fun createMember(member: Member): UserDetails {
        return User.builder()
            .username(member.uuid.toString())
            .password(member.password)
            .roles(Role.MEMBER.name)
            .build()
    }
}