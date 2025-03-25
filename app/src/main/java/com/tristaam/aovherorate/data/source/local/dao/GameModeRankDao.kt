package com.tristaam.aovherorate.data.source.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.tristaam.aovherorate.data.source.local.entity.GameModeRankCrossRef

@Dao
interface GameModeRankDao {
    @Upsert
    suspend fun connectAll(gameModeRankCrossRefs: List<GameModeRankCrossRef>)
}