package intelligent_booking.intelligent_booking.converter

import intelligent_booking.intelligent_booking.reservation.domain.ReservationState
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class ReservationStateConverter : AttributeConverter<ReservationState, String> {

    override fun convertToDatabaseColumn(attribute: ReservationState) = attribute.name

    override fun convertToEntityAttribute(dbData: String) = ReservationState.valueOf(dbData)
}