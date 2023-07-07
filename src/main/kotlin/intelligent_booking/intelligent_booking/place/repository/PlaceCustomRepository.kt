package intelligent_booking.intelligent_booking.place.repository

import intelligent_booking.intelligent_booking.place.domain.Place
import intelligent_booking.intelligent_booking.place.dto.response.PlaceInfo

interface PlaceCustomRepository {
    fun findOneDtoById(id: Long): PlaceInfo
    fun findOneByIdentity(identity: String): Place
    fun findOneDtoByIdentity(identity: String): PlaceInfo
    fun findAllDto(lastId: Long): List<PlaceInfo>
    fun searchByName(name: String, lastId: Long): List<PlaceInfo>
    fun searchByAddress(city: String?, roadNum: String?, detail: String?, lastId: Long): List<PlaceInfo>
}