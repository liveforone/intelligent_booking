package intelligent_booking.intelligent_booking.reservation.controller.constant

object ReservationUrl {
    const val DETAIL = "/reservation/detail/{uuid}"
    const val MEMBER_RESERVATIONS = "/reservation/member/{memberUUID}"
    const val TIMETABLE_RESERVATIONS = "/reservation/timetable/{timetableUUID}"
    const val RESERVATION = "/reservation/create"
    const val CANCEL = "/reservation/cancel/{uuid}"
}