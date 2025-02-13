package dev.gafilianog.tikil.domain.model

data class TikilHadirModel(
    val npp: String,
    val password: String,
    val clockOut: String = "DEFAULT",
    val clockIn: String,
    val dateDiff: Int = 0,
    val reason: String,
    val witness: String,
    val comment: String
)
