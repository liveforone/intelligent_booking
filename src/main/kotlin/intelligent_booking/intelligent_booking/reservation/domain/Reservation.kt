package intelligent_booking.intelligent_booking.reservation.domain

import intelligent_booking.intelligent_booking.converter.ReservationStateConverter
import intelligent_booking.intelligent_booking.exception.exception.ReservationException
import intelligent_booking.intelligent_booking.exception.message.ReservationExceptionMessage
import intelligent_booking.intelligent_booking.globalUtil.UUID_TYPE
import intelligent_booking.intelligent_booking.globalUtil.createUUID
import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.timetable.domain.Timetable
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
class Reservation private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(columnDefinition = UUID_TYPE, unique = true, nullable = false) val uuid: UUID = createUUID(),
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(updatable = false) val timetable: Timetable,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(updatable = false) val member: Member,
    @Convert(converter = ReservationStateConverter::class) @Column(nullable = false) var reservationState: ReservationState = ReservationState.RESERVATION,
    @Column(nullable = false, updatable = false) val createdDateTime: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun create(timetable: Timetable, member: Member): Reservation {
            require(ReservationPolicy.canBeReservation(timetable)) { throw ReservationException(ReservationExceptionMessage.OVER_RESERVATION_TIME) }
            return Reservation(id = null, timetable = timetable, member = member)
        }
    }

    fun cancel() {
        check(ReservationPolicy.canBeCancel(createdDateTime, timetable)) { throw ReservationException(ReservationExceptionMessage.OVER_CANCEL_TIME) }
        reservationState = ReservationState.CANCEL
    }
}