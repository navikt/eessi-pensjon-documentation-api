package no.nav.eessi.pensjon.documentation.ping

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

/** Lets the frontend verify that it can reach the API with a valid token. */
@RestController
@RequestMapping("/api")
class PingController(
    @param:Value("\${spring.application.name}") private val application: String,
) {

    @GetMapping("/ping")
    fun ping(@AuthenticationPrincipal jwt: Jwt?): PingResponse =
        PingResponse(
            status = "OK",
            application = application,
            timestamp = Instant.now(),
            navIdent = jwt?.getClaimAsString("NAVident"),
        )
}

data class PingResponse(
    val status: String,
    val application: String,
    val timestamp: Instant,
    val navIdent: String?,
)
