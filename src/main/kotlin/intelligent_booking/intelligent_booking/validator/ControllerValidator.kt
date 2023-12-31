package intelligent_booking.intelligent_booking.validator

import intelligent_booking.intelligent_booking.exception.exception.BindingException
import org.springframework.validation.BindingResult
import java.util.*

fun validateBinding(bindingResult: BindingResult) {
    if (bindingResult.hasErrors()) {
        val errorMessage = Objects
            .requireNonNull(bindingResult.fieldError)
            ?.defaultMessage
        throw errorMessage?.let { BindingException(it) }!!
    }
}