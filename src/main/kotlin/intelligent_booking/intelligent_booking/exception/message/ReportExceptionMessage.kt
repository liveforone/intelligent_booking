package intelligent_booking.intelligent_booking.exception.message

enum class ReportExceptionMessage(val status: Int, val message: String) {
    RESERVATION_IS_CANCELED(400, "취소된 예약입니다."),
    RESERVATION_IS_NOT_START(400, "예약이 시작되지 않았습니다."),
    OVER_REPORT_DAY(400, "취소 가능날짜가 지났습니다.")
}