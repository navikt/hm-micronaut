package no.nav.hm.micronaut.auth

import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Singleton
import kotlinx.coroutines.reactive.awaitSingle

@Singleton
class TokenExchangeService(
    @param:Client("tokenx")
    private val client: HttpClient,
    @param:Value("\${micronaut.http.services.tokenx.url}")
    private val endpoint: String,
) {
    suspend fun exchangeToken(
        request: TokenExchangeRequest,
    ): HttpResponse<TokenExchangeResponse> = client.exchange(
        HttpRequest.POST(endpoint, request),
        TokenExchangeResponse::class.java,
    ).awaitSingle()

}

data class TokenExchangeRequest(
    val identity_provider: String = "tokenx",
    val target: String,
    val user_token: String
)

data class TokenExchangeResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Long
)
