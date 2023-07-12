package intelligent_booking.intelligent_booking.place.service.query

import intelligent_booking.intelligent_booking.place.dto.response.PlaceInfo
import intelligent_booking.intelligent_booking.place.repository.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class PlaceQueryService @Autowired constructor(
    private val placeRepository: PlaceRepository
) {
    fun getPlaceByUUID(uuid: UUID) = placeRepository.findOneDtoByUUID(uuid)

    fun getPlaceByMember(memberUUID: UUID) = placeRepository.findOneDtoByMember(memberUUID)

    fun getAllPlace(lastUUID: UUID?) = placeRepository.findAllDto(lastUUID)

    fun searchPlaceByName(name: String, lastUUID: UUID?) = placeRepository.searchByName(name, lastUUID)

    fun searchPlaceByAddress(city: String?, roadNum: String?, detail: String?, lastUUID: UUID?): List<PlaceInfo> {
        return placeRepository.searchByAddress(city, roadNum, detail, lastUUID)
    }
}