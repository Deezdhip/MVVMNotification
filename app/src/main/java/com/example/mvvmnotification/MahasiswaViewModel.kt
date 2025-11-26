package com.example.mvvmnotification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Mahasiswa(
    val nama: String,
    val nim: String,
    val jurusan: String
)

data class MahasiswaUiState(
    val mahasiswaList: List<Mahasiswa> = emptyList(),
    val isLoading: Boolean = false
)

class MahasiswaViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(MahasiswaUiState())
    val uiState: StateFlow<MahasiswaUiState> = _uiState.asStateFlow()

    private val repository = MahasiswaRepository(application)
    private val notificationHelper = NotificationHelper(application)

    fun processData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Simulate background process (5 seconds)
            delay(5000)

            // Load data from JSON
            val mahasiswaList = repository.loadMahasiswaData()

            _uiState.value = _uiState.value.copy(
                mahasiswaList = mahasiswaList,
                isLoading = false
            )

            // Show notification
            notificationHelper.showNotification(
                title = "Proses Selesai",
                message = "Data ${mahasiswaList.size} mahasiswa berhasil diproses"
            )
        }
    }
}