package intelligent_booking.intelligent_booking.member.repository

import intelligent_booking.intelligent_booking.exception.exception.MemberException
import intelligent_booking.intelligent_booking.exception.message.MemberExceptionMessage
import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.member.dto.response.MemberInfo
import intelligent_booking.intelligent_booking.member.repository.constant.MemberRepositoryConstant
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : MemberCustomRepository {

    override fun findIdByEmailNullableForValidate(email: String): Long? {
        val foundIds = queryFactory.listQuery<Long> {
            select(listOf(col(Member::id)))
            from(entity(Member::class))
            where(col(Member::email).equal(email))
        }
        return if (foundIds.isEmpty()) null else foundIds[MemberRepositoryConstant.FIRST_INDEX]
    }

    override fun findOneByEmail(email: String): Member {
        return try {
            queryFactory.singleQuery {
                select(entity(Member::class))
                from(entity(Member::class))
                where(col(Member::email).equal(email))
            }
        } catch (e: NoResultException) {
            throw MemberException(MemberExceptionMessage.MEMBER_IS_NULL)
        }
    }

    override fun findOneByIdentity(identity: String): Member {
        return try {
            queryFactory.singleQuery {
                select(entity(Member::class))
                from(entity(Member::class))
                where(col(Member::identity).equal(identity))
            }
        } catch (e: NoResultException) {
            throw MemberException(MemberExceptionMessage.MEMBER_IS_NULL)
        }
    }

    override fun findOneDtoByIdentity(identity: String): MemberInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Member::id),
                    col(Member::email),
                    col(Member::auth)
                ))
                from(entity(Member::class))
                where(col(Member::identity).equal(identity))
            }
        } catch (e: NoResultException) {
            throw MemberException(MemberExceptionMessage.MEMBER_IS_NULL)
        }
    }
}