package intelligent_booking.intelligent_booking.reservation.repository

import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.reservation.domain.Reservation
import intelligent_booking.intelligent_booking.reservation.repository.constant.ReservationQueryParam
import intelligent_booking.intelligent_booking.reservation.repository.constant.ReservationStaticQuery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ReservationRepository : JpaRepository<Reservation, Long>, ReservationCustomRepository {
    @Modifying(clearAutomatically = true)
    @Query(ReservationStaticQuery.DELETE_BY_MEMBER)
    fun deleteBulkByMember(@Param(ReservationQueryParam.MEMBER) member: Member)
}