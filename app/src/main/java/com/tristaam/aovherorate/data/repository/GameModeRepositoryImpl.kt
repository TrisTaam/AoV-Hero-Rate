package com.tristaam.aovherorate.data.repository

import com.tristaam.aovherorate.data.mapper.toGameMode
import com.tristaam.aovherorate.data.source.local.dao.GameModeDao
import com.tristaam.aovherorate.domain.model.GameMode
import com.tristaam.aovherorate.domain.repository.GameModeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GameModeRepositoryImpl(
    private val gameModeDao: GameModeDao
) : GameModeRepository {
    override fun getAllGameModes(): Flow<List<GameMode>> {
        return gameModeDao.getAllGameModeEntitiesWithRankEntities().map { gameModesWithRanksRel ->
            gameModesWithRanksRel.map { it.toGameMode() }
        }.flowOn(Dispatchers.IO)
    }
}