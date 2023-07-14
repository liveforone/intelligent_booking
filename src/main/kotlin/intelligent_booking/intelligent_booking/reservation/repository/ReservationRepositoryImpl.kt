package intelligent_booking.intelligent_booking.reservation.repository

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import intelligent_booking.intelligent_booking.exception.exception.ReservationException
import intelligent_booking.intelligent_booking.exception.message.ReservationExceptionMessage
import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.reservation.domain.Reservation
import intelligent_booking.intelligent_booking.reservation.dto.response.ReservationInfo
import intelligent_booking.intelligent_booking.reservation.repository.constant.ReservationRepositoryConstant
import intelligent_booking.intelligent_booking.timetable.domain.Timetable
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ReservationRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : ReservationCustomRepository {

    override fun findOneByUUID(uuid: UUID): Reservation {
        return try {
            queryFactory.singleQuery {
                select(entity(Reservation::class))
                from(Reservation::class)
                fetch(Reservation::timetable)
                join(Reservation::timetable)
                where(col(Reservation::uuid).equal(uuid))
            }
        } catch (e: NoResultException) {
            throw ReservationException(ReservationExceptionMessage.RESERVATION_IS_NULL)
        }
    }

    override fun findOneDtoByUUID(uuid: UUID): ReservationInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Reservation::uuid),
                    col(Timetable::uuid),
                    col(Member::uuid),
                    col(Reservation::reservationState),
                    col(Reservation::createdDateTime),
                ))
                from(Reservation::class)
                join(Reservation::timetable)
                join(Reservation::member)
                where(col(Reservation::uuid).equal(uuid))
            }
        } catch (e: NoResultException) {
            throw ReservationException(ReservationExceptionMessage.RESERVATION_IS_NULL)
        }
    }

    override fun findReservationsByMember(memberUUID: UUID, lastUUID: UUID?): List<ReservationInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Reservation::uuid),
                col(Timetable::uuid),
                col(Member::uuid),
                col(Reservation::reservationState),
                col(Reservation::createdDateTime),
            ))
            from(Reservation::class)
            join(Reservation::timetable)
            join(Reservation::member)
            where(col(Member::uuid).equal(memberUUID))
            where(ltLastUUID(lastUUID))
            orderBy(col(Reservation::id).desc())
            limit(ReservationRepositoryConstant.LIMIT_PAGE)
        }
    }

    override fun findReservationsByTimetable(timetableUUID: UUID, lastUUID: UUID?): List<ReservationInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Reservation::uuid),
                col(Timetable::uuid),
                col(Member::uuid),
                col(Reservation::reservationState),
                col(Reservation::createdDateTime),
            ))
            from(Reservation::class)
            join(Reservation::timetable)
            join(Reservation::member)
            where(col(Timetable::uuid).equal(timetableUUID))
            where(ltLastUUID(lastUUID))
            orderBy(col(Reservation::id).desc())
            limit(ReservationRepositoryConstant.LIMIT_PAGE)
        }
    }

    private fun findLastId(lastUUID: UUID): Long {
        return queryFactory.singleQuery {
            select(listOf(col(Reservation::id)))
            from(Reservation::class)
            where(col(Reservation::uuid).equal(lastUUID))
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastUUID(lastUUID: UUID?): PredicateSpec? {
        val lastId = lastUUID?.let { findLastId(it) }
        return lastUUID?.let { and(col(Reservation::id).lessThan(lastId!!)) }
    }
}