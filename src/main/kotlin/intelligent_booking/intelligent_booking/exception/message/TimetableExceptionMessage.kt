package intelligent_booking.intelligent_booking.exception.message

enum class TimetableExceptionMessage(val status: Int, val message: String) {
    REMAINING_COUNT_CANT_BE_MINUS(400, "잔여 예약이 남지 않았습니다."),
    TIMETABLE_IS_NULL(404, "타입테이블이 존재하지 않습니다.")
}