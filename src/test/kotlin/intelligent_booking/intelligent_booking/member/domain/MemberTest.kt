package intelligent_booking.intelligent_booking.member.domain

import intelligent_booking.intelligent_booking.member.domain.util.PasswordUtil
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MemberTest {

    @Test
    fun updateEmailTest() {
        //given
        val email = "test1234@gmail.com"
        val pw = "1234"
        val member = Member.create(email, pw, Role.MEMBER)

        //when
        val newEmail = "test_update_email@gmail.com"
        member.updateEmail(newEmail)

        //then
        Assertions.assertThat(member.email).isEqualTo(newEmail)
    }

    @Test
    fun updatePwTest() {
        //given
        val email = "test1234@gmail.com"
        val pw = "1234"
        val member = Member.create(email, pw, Role.MEMBER)

        //when
        val newPw = "1111"
        member.updatePw(newPw, pw)

        //then
        Assertions.assertThat(PasswordUtil.isMatchPassword(newPw, member.pw)).isTrue()
    }

    @Test
    fun addReportTest() {
        //given
        val email = "test1234@gmail.com"
        val pw = "1234"
        val member = Member.create(email, pw, Role.MEMBER)

        //when
        member.addReport()

        //then
        Assertions.assertThat(member.report).isEqualTo(1)
    }

    @Test
    fun suspendTest() {
        //given
        val email = "test1234@gmail.com"
        val pw = "1234"
        val member = Member.create(email, pw, Role.MEMBER)

        //when
        repeat(11) { member.addReport() }

        //then
        Assertions.assertThat(member.auth).isEqualTo(Role.SUSPEND)
    }
}