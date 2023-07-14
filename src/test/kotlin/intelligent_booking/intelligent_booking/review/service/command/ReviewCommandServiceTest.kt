package intelligent_booking.intelligent_booking.review.service.command

import intelligent_booking.intelligent_booking.member.dto.request.SignupRequest
import intelligent_booking.intelligent_booking.member.service.command.MemberCommandService
import intelligent_booking.intelligent_booking.place.dto.request.CreatePlace
import intelligent_booking.intelligent_booking.place.service.command.PlaceCommandService
import intelligent_booking.intelligent_booking.reservation.dto.request.CreateReservation
import intelligent_booking.intelligent_booking.reservation.service.command.ReservationCommandService
import intelligent_booking.intelligent_booking.review.dto.request.CreateReview
import intelligent_booking.intelligent_booking.review.service.query.ReviewQueryService
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
@Transactional
class ReviewCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val placeCommandService: PlaceCommandService,
    private val timetableCommandService: TimetableCommandService,
    private val reservationCommandService: ReservationCommandService,
    private val reviewCommandService: ReviewCommandService,
    private val reviewQueryService: ReviewQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    private fun createPresident(email: String): UUID {
        val pw = "1234"
        val request = SignupRequest(email, pw)
        val uuid = memberCommandService.signupPresident(request)
        flushAndClear()
        return uuid
    }

    private fun createMember(email: String): UUID {
        val pw = "1234"
        val request = SignupRequest(email, pw)
        val uuid = memberCommandService.signupMember(request)
        flushAndClear()
        return uuid
    }

    private fun createPlace(memberUUID: UUID): UUID {
        val request = CreatePlace("test_place", "01012345678", "서울", "잠실-1-1", "101동 101호")
        val uuid = placeCommandService.createPlace(request, memberUUID)
        flushAndClear()
        return uuid
    }

    private fun createTimetable(placeUUID: UUID): UUID {
        val request = CreateTimetable(placeUUID, 5, LocalDateTime.now().plusHours(1).hour.toLong(), 35, "test_description")
        val uuid = timetableCommandService.createTimetable(request)
        flushAndClear()
        return uuid
    }

    private fun createReservation(timetableUUID: UUID, memberUUID: UUID): UUID {
        val request = CreateReservation(timetableUUID, memberUUID)
        val uuid = reservationCommandService.createReservation(request)
        flushAndClear()
        return uuid
    }

    @Test
    fun createReview() {
        //given
        val presidentEmail = "test_president@gmail.com"
        val presidentUUID = createPresident(presidentEmail)
        val placeUUID = createPlace(presidentUUID)
        val timetableUUID = createTimetable(placeUUID)
        val email = "test_review@gmail.com"
        val memberUUID = createMember(email)
        val reservationUUID = createReservation(timetableUUID, memberUUID)
        val content = "test_content"

        //when
        val request = CreateReview(reservationUUID, placeUUID, memberUUID, content)
        val reviewUUID = reviewCommandService.createReview(request)
        flushAndClear()

        //then
        Assertions.assertThat(reviewQueryService.getReviewByUUID(reviewUUID).content)
            .isEqualTo(content)
    }
}