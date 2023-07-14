package intelligent_booking.intelligent_booking.review.service.command

import intelligent_booking.intelligent_booking.exception.exception.ReviewException
import intelligent_booking.intelligent_booking.exception.message.ReviewExceptionMessage
import intelligent_booking.intelligent_booking.member.repository.MemberRepository
import intelligent_booking.intelligent_booking.place.repository.PlaceRepository
import intelligent_booking.intelligent_booking.reservation.repository.ReservationRepository
import intelligent_booking.intelligent_booking.review.domain.Review
import intelligent_booking.intelligent_booking.review.dto.request.CreateReview
import intelligent_booking.intelligent_booking.review.repository.ReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class ReviewCommandService @Autowired constructor(
    private val reviewRepository: ReviewRepository,
    private val reservationRepository: ReservationRepository,
    private val placeRepository: PlaceRepository,
    private val memberRepository: MemberRepository
) {
    fun createReview(createReview: CreateReview): UUID {
        return with(createReview) {
            isOwnerOfReservation(memberUUID!!, reservationUUID!!)
            Review.create(
                place = placeRepository.findOneByUUID(placeUUID!!),
                member = memberRepository.findOneByUUID(memberUUID!!),
                content!!
            ).run { reviewRepository.save(this).uuid }
        }
    }

    private fun isOwnerOfReservation(memberUUID: UUID, reservationUUID: UUID) {
        val foundMemberUUID = reservationRepository.findMemberUUIDByUUID(reservationUUID)
        requireNotNull(foundMemberUUID) { throw ReviewException(ReviewExceptionMessage.RESERVATION_IS_NULL) }
        require(foundMemberUUID == memberUUID) { throw ReviewException(ReviewExceptionMessage.NOT_MATCH_MEMBER) }
    }
}