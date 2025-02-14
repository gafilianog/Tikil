package dev.gafilianog.tikil.data.repository

import dev.gafilianog.tikil.data.remote.CypressTikilApiService
import dev.gafilianog.tikil.domain.model.TikilHadirModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TikilRepository @Inject constructor(
    private val api: CypressTikilApiService
) {

    suspend fun submitTikil(authToken: String, data: TikilHadirModel): Unit? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.triggerCypress(
                    authToken = authToken,
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
//                    response.body()
                } else {
//                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}