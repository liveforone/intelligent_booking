package intelligent_booking.intelligent_booking.reservation.controller.constant

object ReservationUrl {
    const val DETAIL = "/reservation/detail/{uuid}"
    const val RESERVATION_BELONG_MEMBER = "/reservation/member/{memberUUID}"
    const val RESERVATION_BELONG_TIMETABLE = "/reservation/timetable/{timetableUUID}"
    const val RESERVATION = "/reservation/create"
    const val CANCEL = "/reservation/cancel/{uuid}"
}