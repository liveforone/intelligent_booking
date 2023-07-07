package intelligent_booking.intelligent_booking.place.dto.response

import intelligent_booking.intelligent_booking.place.domain.Address

data class PlaceInfo(
    val id: Long?,
    val name: String,
    val tel: String,
    val address: Address
)
