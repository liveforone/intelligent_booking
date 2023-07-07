package intelligent_booking.intelligent_booking.globalConfig

import intelligent_booking.intelligent_booking.jwt.JwtAuthenticationFilter
import intelligent_booking.intelligent_booking.jwt.JwtTokenProvider
import intelligent_booking.intelligent_booking.member.controller.constant.MemberUrl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
        http.sessionManagement { session: SessionManagementConfigurer<HttpSecurity> ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        http.authorizeHttpRequests { path: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry ->
            path.requestMatchers(
                MemberUrl.SIGNUP_MEMBER,
                MemberUrl.SIGNUP_PRESIDENT,
                MemberUrl.LOGIN
            ).permitAll().anyRequest().authenticated()
        }
        http.addFilterBefore(
            JwtAuthenticationFilter(jwtTokenProvider),
            UsernamePasswordAuthenticationFilter::class.java
        )
        http.exceptionHandling { exception: ExceptionHandlingConfigurer<HttpSecurity> ->
            exception.accessDeniedPage(MemberUrl.PROHIBITION)
        }
        return http.build()
    }
}