package com.tristaam.aovherorate.data.repository

import com.tristaam.aovherorate.data.mapper.toGameMode
import com.tristaam.aovherorate.data.source.local.dao.GameModeDao
import com.tristaam.aovherorate.data.source.local.entity.GameModeWithRanksRel
import com.tristaam.aovherorate.domain.model.GameMode
import com.tristaam.aovherorate.domain.repository.GameModeRepository
import kotlinx.coroutines.flow.Flow

class GameModeRepositoryImpl(
    private val gameModeDao: GameModeDao
) : BaseFlowRepository<GameModeWithRanksRel, GameMode>(), GameModeRepository {
    override fun getAllGameModes(): Flow<List<GameMode>> {
        return mapFlow(gameModeDao.getAllGameModeEntitiesWithRankEntities()) { it.toGameMode() }
    }
}