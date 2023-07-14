package intelligent_booking.intelligent_booking.review.controller

import intelligent_booking.intelligent_booking.logger
import intelligent_booking.intelligent_booking.reservation.controller.constant.ReservationParam
import intelligent_booking.intelligent_booking.review.controller.constant.ReviewControllerLog
import intelligent_booking.intelligent_booking.review.controller.constant.ReviewParam
import intelligent_booking.intelligent_booking.review.controller.constant.ReviewUrl
import intelligent_booking.intelligent_booking.review.controller.response.ReviewResponse
import intelligent_booking.intelligent_booking.review.dto.request.CreateReview
import intelligent_booking.intelligent_booking.review.service.command.ReviewCommandService
import intelligent_booking.intelligent_booking.review.service.query.ReviewQueryService
import intelligent_booking.intelligent_booking.validator.validateBinding
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ReviewController @Autowired constructor(
    private val reviewQueryService: ReviewQueryService,
    private val reviewCommandService: ReviewCommandService
) {
    @GetMapping(ReviewUrl.DETAIL)
    fun detail(@PathVariable(ReviewParam.UUID) uuid: UUID): ResponseEntity<*> {
        val review = reviewQueryService.getReviewByUUID(uuid)
        return ReviewResponse.detailSuccess(review)
    }

    @GetMapping(ReviewUrl.MEMBER_REVIEWS)
    fun memberReviews(
        @PathVariable(ReviewParam.MEMBER_UUID) memberUUID: UUID,
        @RequestParam(ReservationParam.LAST_UUID, required = false) lastUUID: UUID?
    ): ResponseEntity<*> {
        val reviews = reviewQueryService.getReviewsByMember(memberUUID, lastUUID)
        return ReviewResponse.memberReviewsSuccess(reviews)
    }

    @GetMapping(ReviewUrl.PLACE_REVIEWS)
    fun placeReviews(
        @PathVariable(ReviewParam.PLACE_UUID) placeUUID: UUID,
        @RequestParam(ReservationParam.LAST_UUID, required = false) lastUUID: UUID?
    ): ResponseEntity<*> {
        val reviews = reviewQueryService.getReviewsByPlace(placeUUID, lastUUID)
        return ReviewResponse.placeReviewsSuccess(reviews)
    }

    @PostMapping(ReviewUrl.CREATE_REVIEW)
    fun createReview(
        @RequestBody @Valid createReview: CreateReview,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        reviewCommandService.createReview(createReview)
        logger().info(ReviewControllerLog.CREATE_REVIEW_SUCCESS.log)

        return ReviewResponse.createReviewSuccess()
    }
}