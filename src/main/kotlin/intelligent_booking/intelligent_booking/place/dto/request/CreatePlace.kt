package intelligent_booking.intelligent_booking.place.dto.request

import jakarta.validation.constraints.NotBlank

data class CreatePlace(
    @field:NotBlank(message = "상호를 입력하세요.")
    val name: String?,
    @field:NotBlank(message = "전화번호를 입력하세요.")
    val tel: String?,
    @field:NotBlank(message = "도시를 입력하세요.")
    val city: String?,
    @field:NotBlank(message = "도로명 주소를 입력하세요.")
    val roadNum: String?,
    @field:NotBlank(message = "상세 주소를 입력하세요.")
    val detail: String?
)
