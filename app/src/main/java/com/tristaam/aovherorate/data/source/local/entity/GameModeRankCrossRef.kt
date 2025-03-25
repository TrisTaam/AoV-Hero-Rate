package com.tristaam.aovherorate.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "game_mode_rank",
    primaryKeys = ["game_mode_id", "rank_id"],
    foreignKeys = [
        ForeignKey(
            entity = GameModeEntity::class,
            parentColumns = ["id"],
            childColumns = ["game_mode_id"]
        ),
        ForeignKey(
            entity = RankEntity::class,
            parentColumns = ["id"],
            childColumns = ["rank_id"]
        )
    ]
)
data class GameModeRankCrossRef(
    @ColumnInfo(name = "game_mode_id")
    val gameModeId: String,

    @ColumnInfo(name = "rank_id")
    val rankId: String
)