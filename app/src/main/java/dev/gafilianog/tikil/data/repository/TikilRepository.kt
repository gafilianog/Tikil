package dev.gafilianog.tikil.data.repository

import dev.gafilianog.tikil.data.remote.CypressTikilApiService
import dev.gafilianog.tikil.domain.model.TikilHadirModel
import javax.inject.Inject

class TikilRepository @Inject constructor(
    private val api: CypressTikilApiService,
    private val githubToken: String
) {

    suspend fun submitTikil(data: TikilHadirModel): Result<Int> {
        return try {
            val response = api.triggerCypress(
                authToken = githubToken,
                body = TikilHadirModel(
                    npp = data.npp,
                    password = data.password,
                    clockOut = data.clockOut,
                    clockIn = data.clockIn,
                    dateDiff = data.dateDiff,
                    reason = data.reason,
                    witness = data.witness,
                    comment = data.comment
                )
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