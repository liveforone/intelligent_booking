package intelligent_booking.intelligent_booking.place.repository

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
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

@Repository
class PlaceRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : PlaceCustomRepository {

    override fun findOneDtoById(id: Long): PlaceInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Place::id),
                    col(Place::name),
                    col(Place::tel),
                    col(Place::address)
                ))
                from(Place::class)
                where(col(Place::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw PlaceException(PlaceExceptionMessage.PLACE_IS_NULL)
        }
    }

    override fun findOneByIdentity(identity: String): Place {
        return try {
            queryFactory.singleQuery {
                select(entity(Place::class))
                from(Place::class)
                fetch(Place::member)
                join(Place::member)
                where(nestedCol(col(Place::member), Member::identity).equal(identity))
            }
        } catch (e: NoResultException) {
            throw PlaceException(PlaceExceptionMessage.PLACE_IS_NULL)
        }
    }

    override fun findOneDtoByIdentity(identity: String): PlaceInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Place::id),
                    col(Place::name),
                    col(Place::tel),
                    col(Place::address)
                ))
                from(Place::class)
                where(nestedCol(col(Place::member), Member::identity).equal(identity))
            }
        } catch (e: NoResultException) {
            throw PlaceException(PlaceExceptionMessage.PLACE_IS_NULL)
        }
    }

    override fun findAllDto(lastId: Long): List<PlaceInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Place::id),
                col(Place::name),
                col(Place::tel),
                col(Place::address)
            ))
            from(Place::class)
            where(ltLastId(lastId))
            orderBy(col(Place::id).desc())
            limit(PlaceRepositoryConstant.LIMIT_PAGE)
        }
    }

    override fun searchByName(name: String, lastId: Long): List<PlaceInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Place::id),
                col(Place::name),
                col(Place::tel),
                col(Place::address)
            ))
            from(Place::class)
            where(col(Place::name).like("$name%"))
            where(ltLastId(lastId))
            orderBy(col(Place::id).desc())
            limit(PlaceRepositoryConstant.LIMIT_PAGE)
        }
    }

    override fun searchByAddress(city: String?, roadNum: String?, detail: String?, lastId: Long): List<PlaceInfo> {
        return queryFactory.listQuery {
            select(listOf(
                    col(Place::id),
                    col(Place::name),
                    col(Place::tel),
                    col(Place::address)
            ))
            from(Place::class)
            associate(Place::class, Address::class, on(Place::address))
            where(searchAddress(city, roadNum, detail))
            where(ltLastId(lastId))
            orderBy(col(Place::id).desc())
            limit(PlaceRepositoryConstant.LIMIT_PAGE)
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastId(lastId: Long): PredicateSpec? {
        return if (lastId == 0.toLong()) null
        else and(col(Place::id).lessThan(lastId))
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