package com.tristaam.aovherorate.domain.repository

import com.tristaam.aovherorate.domain.model.HeroType
import kotlinx.coroutines.flow.Flow

interface HeroTypeRepository {
    fun getAllHeroTypes(): Flow<List<HeroType>>
}