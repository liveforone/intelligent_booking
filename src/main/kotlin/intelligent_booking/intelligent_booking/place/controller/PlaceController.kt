package intelligent_booking.intelligent_booking.place.controller

import intelligent_booking.intelligent_booking.logger
import intelligent_booking.intelligent_booking.place.controller.constant.PlaceControllerLog
import intelligent_booking.intelligent_booking.place.controller.constant.PlaceParam
import intelligent_booking.intelligent_booking.place.controller.constant.PlaceUrl
import intelligent_booking.intelligent_booking.place.controller.response.PlaceResponse
import intelligent_booking.intelligent_booking.place.dto.request.CreatePlace
import intelligent_booking.intelligent_booking.place.dto.update.UpdateAddress
import intelligent_booking.intelligent_booking.place.dto.update.UpdateTel
import intelligent_booking.intelligent_booking.place.service.command.PlaceCommandService
import intelligent_booking.intelligent_booking.place.service.query.PlaceQueryService
import intelligent_booking.intelligent_booking.validator.validateBinding
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class PlaceController @Autowired constructor(
    private val placeQueryService: PlaceQueryService,
    private val placeCommandService: PlaceCommandService
) {

    @GetMapping(PlaceUrl.DETAIL)
    fun detail(@PathVariable(PlaceParam.UUID) uuid: UUID): ResponseEntity<*> {
        val place = placeQueryService.getPlaceByUUID(uuid)
        return PlaceResponse.placeDetailSuccess(place)
    }

    @GetMapping(PlaceUrl.MY_PLACE)
    fun myPlace(@PathVariable(PlaceParam.MEMBER_UUID) memberUUID: UUID): ResponseEntity<*> {
        val place = placeQueryService.getPlaceByMember(memberUUID)
        return PlaceResponse.myPlaceSuccess(place)
    }

    @GetMapping(PlaceUrl.PLACE_HOME)
    fun placeHome(@RequestParam(PlaceParam.LAST_UUID, required = false) lastUUID: UUID?): ResponseEntity<*> {
        val places = placeQueryService.getAllPlace(lastUUID)
        return PlaceResponse.placeHomeSuccess(places)
    }

    @GetMapping(PlaceUrl.SEARCH_NAME)
    fun searchName(
        @RequestParam(PlaceParam.LAST_UUID, required = false) lastUUID: UUID?,
        @RequestParam(PlaceParam.NAME) name: String
    ): ResponseEntity<*> {
        val places = placeQueryService.searchPlaceByName(name, lastUUID)
        return PlaceResponse.searchNameSuccess(places)
    }

    @GetMapping(PlaceUrl.SEARCH_ADDRESS)
    fun searchAddress(
        @RequestParam(PlaceParam.LAST_UUID, required = false) lastUUID: UUID?,
        @RequestParam(PlaceParam.CITY, required = false) city: String?,
        @RequestParam(PlaceParam.ROAD_NUM, required = false) roadNum: String?,
        @RequestParam(PlaceParam.DETAIL, required = false) detail: String?
    ): ResponseEntity<*> {
        val places = placeQueryService.searchPlaceByAddress(city, roadNum, detail, lastUUID)
        return PlaceResponse.searchAddressSuccess(places)
    }

    @PostMapping(PlaceUrl.CREATE_PLACE)
    fun createPlace(
        @PathVariable(PlaceParam.MEMBER_UUID) memberUUID: UUID,
        @RequestBody @Valid createPlace: CreatePlace,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        placeCommandService.createPlace(createPlace, memberUUID)
        logger().info(PlaceControllerLog.CREATE_PLACE_SUCCESS.log)

        return PlaceResponse.createPlaceSuccess()
    }

    @PutMapping(PlaceUrl.UPDATE_TEL)
    fun updateTel(
        @PathVariable(PlaceParam.UUID) uuid: UUID,
        @RequestBody @Valid updateTel: UpdateTel,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        placeCommandService.updateTel(updateTel, uuid)
        logger().info(PlaceControllerLog.UPDATE_TEL_SUCCESS.log)

        return PlaceResponse.updateTelSuccess()
    }

    @PutMapping(PlaceUrl.UPDATE_ADDRESS)
    fun updateAddress(
        @PathVariable(PlaceParam.UUID) uuid: UUID,
        @RequestBody @Valid updateAddress: UpdateAddress,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        placeCommandService.updateAddress(updateAddress, uuid)
        logger().info(PlaceControllerLog.UPDATE_ADDRESS_SUCCESS.log)

        return PlaceResponse.updateAddressSuccess()
    }
}