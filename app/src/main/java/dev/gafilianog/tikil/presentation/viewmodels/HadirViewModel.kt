package dev.gafilianog.tikil.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gafilianog.tikil.data.repository.TikilRepository
import dev.gafilianog.tikil.domain.model.TikilHadirModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HadirViewModel @Inject constructor(
    private val repository: TikilRepository
) : ViewModel() {

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

    private val _reason = MutableStateFlow("")
    val reason = _reason.asStateFlow()

    private val _selectedSpv = MutableStateFlow("")
    val selectedSpv = _selectedSpv.asStateFlow()

    private val _comment = MutableStateFlow("")
    val comment = _comment.asStateFlow()

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

    private fun getDateDiff(): Int {
        return 0
    }

    fun submitTikil() {
        viewModelScope.launch {
            val result = repository.submitTikil(
                "",
                TikilHadirModel(
                    npp = _npp.value,
                    password = _password.value,
                    clockOut = _clockOut.value,
                    clockIn = _clockIn.value,
                    dateDiff = getDateDiff(),
                    reason = _reason.value,
                    witness = _selectedSpv.value,
                    comment = _comment.value
                )
            )
        }
    }
}