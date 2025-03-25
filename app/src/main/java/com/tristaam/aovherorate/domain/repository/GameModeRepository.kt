package com.tristaam.aovherorate.domain.repository

import com.tristaam.aovherorate.domain.model.GameMode
import kotlinx.coroutines.flow.Flow

interface GameModeRepository {
    fun getAllGameModes(): Flow<List<GameMode>>
}