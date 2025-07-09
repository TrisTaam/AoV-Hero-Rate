package com.tristaam.aovherorate.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tristaam.aovherorate.domain.model.Result
import com.tristaam.aovherorate.domain.repository.RemoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val remoteRepository: RemoteRepository
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = remoteRepository.getRemoteData()
        .map { result ->
            when (result) {
                is Result.Success -> {
                    MainUIState(isLoading = false, errorMessage = null)
                }

                is Result.Error -> MainUIState(
                    isLoading = false,
                    errorMessage = result.exception.message
                )

                is Result.Loading -> MainUIState(isLoading = true)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = MainUIState()
        )
}

data class MainUIState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)