package intelligent_booking.intelligent_booking.authentication

import intelligent_booking.intelligent_booking.exception.exception.JwtCustomException
import intelligent_booking.intelligent_booking.exception.message.JwtExceptionMessage
import intelligent_booking.intelligent_booking.jwt.constant.JwtConstant
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthenticationInfo(@Value(JwtConstant.SECRET_KEY_PATH) secretKey: String) {

    private val key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey))

    fun getAuth(request: HttpServletRequest): String {
        val token = resolveToken(request)
        val claims = parseClaims(token)
        return claims[JwtConstant.CLAIM_NAME].toString()
    }

    private fun resolveToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(JwtConstant.HEADER)
        return bearerToken?.takeIf { it.startsWith(JwtConstant.BEARER_TOKEN) }
            ?.substring(JwtConstant.TOKEN_SUB_INDEX)
            ?: throw JwtCustomException(JwtExceptionMessage.EMPTY_CLAIMS)
    }

    private fun parseClaims(accessToken: String): Claims {
        val jwt = Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
        return jwt.parseClaimsJws(accessToken).body
    }
}