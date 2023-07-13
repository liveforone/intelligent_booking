package intelligent_booking.intelligent_booking.exception.message

enum class ReservationExceptionMessage(val status: Int, val message: String) {
    RESERVATION_IS_NULL(404, "예약이 존재하지 않습니다."),
    OVER_RESERVATION_TIME(400, "예약 가능 시간을 초과하여 예약이 불가능합니다."),
    OVER_CANCEL_TIME(400, "예약 취소 가능 시간을 초과하여 예약취소가 불가능합니다.")
}