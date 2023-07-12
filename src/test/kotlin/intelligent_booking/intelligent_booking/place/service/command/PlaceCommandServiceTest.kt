package intelligent_booking.intelligent_booking.place.service.command

import intelligent_booking.intelligent_booking.member.dto.request.SignupRequest
import intelligent_booking.intelligent_booking.member.service.command.MemberCommandService
import intelligent_booking.intelligent_booking.place.domain.Address
import intelligent_booking.intelligent_booking.place.dto.request.CreatePlace
import intelligent_booking.intelligent_booking.place.dto.update.UpdateAddress
import intelligent_booking.intelligent_booking.place.dto.update.UpdateTel
import intelligent_booking.intelligent_booking.place.service.query.PlaceQueryService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@SpringBootTest
class PlaceCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val placeCommandService: PlaceCommandService,
    private val placeQueryService: PlaceQueryService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    private fun createMember(): UUID {
        val email = "test_place@gmail.com"
        val pw = "1234"
        val request = SignupRequest(email, pw)

        //when
        val uuid = memberCommandService.signupPresident(request)
        flushAndClear()
        return uuid
    }

    @Test
    @Transactional
    fun createPlace() {
        //given
        val name = "test_place1"
        val tel = "01012345678"
        val city = "서울"
        val roadNum = "잠실-1-1"
        val detail = "101동 101호"
        val request = CreatePlace(name, tel, city, roadNum, detail)
        val member = createMember()

        //when
        val uuid = placeCommandService.createPlace(request, member)
        flushAndClear()

        //then
        Assertions.assertThat(placeQueryService.getPlaceByMember(member).uuid).isEqualTo(uuid)
    }

    @Test
    @Transactional
    fun updateTel() {
        //given
        val name = "test_place2"
        val tel = "01012345678"
        val city = "서울"
        val roadNum = "잠실-1-1"
        val detail = "101동 101호"
        val request = CreatePlace(name, tel, city, roadNum, detail)
        val member = createMember()
        val uuid = placeCommandService.createPlace(request, member)
        flushAndClear()

        //when
        val updatedTel = "0212345678"
        val updateRequest = UpdateTel(updatedTel)
        placeCommandService.updateTel(updateRequest, uuid)
        flushAndClear()

        //then
        Assertions.assertThat(placeQueryService.getPlaceByUUID(uuid).tel).isEqualTo(updatedTel)
    }

    @Test
    @Transactional
    fun updateAddress() {
        //given
        val name = "test_place2"
        val tel = "01012345678"
        val city = "서울"
        val roadNum = "잠실-1-1"
        val detail = "101동 101호"
        val request = CreatePlace(name, tel, city, roadNum, detail)
        val member = createMember()
        val uuid = placeCommandService.createPlace(request, member)
        flushAndClear()

        //when
        val updatedCity = "경기"
        val updatedRoad = "대왕 판교로-2-2"
        val updatedDetail = "707동 707호"
        val updateRequest = UpdateAddress(updatedCity, updatedRoad, updatedDetail)
        placeCommandService.updateAddress(updateRequest, uuid)
        flushAndClear()

        //then
        Assertions.assertThat(placeQueryService.getPlaceByUUID(uuid).address)
            .isEqualTo(Address(updatedCity, updatedRoad, updatedDetail))
    }
}