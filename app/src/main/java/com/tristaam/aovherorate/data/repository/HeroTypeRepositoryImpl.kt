package com.tristaam.aovherorate.data.repository

import com.tristaam.aovherorate.data.mapper.toHeroType
import com.tristaam.aovherorate.data.source.local.dao.HeroTypeDao
import com.tristaam.aovherorate.data.source.local.entity.HeroTypeEntity
import com.tristaam.aovherorate.domain.model.HeroType
import com.tristaam.aovherorate.domain.repository.HeroTypeRepository
import kotlinx.coroutines.flow.Flow

class HeroTypeRepositoryImpl(
    private val heroTypeDao: HeroTypeDao
) : BaseFlowRepository<HeroTypeEntity, HeroType>(), HeroTypeRepository {
    override fun getAllHeroTypes(): Flow<List<HeroType>> {
        return mapFlow(heroTypeDao.getAllHeroTypeEntities()) { it.toHeroType() }
    }
}