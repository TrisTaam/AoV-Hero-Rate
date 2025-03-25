package com.tristaam.aovherorate.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "hero_rate",
    primaryKeys = ["hero_id", "game_mode_id", "rank_id"],
    foreignKeys = [
        ForeignKey(
            entity = HeroEntity::class,
            parentColumns = ["id"],
            childColumns = ["hero_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GameModeEntity::class,
            parentColumns = ["id"],
            childColumns = ["game_mode_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RankEntity::class,
            parentColumns = ["id"],
            childColumns = ["rank_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HeroRateEntity(
    @ColumnInfo(name = "hero_id")
    val heroId: String,

    @ColumnInfo(name = "game_mode_id")
    val gameModeId: String,

    @ColumnInfo(name = "rank_id")
    val rankId: String,

    @ColumnInfo(name = "win_rate")
    val winRate: Float? = null,

    @ColumnInfo(name = "pick_rate")
    val pickRate: Float? = null,

    @ColumnInfo(name = "ban_rate")
    val banRate: Float? = null
)