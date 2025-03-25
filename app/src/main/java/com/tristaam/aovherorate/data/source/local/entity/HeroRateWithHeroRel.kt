package com.tristaam.aovherorate.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class HeroRateWithHeroRel(
    @Embedded
    val heroRateEntity: HeroRateEntity,

    @Relation(
        parentColumn = "hero_id",
        entityColumn = "id",
        entity = HeroEntity::class
    )
    val heroWithHeroTypeRel: HeroWithHeroTypeRel
)