package intelligent_booking.intelligent_booking.member.service.command

import intelligent_booking.intelligent_booking.exception.exception.MemberException
import intelligent_booking.intelligent_booking.exception.message.MemberExceptionMessage
import intelligent_booking.intelligent_booking.jwt.JwtTokenProvider
import intelligent_booking.intelligent_booking.jwt.TokenInfo
import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.member.domain.util.PasswordUtil
import intelligent_booking.intelligent_booking.member.dto.request.LoginRequest
import intelligent_booking.intelligent_booking.member.dto.request.SignupRequest
import intelligent_booking.intelligent_booking.member.dto.request.WithdrawRequest
import intelligent_booking.intelligent_booking.member.dto.update.UpdateEmail
import intelligent_booking.intelligent_booking.member.dto.update.UpdatePassword
import intelligent_booking.intelligent_booking.member.repository.MemberRepository
import intelligent_booking.intelligent_booking.member.service.validator.MemberServiceValidator
import intelligent_booking.intelligent_booking.member.domain.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class MemberCommandService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberServiceValidator: MemberServiceValidator
) {

    fun signupMember(signupRequest: SignupRequest): UUID {
        return with(signupRequest) {
            memberServiceValidator.validateDuplicateEmail(email!!)
            Member.create(email!!, pw!!, auth = Role.MEMBER)
                .run { memberRepository.save(this).uuid }
        }
    }

    fun signupPresident(signupRequest: SignupRequest): UUID {
        return with(signupRequest) {
            memberServiceValidator.validateDuplicateEmail(email!!)
            Member.create(email!!, pw!!, auth = Role.PRESIDENT)
                .run { memberRepository.save(this).uuid }
        }
    }

    fun login(loginRequest: LoginRequest): TokenInfo {
        val authentication: Authentication = authenticationManagerBuilder
            .`object`
            .authenticate(UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.pw))

        return jwtTokenProvider.generateToken(authentication)
    }

    fun updateEmail(updateEmail: UpdateEmail, uuid: UUID) {
        memberServiceValidator.validateDuplicateEmail(updateEmail.newEmail!!)
        memberRepository.findOneByUuid(uuid)
            .also { it.updateEmail(updateEmail.newEmail!!) }
    }

    fun updatePassword(updatePassword: UpdatePassword, uuid: UUID) {
        with(updatePassword) {
            memberRepository.findOneByUuid(uuid)
                .also { it.updatePw(newPassword!!, oldPassword!!) }
        }
    }

    fun addReport(uuid: UUID) {
        memberRepository.findOneByUuid(uuid)
            .also { it.addReport() }
    }

    fun withdraw(withdrawRequest: WithdrawRequest, uuid: UUID) {
        memberRepository.findOneByUuid(uuid)
            .takeIf { PasswordUtil.isMatchPassword(withdrawRequest.pw!!, it.pw) }
            ?.also { memberRepository.delete(it) }
            ?: throw MemberException(MemberExceptionMessage.WRONG_PASSWORD)
    }
}