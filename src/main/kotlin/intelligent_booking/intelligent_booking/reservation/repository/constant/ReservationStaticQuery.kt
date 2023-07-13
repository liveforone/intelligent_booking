package intelligent_booking.intelligent_booking.reservation.repository.constant

object ReservationStaticQuery {
    const val DELETE_BY_MEMBER = "DELETE from Reservation r where r.member = :member"
}