package com.tristaam.aovherorate.data.mapper

import com.tristaam.aovherorate.data.source.local.entity.HeroEntity
import com.tristaam.aovherorate.data.source.local.entity.HeroWithHeroTypeRel
import com.tristaam.aovherorate.data.source.remote.dto.config.HeroResponse
import com.tristaam.aovherorate.domain.model.Hero

fun HeroResponse.toHeroEntity(id: String): HeroEntity {
    return HeroEntity(
        id = id,
        name = name,
        image = image,
        heroTypeId = heroTypeId
    )
}

fun HeroWithHeroTypeRel.toHero(): Hero {
    return Hero(
        id = heroEntity.id,
        name = heroEntity.name,
        image = heroEntity.image,
        type = heroTypeEntity.toHeroType()
    )
}