package intelligent_booking.intelligent_booking.reservation.service.command

import intelligent_booking.intelligent_booking.member.dto.request.SignupRequest
import intelligent_booking.intelligent_booking.member.service.command.MemberCommandService
import intelligent_booking.intelligent_booking.place.dto.request.CreatePlace
import intelligent_booking.intelligent_booking.place.service.command.PlaceCommandService
import intelligent_booking.intelligent_booking.reservation.domain.ReservationState
import intelligent_booking.intelligent_booking.reservation.dto.request.CreateReservation
import intelligent_booking.intelligent_booking.reservation.service.query.ReservationQueryService
import intelligent_booking.intelligent_booking.timetable.dto.request.CreateTimetable
import intelligent_booking.intelligent_booking.timetable.service.command.TimetableCommandService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
class ReservationCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val placeCommandService: PlaceCommandService,
    private val timetableCommandService: TimetableCommandService,
    private val reservationCommandService: ReservationCommandService,
    private val reservationQueryService: ReservationQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    private fun createMember(email: String): UUID {
        val pw = "1234"
        val request = SignupRequest(email, pw)

        //when
        val uuid = memberCommandService.signupPresident(request)
        flushAndClear()
        return uuid
    }

    private fun createPlace(memberUUID: UUID): UUID {
        val request = CreatePlace("test_place", "01012345678", "서울", "잠실-1-1", "101동 101호")
        val uuid = placeCommandService.createPlace(request, memberUUID)
        flushAndClear()
        return uuid
    }

    private fun createTimetable(): UUID {
        val request = CreateTimetable(createPlace(createMember("test_place@gmail.com")), 5, LocalDateTime.now().plusHours(1).hour.toLong(), 35, "test_description")
        val uuid = timetableCommandService.createTimetable(request)
        flushAndClear()
        return uuid
    }

    @Test
    @Transactional
    fun createReservation() {
        //given
        val timetableUUID = createTimetable()
        val email = "test_reservation@gmail.com"
        val memberUUID = createMember(email)

        //when
        val request = CreateReservation(timetableUUID, memberUUID)
        val uuid = reservationCommandService.createReservation(request)
        flushAndClear()

        //then
        Assertions.assertThat(reservationQueryService.getReservationByUUID(uuid).reservationState)
            .isEqualTo(ReservationState.RESERVATION)
    }

    @Test
    @Transactional
    fun cancelReservation() {
        //given
        val timetableUUID = createTimetable()
        val email = "test_reservation@gmail.com"
        val memberUUID = createMember(email)
        val request = CreateReservation(timetableUUID, memberUUID)
        val uuid = reservationCommandService.createReservation(request)
        flushAndClear()

        //when
        reservationCommandService.cancelReservation(uuid)
        flushAndClear()

        //then
        Assertions.assertThat(reservationQueryService.getReservationByUUID(uuid).reservationState)
            .isEqualTo(ReservationState.CANCEL)
    }
}