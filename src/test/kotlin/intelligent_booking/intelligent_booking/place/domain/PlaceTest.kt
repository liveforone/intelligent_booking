package intelligent_booking.intelligent_booking.place.domain

import intelligent_booking.intelligent_booking.member.domain.Member
import intelligent_booking.intelligent_booking.member.domain.Role
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PlaceTest {

    private fun createMember(): Member {
        return Member.create("domain_test@gmail.com", "1234", Role.PRESIDENT)
    }

    @Test
    fun updateTel() {
        //given
        val member = createMember()
        val name = "test_place"
        val tel = "0212345678"
        val address = Address("서울", "잠실-1-1", "101동 101호")
        val place = Place.create(member, name, tel, address)

        //when
        val updatedTel = "0287654321"
        place.updateTel(updatedTel)

        //then
        Assertions.assertThat(place.tel).isEqualTo(updatedTel)
    }

    @Test
    fun updateAddress() {
        //given
        val member = createMember()
        val name = "test_place"
        val tel = "0212345678"
        val address = Address("서울", "잠실-2-2", "201동 201호")
        val place = Place.create(member, name, tel, address)

        //when
        val updatedCity = "성남"
        val updatedRoadNum = "대왕 판교로-1-1"
        val updatedDetail = "505동 505호"
        place.updateAddress(updatedCity, updatedRoadNum, updatedDetail)

        //then
        Assertions.assertThat(place.address).isEqualTo(Address(updatedCity, updatedRoadNum, updatedDetail))
    }
}