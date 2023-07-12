package intelligent_booking.intelligent_booking.timetable.service.command

import intelligent_booking.intelligent_booking.logger
import intelligent_booking.intelligent_booking.place.repository.PlaceRepository
import intelligent_booking.intelligent_booking.timetable.domain.Timetable
import intelligent_booking.intelligent_booking.timetable.dto.request.CreateTimetable
import intelligent_booking.intelligent_booking.timetable.dto.update.UpdateDescription
import intelligent_booking.intelligent_booking.timetable.repository.TimetableRepository
import intelligent_booking.intelligent_booking.timetable.service.command.constant.TimetableRestorePolicy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class TimetableCommandService @Autowired constructor(
    private val timetableRepository: TimetableRepository,
    private val placeRepository: PlaceRepository
) {

    fun createTimetable(createTimetable: CreateTimetable): UUID {
        return with(createTimetable) {
            Timetable.create(
                place = placeRepository.findOneByUUID(placeUUID!!),
                basicCount!!,
                reservationHour!!,
                reservationMinute!!,
                description!!
            ).run { timetableRepository.save(this).uuid }
        }
    }

    fun subtractCount(uuid: UUID) {
        timetableRepository.findOneByUUID(uuid)
            .also { it.subtractCount() }
    }

    fun updateDescription(updateDescription: UpdateDescription, uuid: UUID) {
        timetableRepository.findOneByUUID(uuid)
            .also { it.updateDescription(updateDescription.description!!) }
    }

    @Scheduled(cron = TimetableRestorePolicy.TIMETABLE_RESTORE_COUNT_POLICY)
    fun restoreCount() {
        timetableRepository.restoreCount()
        logger().info(TimetableRestorePolicy.RESTORE_COUNT_LOG)
    }

    fun deleteTimetable(uuid: UUID) {
        timetableRepository.findOneByUUID(uuid)
            .also { timetableRepository.delete(it) }
    }
}