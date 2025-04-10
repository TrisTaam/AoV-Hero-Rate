package com.tristaam.aovherorate.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tristaam.aovherorate.domain.model.GameMode
import com.tristaam.aovherorate.domain.model.HeroRate
import com.tristaam.aovherorate.domain.model.HeroType
import com.tristaam.aovherorate.domain.model.Result
import com.tristaam.aovherorate.domain.model.Server
import com.tristaam.aovherorate.domain.repository.GameModeRepository
import com.tristaam.aovherorate.domain.repository.HeroRateRepository
import com.tristaam.aovherorate.domain.repository.HeroTypeRepository
import com.tristaam.aovherorate.domain.repository.RemoteRepository
import com.tristaam.aovherorate.domain.repository.ServerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val remoteRepository: RemoteRepository,
    private val gameModeRepository: GameModeRepository,
    private val heroTypeRepository: HeroTypeRepository,
    private val heroRateRepository: HeroRateRepository,
    private val serverRepository: ServerRepository
) : ViewModel() {
    private val _selectedServerPosition = MutableStateFlow(0)

    private val _selectedGameModePosition = MutableStateFlow(0)

    private val _selectedRankPosition = MutableStateFlow(0)

    private val _selectedHeroTypePosition = MutableStateFlow(0)

    private val _refreshTrigger = MutableStateFlow(0)

    private val _sortType = MutableStateFlow(SortType.WIN_RATE_DESC)

    private val servers = serverRepository.getAllServers()

    private val gameModes = gameModeRepository.getAllGameModes().map { gameModes ->
        val newGameModes = mutableListOf<GameMode>()
        with(gameModes) {
            find { it.id == "4" }?.let { newGameModes.add(it) }
            find { it.id == "5" }?.let { newGameModes.add(it) }
            find { it.id == "-1" }?.let { newGameModes.add(it) }
        }
        newGameModes
    }

    private val heroTypes = heroTypeRepository.getAllHeroTypes()

    private val servedId = combine(
        servers,
        _selectedServerPosition
    ) { servers, selectedServerPosition ->
        val serverId = servers.getOrNull(selectedServerPosition)?.id ?: "1"
        serverId
    }

    private val gameModeId = combine(
        gameModes,
        _selectedGameModePosition
    ) { gameModes, selectedGameModePosition ->
        val gameModeId = gameModes.getOrNull(selectedGameModePosition)?.id ?: "4"
        gameModeId
    }

    private val rankId = combine(
        gameModes,
        _selectedGameModePosition,
        _selectedRankPosition
    ) { gameModes, selectedGameModePosition, selectedRankPosition ->
        val gameMode = gameModes.getOrNull(selectedGameModePosition)
        val rankId = gameMode?.ranks?.getOrNull(selectedRankPosition)?.id ?: "-1"
        rankId
    }

    private val heroTypeId = combine(
        heroTypes,
        _selectedHeroTypePosition
    ) { heroTypes, selectedHeroTypePosition ->
        val heroTypeId = heroTypes.getOrNull(selectedHeroTypePosition)?.id ?: "-1"
        heroTypeId
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val heroRates = combine(
        servedId,
        gameModeId,
        rankId,
        heroTypeId,
    ) { serverId, gameModeId, rankId, heroTypeId ->
        Pair(Pair(serverId, gameModeId), Pair(rankId, heroTypeId))
    }.flatMapLatest { pair ->
        heroRateRepository.getHeroRatesByServerIdAndGameModeIdAndRankIdAndHeroTypeId(
            pair.first.first,
            pair.first.second,
            pair.second.first,
            pair.second.second
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val remoteData = _refreshTrigger
        .flatMapLatest { trigger ->
            if (trigger == 0) {
                return@flatMapLatest flowOf(Result.Success(System.currentTimeMillis()))
            }
            remoteRepository.getConfig()
                .flatMapLatest { configResult ->
                    when (configResult) {
                        is Result.Success -> remoteRepository.getServerTrend()
                        is Result.Error -> flowOf(Result.Error(configResult.exception))
                        is Result.Loading -> flowOf(Result.Loading)
                    }
                }
        }

    val uiState = combine(
        servers,
        gameModes,
        heroTypes,
        heroRates,
        remoteData,
    ) { servers, gameModes, heroTypes, heroRates, remoteResult ->
        when (remoteResult) {
            is Result.Loading -> HomeUIState(
                isLoading = true,
                servers = servers,
                gameModes = gameModes,
                heroTypes = heroTypes,
                heroRates = heroRates
            )

            is Result.Success -> HomeUIState(
                isLoading = false,
                errorMessage = null,
                servers = servers,
                gameModes = gameModes,
                heroTypes = heroTypes,
                heroRates = heroRates
            )

            is Result.Error -> HomeUIState(
                isLoading = false,
                errorMessage = remoteResult.exception.message ?: "",
                servers = servers,
                gameModes = gameModes,
                heroTypes = heroTypes,
                heroRates = heroRates
            )
        }
    }.combine(
        _selectedServerPosition
    ) { uiState, selectedServerPosition ->
        uiState.copy(
            selectedServerPosition = selectedServerPosition,
        )
    }.combine(
        _selectedGameModePosition
    ) { uiState, selectedGameModePosition ->
        uiState.copy(
            selectedGameModePosition = selectedGameModePosition,
        )
    }.combine(
        _selectedRankPosition
    ) { uiState, selectedRankPosition ->
        uiState.copy(
            selectedRankPosition = selectedRankPosition,
        )
    }.combine(
        _selectedHeroTypePosition
    ) { uiState, selectedHeroTypePosition ->
        uiState.copy(
            selectedHeroTypePosition = selectedHeroTypePosition,
        )
    }.combine(
        _sortType
    ) { uiState, sortType ->
        uiState.copy(
            sortType = sortType,
            heroRates = uiState.heroRates.sortedByDescending {
                when (sortType) {
                    SortType.WIN_RATE_DESC -> it.winRate
                    SortType.PICK_RATE_DESC -> it.pickRate
                    SortType.BAN_RATE_DESC -> it.banRate
                }
            }
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUIState(isInitial = true)
        )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.Refresh -> {
                refresh()
            }

            is HomeAction.SelectServer -> {
                selectServer(action.position)
            }

            is HomeAction.SelectGameMode -> {
                selectGameMode(action.position)
            }

            is HomeAction.SelectHeroType -> {
                selectHeroType(action.position)
            }

            is HomeAction.SelectRank -> {
                selectRank(action.position)
            }

            is HomeAction.SelectSortType -> {
                selectSortType(action.sortType)
            }
        }
    }

    private fun refresh() {
        _refreshTrigger.update {
            it + 1
        }
    }

    private fun selectServer(position: Int) {
        _selectedServerPosition.update {
            position
        }
    }

    private fun selectGameMode(position: Int) {
        _selectedGameModePosition.update {
            position
        }
        _selectedRankPosition.update {
            0
        }
    }

    private fun selectHeroType(position: Int) {
        _selectedHeroTypePosition.update {
            position
        }
    }

    private fun selectRank(position: Int) {
        _selectedRankPosition.update {
            position
        }
    }

    private fun selectSortType(sortType: SortType) {
        _sortType.update {
            sortType
        }
    }
}

sealed interface HomeAction {
    data object Refresh : HomeAction
    data class SelectServer(val position: Int) : HomeAction
    data class SelectGameMode(val position: Int) : HomeAction
    data class SelectHeroType(val position: Int) : HomeAction
    data class SelectRank(val position: Int) : HomeAction
    data class SelectSortType(val sortType: SortType) : HomeAction
}

data class HomeUIState(
    val isInitial: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val servers: List<Server> = emptyList(),
    val gameModes: List<GameMode> = emptyList(),
    val heroTypes: List<HeroType> = emptyList(),
    val heroRates: List<HeroRate> = emptyList(),
    val selectedServerPosition: Int = 0,
    val selectedGameModePosition: Int = 0,
    val selectedHeroTypePosition: Int = 0,
    val selectedRankPosition: Int = 0,
    val sortType: SortType = SortType.WIN_RATE_DESC
)

enum class SortType(val str: String, val color: Long) {
    WIN_RATE_DESC("Win", 0xFF4CAF50),
    PICK_RATE_DESC("Pick", 0xFF2196F3),
    BAN_RATE_DESC("Ban", 0xFFF44336)
}