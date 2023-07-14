package intelligent_booking.intelligent_booking.report.eventHandler

import intelligent_booking.intelligent_booking.exception.exception.ReportException
import intelligent_booking.intelligent_booking.exception.message.ReportExceptionMessage
import intelligent_booking.intelligent_booking.member.service.command.MemberCommandService
import intelligent_booking.intelligent_booking.report.eventHandler.constant.ReportConstant
import intelligent_booking.intelligent_booking.report.dto.ReportRequest
import intelligent_booking.intelligent_booking.reservation.dto.response.ReservationInfo
import intelligent_booking.intelligent_booking.reservation.service.query.ReservationQueryService
import intelligent_booking.intelligent_booking.timetable.service.query.TimetableQueryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

@Component
class ReportEventHandler @Autowired constructor(
    private val reservationQueryService: ReservationQueryService,
    private val timetableQueryService: TimetableQueryService,
    private val memberCommandService: MemberCommandService
) {
    fun reportMember(reportRequest: ReportRequest) {
        with(reportRequest) {
            isReportPossibleByReservation(reservationQueryService.getReservationByUUID(reservationUUID!!))
            isReportPossibleByTimetable(timetableUUID!!)
            memberCommandService.addReport(memberUUID!!)
        }
    }

    private fun isReportPossibleByReservation(reservationInfo: ReservationInfo) {
        check(reservationInfo.isReservation()) { throw ReportException(ReportExceptionMessage.RESERVATION_IS_CANCELED) }
    }

    private fun isReportPossibleByTimetable(timetableUUID: UUID) {
        val timetable = timetableQueryService.getTimetableByUUID(timetableUUID)
        val now = LocalDateTime.now()
        val reservationHour = timetable.reservationHour
        val reservationMinute = timetable.reservationMinute

        if (now.hour == reservationHour.toInt()) {
            check(now.minute > reservationMinute) { throw ReportException(ReportExceptionMessage.RESERVATION_IS_NOT_START) }
        } else {
            check(now.hour > reservationHour) { throw ReportException(ReportExceptionMessage.RESERVATION_IS_NOT_START) }
        }
        check(now.hour < reservationHour + ReportConstant.ONE_DAY_OF_HOUR) { ReportException(ReportExceptionMessage.OVER_REPORT_DAY) }
    }
}