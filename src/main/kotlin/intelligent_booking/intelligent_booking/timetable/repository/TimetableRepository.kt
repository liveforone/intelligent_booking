package intelligent_booking.intelligent_booking.timetable.repository

import intelligent_booking.intelligent_booking.place.domain.Place
import intelligent_booking.intelligent_booking.timetable.domain.Timetable
import intelligent_booking.intelligent_booking.timetable.repository.constant.TimetableStaticQuery
import intelligent_booking.intelligent_booking.timetable.repository.constant.TimetableQueryParam
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface TimetableRepository : JpaRepository<Timetable, Long>, TimetableCustomRepository {
    @Modifying(clearAutomatically = true)
    @Query(TimetableStaticQuery.RESTORE_REMAINING_COUNT)
    fun restoreCount()

    @Modifying(clearAutomatically = true)
    @Query(TimetableStaticQuery.DELETE_BY_PLACE)
    fun deleteBulkByPlace(@Param(TimetableQueryParam.PLACE) place: Place)
}