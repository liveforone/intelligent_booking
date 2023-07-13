package intelligent_booking.intelligent_booking.timetable.controller.response

import intelligent_booking.intelligent_booking.timetable.dto.response.TimetableInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object TimetableResponse {
    private const val CREATE_TIMETABLE_SUCCESS = "타임테이블을 성공적으로 생성하였습니다."
    private const val UPDATE_DESCRIPTION_SUCCESS = "설명을 성공적으로 변경하였습니다."
    private const val DELETE_TIMETABLE_SUCCESS = "타임테이블을 성공적으로 삭제하였습니다."

    fun detailSuccess(timetableInfo: TimetableInfo) = ResponseEntity.ok(timetableInfo)
    fun placeTimetablesSuccess(timetables: List<TimetableInfo>) = ResponseEntity.ok(timetables)
    fun createTimetableSuccess() = ResponseEntity.status(HttpStatus.CREATED).body(CREATE_TIMETABLE_SUCCESS)
    fun updateDescriptionSuccess() = ResponseEntity.ok(UPDATE_DESCRIPTION_SUCCESS)
    fun deleteTimetableSuccess() = ResponseEntity.ok(DELETE_TIMETABLE_SUCCESS)
}