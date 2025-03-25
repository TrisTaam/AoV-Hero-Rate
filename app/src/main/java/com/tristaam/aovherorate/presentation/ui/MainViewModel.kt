package com.tristaam.aovherorate.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tristaam.aovherorate.domain.model.Result
import com.tristaam.aovherorate.domain.repository.RemoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val remoteRepository: RemoteRepository
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = remoteRepository.getConfig()
        .flatMapLatest { configResult ->
            when (configResult) {
                is Result.Success -> remoteRepository.getServerTrend()
                is Result.Error -> flowOf(Result.Error(configResult.exception))
                is Result.Loading -> flowOf(Result.Loading)
            }
        }
        .map { serverTrendResult ->
            when (serverTrendResult) {
                is Result.Success -> {
                    MainUIState(isLoading = false, errorMessage = null)
                }

                is Result.Error -> MainUIState(
                    isLoading = false,
                    errorMessage = serverTrendResult.exception.message
                )

                is Result.Loading -> MainUIState(isLoading = true)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainUIState()
        )
}

data class MainUIState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)