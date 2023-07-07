package intelligent_booking.intelligent_booking.exception.message

enum class MemberExceptionMessage(val status: Int, val message: String) {
    WRONG_PASSWORD(400, "비밀번호를 틀렸습니다."),
    MEMBER_IS_NULL(404, "회원이 존재하지 않습니다."),
    DUPLICATE_EMAIL(400, "중복되는 이메일이 존재합니다.")
}