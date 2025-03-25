package com.tristaam.aovherorate.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tristaam.aovherorate.data.source.local.entity.HeroTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroTypeDao {
    @Upsert
    suspend fun upsertAllHeroTypeEntities(heroTypeEntities: List<HeroTypeEntity>)

    @Query("SELECT * FROM hero_type")
    fun getAllHeroTypeEntities(): Flow<List<HeroTypeEntity>>
}