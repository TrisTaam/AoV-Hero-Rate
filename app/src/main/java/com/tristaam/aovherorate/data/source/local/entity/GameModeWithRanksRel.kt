package com.tristaam.aovherorate.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GameModeWithRanksRel(
    @Embedded val gameModeEntity: GameModeEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = RankEntity::class,
        associateBy = Junction(
            GameModeRankCrossRef::class,
            parentColumn = "game_mode_id",
            entityColumn = "rank_id"
        )
    )
    val rankEntities: List<RankEntity>
)