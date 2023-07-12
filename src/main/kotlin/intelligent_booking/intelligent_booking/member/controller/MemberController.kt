package intelligent_booking.intelligent_booking.member.controller

import intelligent_booking.intelligent_booking.jwt.constant.JwtConstant
import intelligent_booking.intelligent_booking.logger
import intelligent_booking.intelligent_booking.member.controller.constant.MemberControllerLog
import intelligent_booking.intelligent_booking.member.controller.constant.MemberParam
import intelligent_booking.intelligent_booking.member.controller.constant.MemberUrl
import intelligent_booking.intelligent_booking.member.controller.response.MemberResponse
import intelligent_booking.intelligent_booking.member.dto.request.LoginRequest
import intelligent_booking.intelligent_booking.member.dto.request.SignupRequest
import intelligent_booking.intelligent_booking.member.dto.request.WithdrawRequest
import intelligent_booking.intelligent_booking.member.dto.update.UpdateEmail
import intelligent_booking.intelligent_booking.member.dto.update.UpdatePassword
import intelligent_booking.intelligent_booking.member.service.command.MemberCommandService
import intelligent_booking.intelligent_booking.member.service.query.MemberQueryService
import intelligent_booking.intelligent_booking.validator.validateBinding
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.*

@RestController
class MemberController @Autowired constructor(
    private val memberQueryService: MemberQueryService,
    private val memberCommandService: MemberCommandService
) {

    @GetMapping(MemberUrl.INFO)
    fun memberInfo(principal: Principal): ResponseEntity<*> {
        val member = memberQueryService.getOneByUUID(uuid = UUID.fromString(principal.name))
        return MemberResponse.infoSuccess(member)
    }

    @PostMapping(MemberUrl.SIGNUP_MEMBER)
    fun signupMember(
        @RequestBody @Valid signupRequest: SignupRequest,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        memberCommandService.signupMember(signupRequest)
        logger().info(MemberControllerLog.SIGNUP_SUCCESS.log)

        return MemberResponse.signupSuccess()
    }

    @PostMapping(MemberUrl.SIGNUP_PRESIDENT)
    fun signupPresident(
        @RequestBody @Valid signupRequest: SignupRequest,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        memberCommandService.signupPresident(signupRequest)
        logger().info(MemberControllerLog.SIGNUP_SUCCESS.log)

        return MemberResponse.signupSuccess()
    }

    @PostMapping(MemberUrl.LOGIN)
    fun login(
        @RequestBody @Valid loginRequest: LoginRequest,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        val tokenInfo = memberCommandService.login(loginRequest)
        response.apply {
            addHeader(JwtConstant.ACCESS_TOKEN, tokenInfo.accessToken)
            addHeader(JwtConstant.REFRESH_TOKEN, tokenInfo.refreshToken)
        }
        logger().info(MemberControllerLog.LOGIN_SUCCESS.log)

        return MemberResponse.loginSuccess()
    }

    @PutMapping(MemberUrl.UPDATE_EMAIL)
    fun updateEmail(
        @PathVariable(MemberParam.UUID) uuid: UUID,
        @RequestBody @Valid updateEmail: UpdateEmail,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        memberCommandService.updateEmail(updateEmail, uuid)
        logger().info(MemberControllerLog.UPDATE_EMAIL_SUCCESS.log)

        return MemberResponse.updateEmailSuccess()
    }

    @PutMapping(MemberUrl.UPDATE_PASSWORD)
    fun updatePassword(
        @PathVariable(MemberParam.UUID) uuid: UUID,
        @RequestBody @Valid updatePassword: UpdatePassword,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        memberCommandService.updatePassword(updatePassword, uuid)
        logger().info(MemberControllerLog.UPDATE_PW_SUCCESS.log)

        return MemberResponse.updatePwSuccess()
    }

    @DeleteMapping(MemberUrl.WITHDRAW)
    fun withdraw(
        @PathVariable(MemberParam.UUID) uuid: UUID,
        @RequestBody @Valid withdrawRequest: WithdrawRequest,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        memberCommandService.withdraw(withdrawRequest, uuid)
        logger().info(MemberControllerLog.WITHDRAW_SUCCESS.log)

        return MemberResponse.withdrawSuccess()
    }
}