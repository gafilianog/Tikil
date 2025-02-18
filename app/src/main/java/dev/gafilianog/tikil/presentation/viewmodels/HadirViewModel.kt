package dev.gafilianog.tikil.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gafilianog.tikil.BuildConfig
import dev.gafilianog.tikil.data.repository.TikilRepository
import dev.gafilianog.tikil.domain.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class HadirViewModel @Inject constructor(
    private val repository: TikilRepository
) : ViewModel() {

    private val _statusCode = MutableStateFlow<UiState<Int>>(UiState.Loading)
    val statusCode = _statusCode

    private val _npp = MutableStateFlow("")
    val npp = _npp.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _clockIn = MutableStateFlow("08:00")
    val clockIn = _clockIn.asStateFlow()

    private val _clockOut = MutableStateFlow("")
    val clockOut = _clockOut.asStateFlow()

    private val _selectedDate = MutableStateFlow<Long?>(System.currentTimeMillis())
    val selectedDate = _selectedDate.asStateFlow()

    private val _dateDiff = MutableStateFlow(0)
    val dateDiff = _dateDiff.asStateFlow()

    private val _reason = MutableStateFlow("E-Absensi tidak dapat digunakan")
    val reason = _reason.asStateFlow()

    private val _selectedSpv = MutableStateFlow("")
    val selectedSpv = _selectedSpv.asStateFlow()

    private val _spvList = MutableStateFlow<List<String>>(emptyList())
    val spvList = _spvList

    private val _comment = MutableStateFlow("Mohon approvalnya mas terima kasih")
    val comment = _comment.asStateFlow()

    init {
        loadSpvList()
        _selectedSpv.value = _spvList.value[0]
    }

    private fun loadSpvList() {
        _spvList.value = listOf(
            BuildConfig.SPV_1,
            BuildConfig.SPV_2,
            BuildConfig.SPV_3
        )
    }

    fun onNppChange(npp: String) {
        _npp.value = npp
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onClockInChange(clockIn: String) {
        _clockIn.value = clockIn
    }

    fun onClockOutChange(clockOut: String) {
        _clockOut.value = clockOut
    }

    fun onSelectedDateChange(selectedDate: Long?) {
        _selectedDate.value = selectedDate
        calculateDateDiff(selectedDate)
    }

    fun onReasonChange(reason: String) {
        _reason.value = reason
    }

    fun onSelectedSpvChange(selectedSpv: String) {
        _selectedSpv.value = selectedSpv
    }

    fun onCommentChange(comment: String) {
        _comment.value = comment
    }

    private fun calculateDateDiff(timestamp: Long?) {
        val selectedLocalDate = timestamp?.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }

        val today = LocalDate.now()

        _dateDiff.value = ChronoUnit.DAYS.between(today, selectedLocalDate).toInt()
    }

    fun submitTikil() {
        viewModelScope.launch {
            _statusCode.value = UiState.Loading
            val jsonBody = """
                {
                    "ref": "master",
                    "inputs": {
                        "npp": "${_npp.value}",
                        "password": "${_password.value}",
                        "clock_out": "${_clockOut.value}",
                        "clock_in": "${_clockIn.value}",
                        "date_diff": "${_dateDiff.value}",
                        "reason": "${_reason.value}",
                        "witness": "${_selectedSpv.value}",
                        "comment": "${_comment.value}"
                    }
                }
            """.trimIndent()
            val response = repository.submitTikil(jsonBody)
            _statusCode.value = if (response.isSuccess) {
                UiState.Success(response.getOrNull()!!)
            } else {
                UiState.Error(response.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}