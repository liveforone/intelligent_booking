package intelligent_booking.intelligent_booking.place.domain

import intelligent_booking.intelligent_booking.exception.exception.PlaceException
import intelligent_booking.intelligent_booking.exception.message.PlaceExceptionMessage
import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.place.domain.constant.PlaceConstant
import jakarta.persistence.*

@Entity
class Place private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = PlaceConstant.MEMBER_COLUMN_NAME,
        referencedColumnName = PlaceConstant.IDENTITY,
        updatable = false,
        unique = true
    ) val member: Member,
    @Column(nullable = false) val name: String,
    @Column(nullable = false) var tel: String,
    @Embedded var address: Address
) {

    companion object {
        fun create(member: Member, name: String, tel: String, address: Address): Place {
            return if (member.isPresident()) Place(id = null, member, name, tel, address)
            else throw PlaceException(PlaceExceptionMessage.NOT_PRESIDENT)
        }
    }

    fun updateTel(tel: String) {
        this.tel = tel
    }

    fun updateAddress(city: String, roadNum: String, detail: String) {
        this.address = Address(city, roadNum, detail)
    }
}