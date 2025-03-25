package com.tristaam.aovherorate.data.source.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.tristaam.aovherorate.data.source.local.entity.HeroEntity

@Dao
interface HeroDao {
    @Upsert
    suspend fun upsertAllHeroEntities(heroEntities: List<HeroEntity>)
}