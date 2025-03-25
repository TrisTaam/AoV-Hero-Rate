package com.tristaam.aovherorate.data.mapper

import com.tristaam.aovherorate.data.source.local.entity.GameModeEntity
import com.tristaam.aovherorate.data.source.local.entity.GameModeWithRanksRel
import com.tristaam.aovherorate.data.source.remote.dto.config.GameModeResponse
import com.tristaam.aovherorate.domain.model.GameMode

fun GameModeResponse.toGameModeEntity(): GameModeEntity {
    return GameModeEntity(
        id = id,
        name = name,
        image = image,
        imageActive = imageActive,
        hasRank = hasRank
    )
}

fun GameModeWithRanksRel.toGameMode(): GameMode {
    return GameMode(
        id = this.gameModeEntity.id,
        name = this.gameModeEntity.name,
        image = this.gameModeEntity.image,
        imageActive = this.gameModeEntity.imageActive,
        hasRank = this.gameModeEntity.hasRank,
        ranks = this.rankEntities.map { it.toRank() }
    )
}