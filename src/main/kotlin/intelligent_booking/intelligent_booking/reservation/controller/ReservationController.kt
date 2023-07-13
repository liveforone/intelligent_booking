package intelligent_booking.intelligent_booking.reservation.controller

import intelligent_booking.intelligent_booking.logger
import intelligent_booking.intelligent_booking.reservation.controller.constant.ReservationControllerLog
import intelligent_booking.intelligent_booking.reservation.controller.constant.ReservationParam
import intelligent_booking.intelligent_booking.reservation.controller.constant.ReservationUrl
import intelligent_booking.intelligent_booking.reservation.controller.response.ReservationResponse
import intelligent_booking.intelligent_booking.reservation.dto.request.CreateReservation
import intelligent_booking.intelligent_booking.reservation.service.command.ReservationCommandService
import intelligent_booking.intelligent_booking.reservation.service.query.ReservationQueryService
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
class ReservationController @Autowired constructor(
    private val reservationQueryService: ReservationQueryService,
    private val reservationCommandService: ReservationCommandService
) {

    @GetMapping(ReservationUrl.DETAIL)
    fun detail(@PathVariable(ReservationParam.UUID) uuid: UUID): ResponseEntity<*> {
        val reservation = reservationQueryService.getReservationByUUID(uuid)
        return ReservationResponse.reservationDetailSuccess(reservation)
    }

    @GetMapping(ReservationUrl.RESERVATION_BELONG_MEMBER)
    fun reservationBelongMember(
        @PathVariable(ReservationParam.MEMBER_UUID) memberUUID: UUID,
        @RequestParam(ReservationParam.LAST_UUID, required = false) lastUUID: UUID?
    ): ResponseEntity<*> {
        val reservations = reservationQueryService.getReservationsByMember(memberUUID, lastUUID)
        return ReservationResponse.memberReservationSuccess(reservations)
    }

    @PostMapping(ReservationUrl.RESERVATION)
    fun reservation(
        @RequestBody @Valid createReservation: CreateReservation,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        validateBinding(bindingResult)

        reservationCommandService.createReservation(createReservation)
        logger().info(ReservationControllerLog.RESERVATION_SUCCESS.log)

        return ReservationResponse.reservationSuccess()
    }

    @PutMapping(ReservationUrl.CANCEL)
    fun cancelReservation(@PathVariable(ReservationParam.UUID) uuid: UUID): ResponseEntity<*> {
        reservationCommandService.cancelReservation(uuid)
        logger().info(ReservationControllerLog.CANCEL_SUCCESS.log)

        return ReservationResponse.cancelReservationSuccess()
    }
}