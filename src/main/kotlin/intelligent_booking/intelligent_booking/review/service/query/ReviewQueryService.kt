package intelligent_booking.intelligent_booking.review.service.query

import intelligent_booking.intelligent_booking.review.repository.ReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class ReviewQueryService @Autowired constructor(
    private val reviewRepository: ReviewRepository
) {
    fun getReviewByUUID(uuid: UUID) = reviewRepository.findOneDtoByUUID(uuid)
    fun getReviewsByMember(memberUUID: UUID, lastUUID: UUID?) = reviewRepository.findReviewsByMember(memberUUID, lastUUID)
    fun getReviewsByPlace(placeUUID: UUID, lastUUID: UUID?) = reviewRepository.findReviewsByPlace(placeUUID, lastUUID)
}