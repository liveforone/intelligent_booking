package intelligent_booking.intelligent_booking.place.repository

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import intelligent_booking.intelligent_booking.exception.exception.PlaceException
import intelligent_booking.intelligent_booking.exception.message.PlaceExceptionMessage
import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.place.domain.Address
import intelligent_booking.intelligent_booking.place.domain.Place
import intelligent_booking.intelligent_booking.place.dto.response.PlaceInfo
import intelligent_booking.intelligent_booking.place.repository.constant.PlaceRepositoryConstant
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PlaceRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : PlaceCustomRepository {

    override fun findOneByUUID(uuid: UUID): Place {
        return try {
            queryFactory.singleQuery {
                select(entity(Place::class))
                from(Place::class)
                where(col(Place::uuid).equal(uuid))
            }
        } catch (e: NoResultException) {
            throw PlaceException(PlaceExceptionMessage.PLACE_IS_NULL)
        }
    }

    override fun findOneByMember(memberUUID: UUID): Place {
        return try {
            queryFactory.singleQuery {
                select(entity(Place::class))
                from(Place::class)
                join(Place::member)
                where(col(Member::uuid).equal(memberUUID))
            }
        } catch (e: NoResultException) {
            throw PlaceException(PlaceExceptionMessage.PLACE_IS_NULL)
        }
    }

    override fun findOneDtoByUUID(uuid: UUID): PlaceInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Place::uuid),
                    col(Place::name),
                    col(Place::tel),
                    col(Place::address)
                ))
                from(Place::class)
                where(col(Place::uuid).equal(uuid))
            }
        } catch (e: NoResultException) {
            throw PlaceException(PlaceExceptionMessage.PLACE_IS_NULL)
        }
    }

    override fun findOneDtoByMember(memberUUID: UUID): PlaceInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Place::uuid),
                    col(Place::name),
                    col(Place::tel),
                    col(Place::address)
                ))
                from(Place::class)
                join(Place::member)
                where(col(Member::uuid).equal(memberUUID))
            }
        } catch (e: NoResultException) {
            throw PlaceException(PlaceExceptionMessage.PLACE_IS_NULL)
        }
    }

    override fun findAllDto(lastUUID: UUID?): List<PlaceInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Place::uuid),
                col(Place::name),
                col(Place::tel),
                col(Place::address)
            ))
            from(Place::class)
            where(ltLastUUID(lastUUID))
            orderBy(col(Place::id).desc())
            limit(PlaceRepositoryConstant.LIMIT_PAGE)
        }
    }

    override fun searchByName(name: String, lastUUID: UUID?): List<PlaceInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Place::uuid),
                col(Place::name),
                col(Place::tel),
                col(Place::address)
            ))
            from(Place::class)
            where(col(Place::name).like("$name%"))
            where(ltLastUUID(lastUUID))
            orderBy(col(Place::id).desc())
            limit(PlaceRepositoryConstant.LIMIT_PAGE)
        }
    }

    override fun searchByAddress(city: String?, roadNum: String?, detail: String?, lastUUID: UUID?): List<PlaceInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Place::uuid),
                col(Place::name),
                col(Place::tel),
                col(Place::address)
            ))
            from(Place::class)
            associate(Place::class, Address::class, on(Place::address))
            where(searchAddress(city, roadNum, detail))
            where(ltLastUUID(lastUUID))
            orderBy(col(Place::id).desc())
            limit(PlaceRepositoryConstant.LIMIT_PAGE)
        }
    }

    private fun findLastId(lastUUID: UUID): Long {
        return queryFactory.singleQuery {
            select(listOf(col(Place::id)))
            from(Place::class)
            where(col(Place::uuid).equal(lastUUID))
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastUUID(lastUUID: UUID?): PredicateSpec? {
        val lastId = lastUUID?.let { findLastId(it) }
        return lastUUID?.let { and(col(Place::id).lessThan(lastId!!)) }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.searchAddress(
        city: String?,
        roadNum: String?,
        detail: String?
    ): PredicateSpec {
        val searchCity = city?.let { col(Address::city).like("$city%") }
        val searchRoadNum = roadNum?.let { col(Address::roadNum).like("$roadNum%") }
        val searchDetail = detail?.let { col(Address::detail).like("$detail%") }
        return and(searchCity, searchRoadNum, searchDetail)
    }
}