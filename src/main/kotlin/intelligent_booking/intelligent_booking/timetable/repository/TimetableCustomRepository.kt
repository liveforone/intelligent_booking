package intelligent_booking.intelligent_booking.timetable.repository

import intelligent_booking.intelligent_booking.timetable.domain.Timetable
import intelligent_booking.intelligent_booking.timetable.dto.response.TimetableInfo
import java.util.UUID

interface TimetableCustomRepository {
    fun findOneByUUID(uuid: UUID): Timetable
    fun findOneDtoByUUID(uuid: UUID): TimetableInfo
    fun findTimetablesByPlace(placeUUID: UUID, lastUUID: UUID?): List<TimetableInfo>
}