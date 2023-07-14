package intelligent_booking.intelligent_booking.review.controller.response

import intelligent_booking.intelligent_booking.review.dto.response.ReviewInfo
import org.springframework.http.ResponseEntity

object ReviewResponse {
    private const val CREATE_REVIEW_SUCCESS = "리뷰를 성공적으로 등록하였습니다."

    fun detailSuccess(reviewInfo: ReviewInfo) = ResponseEntity.ok(reviewInfo)
    fun memberReviewsSuccess(reviews: List<ReviewInfo>) = ResponseEntity.ok(reviews)
    fun placeReviewsSuccess(reviews: List<ReviewInfo>) = ResponseEntity.ok(reviews)
    fun createReviewSuccess() = ResponseEntity.ok(CREATE_REVIEW_SUCCESS)
}