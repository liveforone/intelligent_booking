package intelligent_booking.intelligent_booking.review.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class ReviewInfo(
    val uuid: UUID,
    val placeUUID: UUID,
    val content: String,
    val createdDate: LocalDateTime
)