package com.tristaam.aovherorate.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tristaam.aovherorate.data.source.local.entity.RankEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RankDao {
    @Upsert
    suspend fun insertAllRankEntities(rankEntities: List<RankEntity>)

    @Query("SELECT * FROM rank")
    fun getAllRankEntities(): Flow<List<RankEntity>>
}