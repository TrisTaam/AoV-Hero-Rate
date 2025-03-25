package com.tristaam.aovherorate.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.tristaam.aovherorate.data.source.local.entity.GameModeEntity
import com.tristaam.aovherorate.data.source.local.entity.GameModeWithRanksRel
import kotlinx.coroutines.flow.Flow

@Dao
interface GameModeDao {
    @Upsert
    suspend fun upsertAllGameModeEntities(gameModeEntities: List<GameModeEntity>)

    @Transaction
    @Query("SELECT * FROM game_mode")
    fun getAllGameModeEntitiesWithRankEntities(): Flow<List<GameModeWithRanksRel>>
}