package com.tristaam.aovherorate.data.repository

import com.tristaam.aovherorate.data.mapper.toHeroType
import com.tristaam.aovherorate.data.source.local.dao.HeroTypeDao
import com.tristaam.aovherorate.domain.model.HeroType
import com.tristaam.aovherorate.domain.repository.HeroTypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class HeroTypeRepositoryImpl(
    private val heroTypeDao: HeroTypeDao
) : HeroTypeRepository {
    override fun getAllHeroTypes(): Flow<List<HeroType>> {
        return heroTypeDao.getAllHeroTypeEntities().map { heroTypeEntities ->
            heroTypeEntities.map { it.toHeroType() }
        }.flowOn(Dispatchers.IO)
    }
}