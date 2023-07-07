package intelligent_booking.intelligent_booking.converter

import intelligent_booking.intelligent_booking.member.domain.Role
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class RoleConverter : AttributeConverter<Role, String> {

    override fun convertToDatabaseColumn(attribute: Role): String = attribute.name

    override fun convertToEntityAttribute(dbData: String): Role = Role.valueOf(dbData)
}