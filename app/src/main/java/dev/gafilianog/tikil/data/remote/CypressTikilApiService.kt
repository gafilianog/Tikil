package dev.gafilianog.tikil.data.remote

import dev.gafilianog.tikil.domain.model.TikilHadirModel
import dev.gafilianog.tikil.domain.model.TikilSubmitRequest
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface CypressTikilApiService {

    @Headers(
        "Accept: application/vnd.github+json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @POST("repos/gafilianog/cypress-tikil/actions/workflows/main.yml/dispatches")
    suspend fun triggerCypress(
        @Header("Authorization") authToken: String,
        @Body body: RequestBody
    ): Response<Unit>
}