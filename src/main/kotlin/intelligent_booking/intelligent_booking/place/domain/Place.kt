package intelligent_booking.intelligent_booking.place.domain

import intelligent_booking.intelligent_booking.exception.exception.PlaceException
import intelligent_booking.intelligent_booking.exception.message.PlaceExceptionMessage
import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.place.domain.constant.PlaceConstant
import jakarta.persistence.*
import java.util.*

@Entity
class Place private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(columnDefinition = PlaceConstant.UUID_TYPE, unique = true, nullable = false) val uuid: UUID,
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(
        updatable = false,
        unique = true
    ) val member: Member,
    @Column(nullable = false) val name: String,
    @Column(nullable = false) var tel: String,
    @Embedded var address: Address
) {

    companion object {
        private fun createUuid() = UUID.randomUUID()
        fun create(member: Member, name: String, tel: String, address: Address): Place {
            require(member.isPresident()) { throw PlaceException(PlaceExceptionMessage.NOT_PRESIDENT) }
            return Place(id = null, uuid = createUuid(), member, name, tel, address)
        }
    }

    fun updateTel(tel: String) {
        this.tel = tel
    }

    fun updateAddress(city: String, roadNum: String, detail: String) {
        this.address = Address(city, roadNum, detail)
    }
}