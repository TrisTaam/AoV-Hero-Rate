package com.tristaam.aovherorate.domain.repository

import com.tristaam.aovherorate.domain.model.HeroRate
import kotlinx.coroutines.flow.Flow

interface HeroRateRepository {
    fun getHeroRatesByServerIdAndGameModeIdAndRankIdAndHeroTypeId(
        serverId: String,
        gameModeId: String,
        rankId: String,
        heroTypeId: String
    ): Flow<List<HeroRate>>
}