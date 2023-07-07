package intelligent_booking.intelligent_booking.place.dto.update

import jakarta.validation.constraints.NotBlank

data class UpdateAddress(
    @field:NotBlank(message = "도시를 입력하세요.")
    var city: String?,
    @field:NotBlank(message = "도로명 주소를 입력하세요.")
    var roadNum: String?,
    @field:NotBlank(message = "상세 주소를 입력하세요.")
    var detail: String?
)
