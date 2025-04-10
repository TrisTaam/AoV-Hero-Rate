package com.tristaam.aovherorate.data.repository

import com.tristaam.aovherorate.data.mapper.toHeroRate
import com.tristaam.aovherorate.data.source.local.dao.HeroRateDao
import com.tristaam.aovherorate.domain.model.HeroRate
import com.tristaam.aovherorate.domain.repository.HeroRateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class HeroRateRepositoryImpl(
    private val heroRateDao: HeroRateDao,
) : HeroRateRepository {
    override fun getHeroRatesByServerIdAndGameModeIdAndRankIdAndHeroTypeId(
        serverId: String,
        gameModeId: String,
        rankId: String,
        heroTypeId: String
    ): Flow<List<HeroRate>> {
        return if (heroTypeId == "-1") {
            heroRateDao.getHeroRatesWithHeroRelByServerIdAndGameModeIdAndRankId(
                serverId,
                gameModeId,
                rankId
            )
                .map { heroRatesWithHeroRel ->
                    heroRatesWithHeroRel.map { it.toHeroRate() }
                }
        } else {
            heroRateDao.getHeroRatesWithHeroRelByServerIdAndGameModeIdAndRankIdAndHeroTypeId(
                serverId,
                gameModeId,
                rankId,
                heroTypeId
            )
                .map { heroRatesWithHeroRel ->
                    heroRatesWithHeroRel.map { it.toHeroRate() }
                }
        }.flowOn(Dispatchers.IO)
    }
}