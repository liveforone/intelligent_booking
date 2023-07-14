package intelligent_booking.intelligent_booking.review.repository

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import intelligent_booking.intelligent_booking.exception.exception.ReviewException
import intelligent_booking.intelligent_booking.exception.message.ReviewExceptionMessage
import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.place.domain.Place
import intelligent_booking.intelligent_booking.review.domain.Review
import intelligent_booking.intelligent_booking.review.dto.response.ReviewInfo
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ReviewRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : ReviewCustomRepository {

    override fun findOneDtoByUUID(uuid: UUID): ReviewInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Review::uuid),
                    col(Place::uuid),
                    col(Review::content),
                    col(Review::createdDate)
                ))
                from(Review::class)
                join(Review::place)
                where(col(Review::uuid).equal(uuid))
            }
        } catch (e: NoResultException) {
            throw ReviewException(ReviewExceptionMessage.REVIEW_IS_NULL)
        }
    }

    override fun findReviewsByMember(memberUUID: UUID, lastUUID: UUID?): List<ReviewInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Review::uuid),
                col(Place::uuid),
                col(Review::content),
                col(Review::createdDate)
            ))
            from(Review::class)
            join(Review::place)
            join(Review::member)
            where(col(Member::uuid).equal(memberUUID))
            where(ltLastUUID(lastUUID))
        }
    }

    override fun findReviewsByPlace(placeUUID: UUID, lastUUID: UUID?): List<ReviewInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Review::uuid),
                col(Place::uuid),
                col(Review::content),
                col(Review::createdDate)
            ))
            from(Review::class)
            join(Review::place)
            where(col(Place::uuid).equal(placeUUID))
            where(ltLastUUID(lastUUID))
        }
    }

    private fun findLastId(lastUUID: UUID): Long {
        return queryFactory.singleQuery {
            select(listOf(col(Review::id)))
            from(Review::class)
            where(col(Review::uuid).equal(lastUUID))
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastUUID(lastUUID: UUID?): PredicateSpec? {
        val lastId = lastUUID?.let { findLastId(it) }
        return lastUUID?.let { and(col(Review::id).lessThan(lastId!!)) }
    }
}