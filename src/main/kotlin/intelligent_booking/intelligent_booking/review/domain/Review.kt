package intelligent_booking.intelligent_booking.review.domain

import intelligent_booking.intelligent_booking.globalUtil.UUID_TYPE
import intelligent_booking.intelligent_booking.globalUtil.createUUID
import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.place.domain.Place
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime
import java.util.*

@Entity
class Review private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(columnDefinition = UUID_TYPE, unique = true, nullable = false) val uuid: UUID = createUUID(),
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(updatable = false) @OnDelete(action = OnDeleteAction.CASCADE) val place: Place,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(updatable = false) val member: Member,
    @Column(nullable = false) val content: String,
    @Column(nullable = false, updatable = false) val createdDate: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun create(place: Place, member: Member, content: String) = Review(id = null, place = place, member = member, content = content)
    }
}