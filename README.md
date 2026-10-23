# eessi-pensjon-documentation-api
Backend for [eessi-pensjon-documentation](https://github.com/navikt/eessi-pensjon-documentation),
dokumentasjonsportalen for EESSI Pensjon.

| Miljø | URL |
|---|---|
| dev-gcp | https://eessi-pensjon-documentation-api.intern.dev.nav.no |

## Stack

Kotlin, Spring Boot (Web MVC, Security OAuth2 Resource Server, Actuator), Maven, Java 25.

## Endepunkter

| Endepunkt | Tilgang | Beskrivelse |
|---|---|---|
| `GET /api/ping` | Entra ID-token | Svarer med status, tidspunkt og `NAVident` fra tokenet |
| `GET /actuator/health/{liveness,readiness}` | Åpen | Helsesjekker for NAIS |
| `GET /actuator/prometheus` | Åpen | Metrikker |

Alle andre kall krever et gyldig Entra ID-token med denne appen som audience. Frontenden henter det
som on-behalf-of-token via Texas. Inbound access policy slipper bare inn `eessi-pensjon-documentation`.

## Utvikling lokalt

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local   # http://localhost:8080, ingen autentisering
mvn clean verify                                       # bygg og tester
```

## Bygg og deploy

`.github/workflows/build-deploy.yaml` bygger og tester på alle PR-er. Push til `main` bygger image og
deployer til dev-gcp med `.nais/nais.yaml` + `.nais/dev.yaml`.
>>>>>>> 169faec (F! - Add eessi-pensjon-documentation-api)
