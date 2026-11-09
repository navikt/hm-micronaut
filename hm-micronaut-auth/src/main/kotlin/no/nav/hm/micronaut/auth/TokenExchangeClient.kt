package no.nav.hm.micronaut.auth

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("\${tokenx.url}")
interface TokenExchangeClient {

    @Post("/")
    suspend fun exchangeToken(
        @Body request: TokenExchangeRequest
    ): HttpResponse<TokenExchangeResponse>

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

