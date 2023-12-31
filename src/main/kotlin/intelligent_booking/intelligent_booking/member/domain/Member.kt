package intelligent_booking.intelligent_booking.member.domain

import intelligent_booking.intelligent_booking.converter.RoleConverter
import intelligent_booking.intelligent_booking.exception.exception.MemberException
import intelligent_booking.intelligent_booking.exception.message.MemberExceptionMessage
import intelligent_booking.intelligent_booking.globalUtil.UUID_TYPE
import intelligent_booking.intelligent_booking.globalUtil.createUUID
import intelligent_booking.intelligent_booking.member.domain.constant.MemberConstant
import intelligent_booking.intelligent_booking.member.domain.util.PasswordUtil
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
class Member private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(columnDefinition = UUID_TYPE, unique = true, nullable = false) val uuid: UUID = createUUID(),
    @Convert(converter = RoleConverter::class) @Column(nullable = false) var auth: Role,
    @Column(nullable = false) var email: String,
    @Column(nullable = false) var pw: String,
    @Column(nullable = false) var report: Long
) : UserDetails {

    companion object {
        private fun isAdmin(email: String) = (email == MemberConstant.ADMIN_EMAIL)

        fun create(email: String, pw: String, auth: Role): Member {
            return Member(
                id = null,
                auth = if (isAdmin(email)) Role.ADMIN else auth,
                email = email,
                pw = PasswordUtil.encodePassword(pw),
                report = MemberConstant.BASIC_REPORT
            )
        }
    }

    fun isNotSuspend() = auth != Role.SUSPEND

    fun isPresident() = auth == Role.PRESIDENT

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun updatePw(newPassword: String, oldPassword: String) {
        require (PasswordUtil.isMatchPassword(oldPassword, pw)) { throw MemberException(MemberExceptionMessage.WRONG_PASSWORD) }
        pw = PasswordUtil.encodePassword(newPassword)
    }

    fun addReport() {
        if (report > MemberConstant.SUSPEND_LIMIT) auth = Role.SUSPEND
        else report += MemberConstant.BASIC_VARIATION
    }


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return arrayListOf<GrantedAuthority>(SimpleGrantedAuthority(auth.auth))
    }
    override fun getPassword() = pw
    override fun getUsername() = uuid.toString()
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}