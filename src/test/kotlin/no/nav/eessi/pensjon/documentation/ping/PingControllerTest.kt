package no.nav.eessi.pensjon.documentation.ping

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class PingControllerTest(@Autowired private val mockMvc: MockMvc) {

    @Test
    fun `ping without token is rejected`() {
        mockMvc.get("/api/ping").andExpect { status { isUnauthorized() } }
    }

    @Test
    fun `ping with token returns status and NAV-ident`() {
        mockMvc.get("/api/ping") {
            with(jwt().jwt { it.claim("NAVident", "Z990000") })
        }.andExpect {
            status { isOk() }
            jsonPath("$.status") { value("OK") }
            jsonPath("$.application") { value("eessi-pensjon-documentation-api") }
            jsonPath("$.navIdent") { value("Z990000") }
        }
    }

    @Test
    fun `health endpoint is open for NAIS probes`() {
        mockMvc.get("/actuator/health/liveness").andExpect { status { isOk() } }
    }
}
