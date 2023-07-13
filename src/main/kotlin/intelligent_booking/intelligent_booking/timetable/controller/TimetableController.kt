package intelligent_booking.intelligent_booking.timetable.controller

import intelligent_booking.intelligent_booking.logger
import intelligent_booking.intelligent_booking.timetable.controller.constant.TimetableControllerLog
import intelligent_booking.intelligent_booking.timetable.controller.constant.TimetableParam
import intelligent_booking.intelligent_booking.timetable.controller.constant.TimetableUrl
import intelligent_booking.intelligent_booking.timetable.controller.response.TimetableResponse
import intelligent_booking.intelligent_booking.timetable.dto.request.CreateTimetable
import intelligent_booking.intelligent_booking.timetable.dto.update.UpdateDescription
import intelligent_booking.intelligent_booking.timetable.service.command.TimetableCommandService
import intelligent_booking.intelligent_booking.timetable.service.query.TimetableQueryService
import intelligent_booking.intelligent_booking.validator.validateBinding
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class TimetableController @Autowired constructor(
    private val timetableQueryService: TimetableQueryService,
    private val timetableCommandService: TimetableCommandService
) {
    @GetMapping(TimetableUrl.DETAIL)
    fun detail(@PathVariable(TimetableParam.UUID) uuid: UUID): ResponseEntity<*> {
        val timetable = timetableQueryService.getTimetableByUUID(uuid)
        return TimetableResponse.detailSuccess(timetable)
    }

    @GetMapping(TimetableUrl.TIMETABLES_BELONG_PLACE)
    fun timetableByPlace(
        @PathVariable(TimetableParam.PLACE_UUID) placeUUID: UUID,
        @RequestParam(TimetableParam.LAST_UUID, required = false) lastUUID: UUID?
    ): ResponseEntity<*> {
        val timetables = timetableQueryService.getTimetablesByPlace(placeUUID, lastUUID)
        return TimetableResponse.placeTimetablesSuccess(timetables)
    }

    @PostMapping(TimetableUrl.CREATE)
    fun createTimetable(
        @RequestBody @Valid createTimetable: CreateTimetable,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        timetableCommandService.createTimetable(createTimetable)
        logger().info(TimetableControllerLog.CREATE_TIMETABLE_SUCCESS.log)

        return TimetableResponse.createTimetableSuccess()
    }

    @PutMapping(TimetableUrl.UPDATE_DESCRIPTION)
    fun updateDescription(
        @PathVariable(TimetableParam.UUID) uuid: UUID,
        @RequestBody @Valid updateDescription: UpdateDescription,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        timetableCommandService.updateDescription(updateDescription, uuid)
        logger().info(TimetableControllerLog.UPDATE_DESCRIPTION_SUCCESS.log)

        return TimetableResponse.updateDescriptionSuccess()
    }

    @DeleteMapping(TimetableUrl.DELETE)
    fun deleteTimetable(@PathVariable(TimetableParam.UUID) uuid: UUID): ResponseEntity<*> {
        timetableCommandService.deleteTimetable(uuid)
        logger().info(TimetableControllerLog.DELETE_TIMETABLE_SUCCESS.log)

        return TimetableResponse.deleteTimetableSuccess()
    }
}