package com.tristaam.aovherorate.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tristaam.aovherorate.data.source.local.dao.GameModeDao
import com.tristaam.aovherorate.data.source.local.dao.GameModeRankDao
import com.tristaam.aovherorate.data.source.local.dao.HeroDao
import com.tristaam.aovherorate.data.source.local.dao.HeroRateDao
import com.tristaam.aovherorate.data.source.local.dao.HeroTypeDao
import com.tristaam.aovherorate.data.source.local.dao.RankDao
import com.tristaam.aovherorate.data.source.local.entity.GameModeEntity
import com.tristaam.aovherorate.data.source.local.entity.GameModeRankCrossRef
import com.tristaam.aovherorate.data.source.local.entity.HeroEntity
import com.tristaam.aovherorate.data.source.local.entity.HeroRateEntity
import com.tristaam.aovherorate.data.source.local.entity.HeroTypeEntity
import com.tristaam.aovherorate.data.source.local.entity.RankEntity

@Database(
    entities = [
        HeroEntity::class,
        HeroTypeEntity::class,
        RankEntity::class,
        GameModeEntity::class,
        HeroRateEntity::class,
        GameModeRankCrossRef::class,
    ],
    version = 1
)
abstract class AoVHeroRateDatabase : RoomDatabase() {
    abstract val heroDao: HeroDao
    abstract val heroTypeDao: HeroTypeDao
    abstract val rankDao: RankDao
    abstract val gameModeDao: GameModeDao
    abstract val heroRateDao: HeroRateDao
    abstract val gameModeRankDao: GameModeRankDao

    companion object {
        const val DATABASE_NAME = "AoVHeroRate.db"
    }
}