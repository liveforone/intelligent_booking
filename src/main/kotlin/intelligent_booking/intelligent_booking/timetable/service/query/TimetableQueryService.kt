package intelligent_booking.intelligent_booking.timetable.service.query

import intelligent_booking.intelligent_booking.timetable.repository.TimetableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class TimetableQueryService @Autowired constructor(
    private val timetableRepository: TimetableRepository
) {
    fun getTimetableByUUID(uuid: UUID) = timetableRepository.findOneDtoByUUID(uuid)
    fun getTimetablesByPlace(placeUUID: UUID, lastUUID: UUID?) = timetableRepository.findTimetablesByPlace(placeUUID, lastUUID)
}