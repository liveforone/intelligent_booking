package intelligent_booking.intelligent_booking.review.repository

import intelligent_booking.intelligent_booking.review.dto.response.ReviewInfo
import java.util.UUID

interface ReviewCustomRepository {
    fun findOneDtoByUUID(uuid: UUID): ReviewInfo
    fun findReviewsByMember(memberUUID: UUID, lastUUID: UUID?): List<ReviewInfo>
    fun findReviewsByPlace(placeUUID: UUID, lastUUID: UUID?): List<ReviewInfo>
}