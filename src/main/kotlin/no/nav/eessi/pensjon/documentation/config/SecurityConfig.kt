package no.nav.eessi.pensjon.documentation.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

/**
 * Every call under /api must carry an Entra ID token issued for this app
 * (the frontend gets one through on-behalf-of exchange). Only the actuator
 * endpoints used by NAIS probes and Prometheus are open.
 */
@Configuration
class SecurityConfig {

    @Bean
    @Profile("!local")
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .authorizeHttpRequests {
                it.requestMatchers("/actuator/health/**", "/actuator/prometheus").permitAll()
                it.anyRequest().authenticated()
            }
            .oauth2ResourceServer { it.jwt(withDefaults()) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .csrf { it.disable() }
            .build()

    /** Local development only: no token validation. */
    @Bean
    @Profile("local")
    fun localSecurityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .csrf { it.disable() }
            .build()
}
