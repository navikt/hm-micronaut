package no.nav.hm.micronaut.auth
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import io.micronaut.security.token.Claims
import io.micronaut.security.token.jwt.validator.GenericJwtClaimsValidator
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
@Requires(property="micronaut.security.token.jwt.claims-validators.acr")
class IdPortenClaimValidator<T>(@param:Value("\${micronaut.security.token.jwt.claims-validators.acr}") private val acr: String):
    GenericJwtClaimsValidator<T> {

    override fun validate(claims: Claims, request: T?): Boolean {
        LOG.debug("Validating JWT token for acr claim {}", claims[ACR_CLAIM])
        return claims[ACR_CLAIM] == acr
    }

    companion object {
        const val ACR_CLAIM = "acr"
        private val LOG = LoggerFactory.getLogger(IdPortenClaimValidator::class.java)
    }
}