package intelligent_booking.intelligent_booking.timetable.repository.constant

object TimetableStaticQuery {
    const val RESTORE_REMAINING_COUNT = "UPDATE Timetable t SET t.remainingCount = t.basicCount"
    const val DELETE_BY_PLACE = "DELETE from Timetable t where t.place = :place"
}