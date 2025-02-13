package dev.gafilianog.tikil.domain.model

data class TikilSubmitRequest(
    val ref: String = "master",
    val inputs: TikilHadirModel
)