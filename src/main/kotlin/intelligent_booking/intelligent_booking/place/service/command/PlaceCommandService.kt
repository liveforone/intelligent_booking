package intelligent_booking.intelligent_booking.place.service.command

import intelligent_booking.intelligent_booking.member.repository.MemberRepository
import intelligent_booking.intelligent_booking.place.domain.Address
import intelligent_booking.intelligent_booking.place.domain.Place
import intelligent_booking.intelligent_booking.place.dto.request.CreatePlace
import intelligent_booking.intelligent_booking.place.dto.update.UpdateAddress
import intelligent_booking.intelligent_booking.place.dto.update.UpdateTel
import intelligent_booking.intelligent_booking.place.repository.PlaceRepository
import intelligent_booking.intelligent_booking.timetable.service.command.TimetableCommandService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class PlaceCommandService @Autowired constructor(
    private val placeRepository: PlaceRepository,
    private val memberRepository: MemberRepository,
    private val timetableCommandService: TimetableCommandService
) {

    fun createPlace(createPlace: CreatePlace, memberUUID: UUID): UUID {
        return with(createPlace) {
            Place.create(
                member = memberRepository.findOneByUUID(memberUUID),
                name!!,
                tel!!,
                Address(city!!, roadNum!!, detail!!)
            ).run { placeRepository.save(this).uuid }
        }
    }

    fun updateTel(updateTel: UpdateTel, uuid: UUID) {
        placeRepository.findOneByUUID(uuid)
            .also { it.updateTel(updateTel.tel!!) }
    }

    fun updateAddress(updateAddress: UpdateAddress, uuid: UUID) {
        with(updateAddress) {
            placeRepository.findOneByUUID(uuid)
                .also { it.updateAddress(city!!, roadNum!!, detail!!) }
        }
    }

    fun deletePlaceByMember(memberUUID: UUID) {
        placeRepository.findOneByMember(memberUUID)
            .also {
                timetableCommandService.deleteTimetableByPlace(it)
                placeRepository.delete(it)
            }
    }
}