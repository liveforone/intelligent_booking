package intelligent_booking.intelligent_booking.reservation.domain

import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.member.domain.Role
import intelligent_booking.intelligent_booking.place.domain.Address
import intelligent_booking.intelligent_booking.place.domain.Place
import intelligent_booking.intelligent_booking.timetable.domain.Timetable
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ReservationTest {

    private fun createMember(email: String, auth: Role): Member {
        return Member.create(email, "1234", auth)
    }

    private fun createPlace(): Place {
        return Place.create(createMember("domain_test@gmail.com", Role.PRESIDENT), "test_place", "0212345678", Address("서울", "잠실-1-1", "101동 101호"))
    }

    private fun createTimetable(): Timetable {
        return Timetable.create(createPlace(), 5, 12, 35, "test_description")
    }

    @Test
    fun cancel() {
        //given
        val member = createMember("reservation@gmail.com", Role.MEMBER)
        val reservation = Reservation.create(createTimetable(), member)

        //when
        reservation.cancel()

        //then
        Assertions.assertThat(reservation.reservationState).isEqualTo(ReservationState.CANCEL)
    }
}