package intelligent_booking.intelligent_booking.place.repository

import intelligent_booking.intelligent_booking.place.domain.Place
import intelligent_booking.intelligent_booking.place.dto.response.PlaceInfo
import java.util.UUID

interface PlaceCustomRepository {
    fun findOneByUUID(uuid: UUID): Place
    fun findOneDtoByUUID(uuid: UUID): PlaceInfo
    fun findOneDtoByMember(memberUUID: UUID): PlaceInfo
    fun findAllDto(lastId: Long?): List<PlaceInfo>
    fun searchByName(name: String, lastId: Long?): List<PlaceInfo>
    fun searchByAddress(city: String?, roadNum: String?, detail: String?, lastId: Long?): List<PlaceInfo>
}