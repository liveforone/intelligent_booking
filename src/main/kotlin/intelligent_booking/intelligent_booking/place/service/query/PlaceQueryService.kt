package intelligent_booking.intelligent_booking.place.service.query

import intelligent_booking.intelligent_booking.place.dto.response.PlaceInfo
import intelligent_booking.intelligent_booking.place.repository.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PlaceQueryService @Autowired constructor(
    private val placeRepository: PlaceRepository
) {
    fun getOneById(id: Long) = placeRepository.findOneDtoById(id)

    fun getOneByIdentity(identity: String) = placeRepository.findOneDtoByIdentity(identity)

    fun getAll(lastId: Long) = placeRepository.findAllDto(lastId)

    fun searchByName(name: String, lastId: Long) = placeRepository.searchByName(name, lastId)

    fun searchByAddress(city: String?, roadNum: String?, detail: String?, lastId: Long): List<PlaceInfo> {
        return placeRepository.searchByAddress(city, roadNum, detail, lastId)
    }
}