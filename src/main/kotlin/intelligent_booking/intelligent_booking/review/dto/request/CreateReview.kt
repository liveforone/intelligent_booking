package intelligent_booking.intelligent_booking.review.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateReview(
    @field:NotNull(message = "예약의 외부 식별자를 입력하세요.")
    var reservationUUID: UUID?,
    @field:NotNull(message = "장소의 외부 식별자를 입력하세요.")
    var placeUUID: UUID?,
    @field:NotNull(message = "회원의 외부 식별자를 입력하세요.")
    var memberUUID: UUID?,
    @field:NotBlank(message = "리뷰를 입력하세요.")
    var content: String?
)
