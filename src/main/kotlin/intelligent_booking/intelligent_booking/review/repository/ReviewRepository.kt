package intelligent_booking.intelligent_booking.review.repository

import intelligent_booking.intelligent_booking.review.domain.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long>, ReviewCustomRepository