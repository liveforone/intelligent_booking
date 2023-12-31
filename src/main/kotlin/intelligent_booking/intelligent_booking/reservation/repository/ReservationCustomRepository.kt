package intelligent_booking.intelligent_booking.reservation.repository

import intelligent_booking.intelligent_booking.reservation.domain.Reservation
import intelligent_booking.intelligent_booking.reservation.dto.response.ReservationInfo
import java.util.UUID

interface ReservationCustomRepository {
    fun findOneByUUID(uuid: UUID): Reservation
    fun findOneDtoByUUID(uuid: UUID): ReservationInfo
    fun findMemberUUIDByUUID(uuid: UUID): UUID?
    fun findReservationsByMember(memberUUID: UUID, lastUUID: UUID?): List<ReservationInfo>
    fun findReservationsByTimetable(timetableUUID: UUID, lastUUID: UUID?): List<ReservationInfo>
}