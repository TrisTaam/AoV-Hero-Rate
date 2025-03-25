package com.tristaam.aovherorate.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class HeroWithHeroTypeRel(
    @Embedded
    val heroEntity: HeroEntity,

    @Relation(
        parentColumn = "hero_type_id",
        entityColumn = "id",
    )
    val heroTypeEntity: HeroTypeEntity
)