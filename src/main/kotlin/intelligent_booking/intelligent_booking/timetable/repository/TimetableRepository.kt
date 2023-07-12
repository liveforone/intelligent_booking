package intelligent_booking.intelligent_booking.timetable.repository

import intelligent_booking.intelligent_booking.timetable.domain.Timetable
import intelligent_booking.intelligent_booking.timetable.repository.constant.TimetableQuery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface TimetableRepository : JpaRepository<Timetable, Long>, TimetableCustomRepository {
    @Modifying(clearAutomatically = true)
    @Query(TimetableQuery.RESTORE_REMAINING_COUNT)
    fun restoreCount()
}