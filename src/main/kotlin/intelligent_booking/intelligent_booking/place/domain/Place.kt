package intelligent_booking.intelligent_booking.place.domain

import intelligent_booking.intelligent_booking.exception.exception.PlaceException
import intelligent_booking.intelligent_booking.exception.message.PlaceExceptionMessage
import intelligent_booking.intelligent_booking.globalUtil.UUID_TYPE
import intelligent_booking.intelligent_booking.globalUtil.createUUID
import intelligent_booking.intelligent_booking.member.domain.Member
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*

@Entity
class Place private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(columnDefinition = UUID_TYPE, unique = true, nullable = false) val uuid: UUID = createUUID(),
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(
        updatable = false,
        unique = true
    ) @OnDelete(action = OnDeleteAction.CASCADE) val member: Member,
    @Column(nullable = false) val name: String,
    @Column(nullable = false) var tel: String,
    @Embedded var address: Address
) {

    companion object {
        fun create(member: Member, name: String, tel: String, address: Address): Place {
            require(member.isPresident()) { throw PlaceException(PlaceExceptionMessage.NOT_PRESIDENT) }
            return Place(id = null, member = member, name = name, tel = tel, address = address)
        }
    }

    fun updateTel(tel: String) {
        this.tel = tel
    }

    fun updateAddress(city: String, roadNum: String, detail: String) {
        this.address = Address(city, roadNum, detail)
    }
}