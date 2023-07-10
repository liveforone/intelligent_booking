package intelligent_booking.intelligent_booking.exception.message

enum class PlaceExceptionMessage(val status: Int, val message: String) {
    NOT_PRESIDENT(403, "사장 권한을 가진 회원만 접근 가능합니다."),
    PLACE_IS_NULL(404, "장소가 존재하지 않습니다.")
}