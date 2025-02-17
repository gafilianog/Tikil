package dev.gafilianog.tikil.data.repository

import dev.gafilianog.tikil.BuildConfig
import dev.gafilianog.tikil.data.remote.CypressTikilApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class TikilRepository @Inject constructor(
    private val api: CypressTikilApiService
) {

    suspend fun submitTikil(jsonBody: String): Result<Int> {
        return try {
            val requestBody = jsonBody.toRequestBody("application/json".toMediaType())

            val response = api.triggerCypress(
                authToken = "Bearer ${BuildConfig.GITHUB_ACTION_TOKEN}",
                body = requestBody
            )
            if (response.isSuccessful) {
                Result.success(response.code())
            } else {
                Result.failure(Exception("API request failed with code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}