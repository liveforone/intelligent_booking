package intelligent_booking.intelligent_booking.exception.message

enum class ReviewExceptionMessage(val status: Int, val message: String) {
    REVIEW_IS_NULL(404, "리뷰가 존재하지 않습니다."),
    RESERVATION_IS_NULL(404, "예약 하지 않은 장소에 대한 리뷰는 불가능합니다."),
    NOT_MATCH_MEMBER(400, "예약한 회원이 아닙니다.")
}