package intelligent_booking.intelligent_booking.timetable.dto.response

import java.util.*

data class TimetableInfo(
    val uuid: UUID,
    val placeUUID: UUID,
    val remainingCount: Long,
    val reservationHour: Long,
    val reservationMinute: Long,
    val description: String
)
