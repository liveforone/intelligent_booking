package intelligent_booking.intelligent_booking

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.HiddenHttpMethodFilter

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!

@SpringBootApplication
class IntelligentBookingApplication

fun main(args: Array<String>) {
	runApplication<IntelligentBookingApplication>(*args)

	@Bean
	fun hiddenMethodFilter(): HiddenHttpMethodFilter = HiddenHttpMethodFilter()
}
