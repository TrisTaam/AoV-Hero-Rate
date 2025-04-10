package com.tristaam.aovherorate.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.tristaam.aovherorate.data.source.local.entity.HeroRateEntity
import com.tristaam.aovherorate.data.source.local.entity.HeroRateWithHeroRel
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroRateDao {
    @Upsert
    suspend fun upsertAllHeroRateEntities(heroRateEntities: List<HeroRateEntity>)

    @Transaction
    @Query(
        """
        SELECT *
        FROM hero_rate
        WHERE server_id = :serverId AND game_mode_id = :gameModeId AND rank_id = :rankId
        ORDER BY win_rate DESC
    """
    )
    fun getHeroRatesWithHeroRelByServerIdAndGameModeIdAndRankId(
        serverId: String,
        gameModeId: String,
        rankId: String
    ): Flow<List<HeroRateWithHeroRel>>

    @Transaction
    @Query(
        """
        SELECT *
        FROM hero_rate
        JOIN hero ON hero_rate.hero_id = hero.id
        JOIN hero_type ON hero.hero_type_id = hero_type.id
        WHERE server_id = :serverId AND game_mode_id = :gameModeId AND rank_id = :rankId AND hero.hero_type_id = :heroTypeId
        ORDER BY win_rate DESC
    """
    )
    fun getHeroRatesWithHeroRelByServerIdAndGameModeIdAndRankIdAndHeroTypeId(
        serverId: String,
        gameModeId: String,
        rankId: String,
        heroTypeId: String
    ): Flow<List<HeroRateWithHeroRel>>
}