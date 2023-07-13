package intelligent_booking.intelligent_booking.timetable.repository

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import intelligent_booking.intelligent_booking.exception.exception.TimetableException
import intelligent_booking.intelligent_booking.exception.message.TimetableExceptionMessage
import intelligent_booking.intelligent_booking.place.domain.Place
import intelligent_booking.intelligent_booking.timetable.domain.Timetable
import intelligent_booking.intelligent_booking.timetable.dto.response.TimetableInfo
import intelligent_booking.intelligent_booking.timetable.repository.constant.TimetableRepositoryConstant
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TimetableRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : TimetableCustomRepository {

    override fun findOneByUUID(uuid: UUID): Timetable {
        return try {
            queryFactory.singleQuery {
                select(entity(Timetable::class))
                from(Timetable::class)
                where(col(Timetable::uuid).equal(uuid))
            }
        } catch (e: NoResultException) {
            throw TimetableException(TimetableExceptionMessage.TIMETABLE_IS_NULL)
        }
    }

    override fun findOneDtoByUUID(uuid: UUID): TimetableInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Timetable::uuid),
                    col(Timetable::remainingCount),
                    col(Timetable::reservationHour),
                    col(Timetable::reservationMinute),
                    col(Timetable::description)
                ))
                from(Timetable::class)
                where(col(Timetable::uuid).equal(uuid))
            }
        } catch (e: NoResultException) {
            throw TimetableException(TimetableExceptionMessage.TIMETABLE_IS_NULL)
        }
    }

    override fun findTimetablesByPlace(placeUUID: UUID, lastUUID: UUID?): List<TimetableInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Timetable::uuid),
                col(Timetable::remainingCount),
                col(Timetable::reservationHour),
                col(Timetable::reservationMinute),
                col(Timetable::description)
            ))
            from(Timetable::class)
            join(Timetable::place)
            where(col(Place::uuid).equal(placeUUID))
            where(ltLastUUID(lastUUID))
            orderBy(col(Timetable::id).desc())
            limit(TimetableRepositoryConstant.LIMIT_PAGE)
        }
    }

    private fun findLastId(lastUUID: UUID): Long {
        return queryFactory.singleQuery {
            select(listOf(col(Timetable::id)))
            from(Timetable::class)
            where(col(Timetable::uuid).equal(lastUUID))
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastUUID(lastUUID: UUID?): PredicateSpec? {
        val lastId = lastUUID?.let { findLastId(it) }
        return lastUUID?.let { and(col(Timetable::id).lessThan(lastId!!)) }
    }
}