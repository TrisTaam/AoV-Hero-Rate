package com.tristaam.aovherorate.data.mapper

import com.tristaam.aovherorate.data.source.local.entity.RankEntity
import com.tristaam.aovherorate.data.source.remote.dto.config.RankResponse
import com.tristaam.aovherorate.domain.model.Rank

fun RankResponse.toRankEntity(): RankEntity {
    return RankEntity(
        id = id,
        name = name,
        image = image
    )
}

fun RankEntity.toRank(): Rank {
    return Rank(
        id = id,
        name = name,
        image = image
    )
}