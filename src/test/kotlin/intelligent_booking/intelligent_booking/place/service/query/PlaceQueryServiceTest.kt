package intelligent_booking.intelligent_booking.place.service.query

import intelligent_booking.intelligent_booking.logger
import intelligent_booking.intelligent_booking.member.dto.request.SignupRequest
import intelligent_booking.intelligent_booking.member.service.command.MemberCommandService
import intelligent_booking.intelligent_booking.place.dto.request.CreatePlace
import intelligent_booking.intelligent_booking.place.service.command.PlaceCommandService
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class PlaceQueryServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val placeQueryService: PlaceQueryService,
    private val placeCommandService: PlaceCommandService,
    private val memberCommandService: MemberCommandService
) {

    private fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    private fun createMember(): String {
        val email = "test_place_query@gmail.com"
        val pw = "1234"
        val request = SignupRequest(email, pw)
        return memberCommandService.signupPresident(request)
    }

    @Test
    @Transactional
    fun searchByName() {
        //given
        val member = createMember()
        val name = "test1"
        val tel = "0212345678"
        val city = "서울"
        val roadNum = "잠실"
        val detail = "101동 102호"
        val request = CreatePlace(name, tel, city, roadNum, detail)
        placeCommandService.createPlace(request, member)
        flushAndClear()

        //when
        val keyword = "t"
        val places = placeQueryService.searchByName(keyword, 0)

        //then
        places.map { logger().info("${it.id}") }
    }

    @Test
    @Transactional
    fun searchByAddress() {
        //given
        val member = createMember()
        val name = "test1"
        val tel = "0212345678"
        val city = "서울"
        val roadNum = "잠실"
        val detail = "101동 102호"
        val request = CreatePlace(name, tel, city, roadNum, detail)
        placeCommandService.createPlace(request, member)
        flushAndClear()

        //when
        val places = placeQueryService.searchByAddress("서", "잠", "1", 0)

        //then
        places.map { logger().info("${it.id}") }
    }
}