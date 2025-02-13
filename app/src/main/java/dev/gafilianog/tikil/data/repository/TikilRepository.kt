package dev.gafilianog.tikil.data.repository

import dev.gafilianog.tikil.data.remote.RetrofitClient
import dev.gafilianog.tikil.domain.model.TikilHadirModel
import dev.gafilianog.tikil.domain.model.TikilSubmitResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TikilRepository {

    private val api = RetrofitClient.retrofit

    suspend fun submitTikil(): Unit? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.triggerCypress(
                    authToken = "",
                    body = TikilHadirModel(
                        npp = "",
                        password = "",
                        clockOut = "",
                        clockIn = "",
                        dateDiff = 0,
                        reason = "",
                        witness = "",
                        comment = ""
                    )
                )
            } catch (e: Exception) {
                null
            }
        }
    }
}