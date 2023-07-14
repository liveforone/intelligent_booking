package intelligent_booking.intelligent_booking.reservation.dto.response

import intelligent_booking.intelligent_booking.reservation.domain.ReservationState
import java.time.LocalDateTime
import java.util.*

data class ReservationInfo(
    val uuid: UUID,
    val timetableUUID: UUID,
    val memberUUID: UUID,
    val reservationState: ReservationState,
    val createdDateTime: LocalDateTime
) {
    fun isReservation() = reservationState == ReservationState.RESERVATION
}
