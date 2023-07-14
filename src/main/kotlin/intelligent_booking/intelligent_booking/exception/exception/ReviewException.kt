package intelligent_booking.intelligent_booking.exception.exception

import intelligent_booking.intelligent_booking.exception.message.ReviewExceptionMessage

class ReviewException(val reviewExceptionMessage: ReviewExceptionMessage) : RuntimeException(reviewExceptionMessage.message)