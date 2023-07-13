package intelligent_booking.intelligent_booking.reservation.domain

import intelligent_booking.intelligent_booking.timetable.domain.Timetable
import java.time.LocalDateTime

object ReservationPolicy {
    private const val LIMIT_CANCEL_HOUR: Long = 1

    fun canBeReservation(timetable: Timetable): Boolean {
        val nowHour = LocalDateTime.now().hour
        val nowMinute = LocalDateTime.now().minute
        val reservationHour = timetable.reservationHour
        val reservationMinute = timetable.reservationMinute

        if (nowHour == reservationHour.toInt()) {
            return nowMinute < reservationMinute
        }
        return nowHour < reservationHour

    }

    fun canBeCancel(createdDateTime: LocalDateTime, timetable: Timetable): Boolean {
        val nowHour = LocalDateTime.now().hour
        val nowMinute = LocalDateTime.now().minute
        val reservationHour = timetable.reservationHour
        val reservationMinute = timetable.reservationMinute

        val now = LocalDateTime.now()
        if (now >= createdDateTime.plusHours(LIMIT_CANCEL_HOUR)) {
            return false
        }
        if (nowHour == reservationHour.toInt()) {
            return nowMinute < reservationMinute
        }
        return nowHour < reservationHour
    }

}