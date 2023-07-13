package intelligent_booking.intelligent_booking.exception.exception

import intelligent_booking.intelligent_booking.exception.message.ReservationExceptionMessage

class ReservationException(val reservationExceptionMessage: ReservationExceptionMessage) : RuntimeException(reservationExceptionMessage.message)