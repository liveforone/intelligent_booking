package intelligent_booking.intelligent_booking.timetable.service.command

import intelligent_booking.intelligent_booking.member.dto.request.SignupRequest
import intelligent_booking.intelligent_booking.member.service.command.MemberCommandService
import intelligent_booking.intelligent_booking.place.dto.request.CreatePlace
import intelligent_booking.intelligent_booking.place.service.command.PlaceCommandService
import intelligent_booking.intelligent_booking.timetable.dto.request.CreateTimetable
import intelligent_booking.intelligent_booking.timetable.dto.update.UpdateDescription
import intelligent_booking.intelligent_booking.timetable.service.query.TimetableQueryService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
class TimetableCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val placeCommandService: PlaceCommandService,
    private val timetableCommandService: TimetableCommandService,
    private val timetableQueryService: TimetableQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    private fun createMember(): UUID {
        val email = "test_place@gmail.com"
        val pw = "1234"
        val request = SignupRequest(email, pw)

        //when
        val uuid = memberCommandService.signupPresident(request)
        flushAndClear()
        return uuid
    }

    private fun createPlace(memberUUID: UUID): UUID {
        val request = CreatePlace("test_place1", "01012345678", "서울", "잠실-1-1", "101동 101호")
        val uuid = placeCommandService.createPlace(request, memberUUID)
        flushAndClear()
        return uuid
    }

    @Test
    @Transactional
    fun createTimetableTest() {
        val memberUUID = createMember()
        val placeUUID = createPlace(memberUUID)
        val basicCount: Long = 5
        val reservationHour: Long = 12
        val reservationMinute: Long = 35
        val description = "test_description"

        //when
        val request = CreateTimetable(placeUUID, basicCount, reservationHour, reservationMinute, description)
        val uuid = timetableCommandService.createTimetable(request)
        flushAndClear()

        //then
        Assertions.assertThat(timetableQueryService.getTimetableByUUID(uuid).remainingCount)
            .isEqualTo(basicCount)
    }

    @Test
    @Transactional
    fun subtractCountTest() {
        //given
        val memberUUID = createMember()
        val placeUUID = createPlace(memberUUID)
        val basicCount: Long = 5
        val reservationHour: Long = 12
        val reservationMinute: Long = 35
        val description = "test_description"
        val request = CreateTimetable(placeUUID, basicCount, reservationHour, reservationMinute, description)
        val uuid = timetableCommandService.createTimetable(request)

        //when
        timetableCommandService.subtractCount(uuid)
        flushAndClear()

        //then
        Assertions.assertThat(timetableQueryService.getTimetableByUUID(uuid).remainingCount)
            .isEqualTo(basicCount - 1)
    }

    @Test
    @Transactional
    fun updateDescriptionTest() {
        //given
        val memberUUID = createMember()
        val placeUUID = createPlace(memberUUID)
        val basicCount: Long = 5
        val reservationHour: Long = 12
        val reservationMinute: Long = 35
        val description = "test_description"
        val request = CreateTimetable(placeUUID, basicCount, reservationHour, reservationMinute, description)
        val uuid = timetableCommandService.createTimetable(request)

        //when
        val updatedDescription = "updated_description"
        val updateRequest = UpdateDescription(updatedDescription)
        timetableCommandService.updateDescription(updateRequest, uuid)
        flushAndClear()

        //then
        Assertions.assertThat(timetableQueryService.getTimetableByUUID(uuid).description)
            .isEqualTo(updatedDescription)
    }

    @Test
    @Transactional
    fun restoreCountTest() {
        //given
        val memberUUID = createMember()
        val placeUUID = createPlace(memberUUID)
        val basicCount: Long = 5
        val reservationHour: Long = 12
        val reservationMinute: Long = 35
        val description = "test_description"
        val request = CreateTimetable(placeUUID, basicCount, reservationHour, reservationMinute, description)
        val uuid = timetableCommandService.createTimetable(request)

        //when
        repeat(4) {
            timetableCommandService.subtractCount(uuid)
            flushAndClear()
        }
        timetableCommandService.restoreCount()
        flushAndClear()

        //then
        Assertions.assertThat(timetableQueryService.getTimetableByUUID(uuid).remainingCount)
            .isEqualTo(basicCount)
    }
}