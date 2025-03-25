package com.tristaam.aovherorate.data.mapper

import com.tristaam.aovherorate.data.source.local.entity.HeroTypeEntity
import com.tristaam.aovherorate.data.source.remote.dto.config.HeroTypeResponse
import com.tristaam.aovherorate.domain.model.HeroType

fun HeroTypeResponse.toHeroTypeEntity(): HeroTypeEntity {
    return HeroTypeEntity(
        id = id,
        name = name,
        image = image,
        imageActive = imageActive
    )
}

fun HeroTypeEntity.toHeroType(): HeroType {
    return HeroType(
        id = id,
        name = name,
        image = image,
        imageActive = imageActive
    )
}