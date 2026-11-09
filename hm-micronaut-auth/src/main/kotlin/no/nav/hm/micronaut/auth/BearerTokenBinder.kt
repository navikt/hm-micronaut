package no.nav.hm.micronaut.auth

import io.micronaut.core.annotation.Introspected
import io.micronaut.core.bind.ArgumentBinder.BindingResult
import io.micronaut.core.convert.ArgumentConversionContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.bind.binders.TypedRequestArgumentBinder
import io.micronaut.security.token.reader.TokenResolver
import jakarta.inject.Singleton
import java.util.Optional

@Singleton
class BearerTokenBinder(private val tokenResolver : TokenResolver<HttpRequest<*>>) : TypedRequestArgumentBinder<BearerToken> {

    override fun argumentType(): Argument<BearerToken> =
        Argument.of(BearerToken::class.java)

    override fun bind(
        context: ArgumentConversionContext<BearerToken>,
        source: HttpRequest<*>,
    ): BindingResult<BearerToken> {
        val token = tokenResolver.resolveTokens(source).firstOrNull()?.let { BearerToken(it) }
        return BindingResult {  Optional.ofNullable(token) }
    }
}

@Introspected
data class BearerToken(val value: String)