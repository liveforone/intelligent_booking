package intelligent_booking.intelligent_booking.timetable.domain

import intelligent_booking.intelligent_booking.exception.exception.TimetableException
import intelligent_booking.intelligent_booking.exception.message.TimetableExceptionMessage
import intelligent_booking.intelligent_booking.globalUtil.UUID_TYPE
import intelligent_booking.intelligent_booking.globalUtil.createUUID
import intelligent_booking.intelligent_booking.place.domain.Place
import jakarta.persistence.*
import java.util.*
import intelligent_booking.intelligent_booking.timetable.domain.constant.TimetableConstant as constant

@Entity
class Timetable private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(columnDefinition = UUID_TYPE, unique = true, nullable = false) val uuid: UUID,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(updatable = false) val place: Place,
    @Column(updatable = false, nullable = false) val basicCount: Long,
    @Column(nullable = false) var remainingCount: Long,
    @Column(updatable = false, nullable = false) val reservationHour: Long,
    @Column(updatable = false, nullable = false) val reservationMinute: Long,
    @Column(nullable = false) var description: String
) {
    companion object {
        fun create(
            place: Place,
            basicCount: Long,
            reservationHour: Long,
            reservationMinute: Long,
            description: String
        ): Timetable {
            return Timetable(
                id = null,
                uuid = createUUID(),
                place,
                basicCount,
                remainingCount = basicCount,
                reservationHour,
                reservationMinute,
                description
            )
        }
    }

    fun subtractCount() {
        check(remainingCount - constant.ONE_COUNT >= constant.SUBTRACT_LIMIT_COUNT) { TimetableException(TimetableExceptionMessage.REMAINING_COUNT_CANT_BE_MINUS) }
        remainingCount -= constant.ONE_COUNT
    }

    fun updateDescription(description: String) {
        this.description = description
    }
}