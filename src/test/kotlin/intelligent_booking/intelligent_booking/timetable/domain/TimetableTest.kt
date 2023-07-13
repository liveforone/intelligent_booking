package intelligent_booking.intelligent_booking.timetable.domain

import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.member.domain.Role
import intelligent_booking.intelligent_booking.place.domain.Address
import intelligent_booking.intelligent_booking.place.domain.Place
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TimetableTest {

    private fun createMember(): Member {
        return Member.create("domain_test@gmail.com", "1234", Role.PRESIDENT)
    }

    private fun createPlace(): Place {
        return Place.create(createMember(), "test_place", "0212345678", Address("서울", "잠실-1-1", "101동 101호"))
    }

    @Test
    fun subtractCountTest() {
        //given
        val place = createPlace()
        val basicCount: Long = 5
        val reservationHour: Long = 12
        val reservationMinute: Long = 35
        val description = "test_description"
        val timetable = Timetable.create(place, basicCount, reservationHour, reservationMinute, description)

        //when
        timetable.subtractCount()

        //then
        Assertions.assertThat(timetable.remainingCount).isEqualTo(basicCount - 1)
    }

    @Test
    fun restoreOneCountTest() {
        //given
        val place = createPlace()
        val basicCount: Long = 5
        val reservationHour: Long = 12
        val reservationMinute: Long = 35
        val description = "test_description"
        val timetable = Timetable.create(place, basicCount, reservationHour, reservationMinute, description)
        timetable.subtractCount()

        //when
        timetable.restoreOneCount()

        //then
        Assertions.assertThat(timetable.remainingCount).isEqualTo(basicCount)
    }

    @Test
    fun updateDescriptionTest() {
        //given
        val place = createPlace()
        val basicCount: Long = 5
        val reservationHour: Long = 12
        val reservationMinute: Long = 35
        val description = "test_description"
        val timetable = Timetable.create(place, basicCount, reservationHour, reservationMinute, description)

        //when
        val updatedDescription = "updated_description"
        timetable.updateDescription(updatedDescription)

        //then
        Assertions.assertThat(timetable.description).isEqualTo(updatedDescription)
    }
}