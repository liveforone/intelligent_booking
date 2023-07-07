package intelligent_booking.intelligent_booking.member.service.command

import intelligent_booking.intelligent_booking.member.domain.Role
import intelligent_booking.intelligent_booking.member.dto.request.SignupRequest
import intelligent_booking.intelligent_booking.member.dto.update.UpdateEmail
import intelligent_booking.intelligent_booking.member.service.query.MemberQueryService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class MemberCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val memberQueryService: MemberQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun createMemberTest() {
        //given
        val email = "test1@gmail.com"
        val pw = "1234"
        val request = SignupRequest(email, pw)

        //when
        val identity = memberCommandService.signupMember(request)
        flushAndClear()

        //then
        Assertions.assertThat(memberQueryService.getOneByIdentity(identity).auth)
            .isEqualTo(Role.MEMBER)
    }

    @Test
    @Transactional
    fun createPresidentTest() {
        //given
        val email = "test2@gmail.com"
        val pw = "1234"
        val request = SignupRequest(email, pw)

        //when
        val identity = memberCommandService.signupPresident(request)
        flushAndClear()

        //then
        Assertions.assertThat(memberQueryService.getOneByIdentity(identity).auth)
            .isEqualTo(Role.PRESIDENT)
    }

    @Test
    @Transactional
    fun updateEmailTest() {
        //given
        val email = "test3@gmail.com"
        val pw = "1234"
        val request = SignupRequest(email, pw)
        val identity = memberCommandService.signupMember(request)
        flushAndClear()

        //when
        val newEmail = "test_newEmail@gmail.com"
        val updateRequest = UpdateEmail(newEmail)
        memberCommandService.updateEmail(updateRequest, identity)
        flushAndClear()

        //then
        Assertions.assertThat(memberQueryService.getOneByIdentity(identity).email)
            .isEqualTo(newEmail)
    }
}