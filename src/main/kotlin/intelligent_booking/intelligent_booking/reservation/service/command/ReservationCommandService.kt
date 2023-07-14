package intelligent_booking.intelligent_booking.reservation.service.command

import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.member.repository.MemberRepository
import intelligent_booking.intelligent_booking.reservation.domain.Reservation
import intelligent_booking.intelligent_booking.reservation.dto.request.CreateReservation
import intelligent_booking.intelligent_booking.reservation.repository.ReservationRepository
import intelligent_booking.intelligent_booking.timetable.repository.TimetableRepository
import intelligent_booking.intelligent_booking.timetable.service.command.TimetableCommandService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class ReservationCommandService @Autowired constructor(
    private val reservationRepository: ReservationRepository,
    private val memberRepository: MemberRepository,
    private val timetableRepository: TimetableRepository,
    private val timetableCommandService: TimetableCommandService
) {
    //timetable_reservation 생성
    fun createReservation(createReservation: CreateReservation): UUID {
        return with(createReservation) {
            Reservation.create(
                timetable = timetableRepository.findOneByUUID(timetableUUID!!),
                member = memberRepository.findOneByUUID(memberUUID!!)
            ).run {
                timetableCommandService.subtractCount(timetableUUID!!)
                reservationRepository.save(this).uuid
            }
        }
    }

    /*
    cancel시 timetable_reservation에서 삭제
     */
    fun cancelReservation(uuid: UUID) {
        reservationRepository.findOneByUUID(uuid)
            .also {
                it.cancel()
                timetableCommandService.restoreOneCount(it.timetable.uuid)
            }
    }
}