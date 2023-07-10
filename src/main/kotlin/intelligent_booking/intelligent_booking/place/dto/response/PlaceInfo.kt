package intelligent_booking.intelligent_booking.place.dto.response

import intelligent_booking.intelligent_booking.place.domain.Address
import java.util.UUID

data class PlaceInfo(
    val uuid: UUID,
    val name: String,
    val tel: String,
    val address: Address
)
