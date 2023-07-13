package intelligent_booking.intelligent_booking.reservation.service.query

import intelligent_booking.intelligent_booking.reservation.repository.ReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class ReservationQueryService @Autowired constructor(
    private val reservationRepository: ReservationRepository
) {
    fun getReservationByUUID(uuid: UUID) = reservationRepository.findOneDtoByUUID(uuid)
    fun getReservationsByMember(memberUUID: UUID, lastUUID: UUID?) = reservationRepository.findReservationsByMember(memberUUID, lastUUID)
}