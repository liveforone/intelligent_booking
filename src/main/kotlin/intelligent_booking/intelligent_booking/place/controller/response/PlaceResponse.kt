package intelligent_booking.intelligent_booking.place.controller.response

import intelligent_booking.intelligent_booking.place.dto.response.PlaceInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object PlaceResponse {

    private const val CREATE_PLACE_SUCCESS = "장소를 성공적으로 등록했습니다."
    private const val UPDATE_TEL_SUCCESS = "전화번호를 성공적으로 변경하였습니다."
    private const val UPDATE_ADDRESS_SUCCESS = "주소를 성공적으로 변경하였습니다."

    fun placeDetailSuccess(placeInfo: PlaceInfo) = ResponseEntity.ok(placeInfo)
    fun myPlaceSuccess(placeInfo: PlaceInfo) = ResponseEntity.ok(placeInfo)
    fun placeHomeSuccess(places: List<PlaceInfo>) = ResponseEntity.ok(places)
    fun searchNameSuccess(places: List<PlaceInfo>) = ResponseEntity.ok(places)
    fun searchAddressSuccess(places: List<PlaceInfo>) = ResponseEntity.ok(places)
    fun createPlaceSuccess(): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(CREATE_PLACE_SUCCESS)
    }
    fun updateTelSuccess() = ResponseEntity.ok(UPDATE_TEL_SUCCESS)
    fun updateAddressSuccess() = ResponseEntity.ok(UPDATE_ADDRESS_SUCCESS)
}