package intelligent_booking.intelligent_booking.place.repository

import intelligent_booking.intelligent_booking.place.domain.Place
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceRepository : JpaRepository<Place, Long>, PlaceCustomRepository