package intelligent_booking.intelligent_booking.member.domain

enum class Role(val auth:String) {
    MEMBER("ROLE_MEMBER"),
    PRESIDENT("ROLE_PRESIDENT"),
    ADMIN("ROLE_ADMIN"),
    SUSPEND("ROLE_SUSPEND")
}