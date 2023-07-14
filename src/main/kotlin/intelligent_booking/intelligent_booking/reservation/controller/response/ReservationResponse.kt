package intelligent_booking.intelligent_booking.reservation.controller.response

import intelligent_booking.intelligent_booking.reservation.dto.response.ReservationInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ReservationResponse {
    private const val RESERVATION_SUCCESS = "성공적으로 예약하였습니다."
    private const val CANCEL_RESERVATION_SUCCESS = "예약을 성공적으로 취소하였습니다."

    fun reservationDetailSuccess(reservation: ReservationInfo) = ResponseEntity.ok(reservation)
    fun memberReservationSuccess(reservations: List<ReservationInfo>) = ResponseEntity.ok(reservations)
    fun timetableReservationSuccess(reservations: List<ReservationInfo>) = ResponseEntity.ok(reservations)
    fun reservationSuccess() = ResponseEntity.status(HttpStatus.CREATED).body(RESERVATION_SUCCESS)
    fun cancelReservationSuccess() = ResponseEntity.ok(CANCEL_RESERVATION_SUCCESS)
}