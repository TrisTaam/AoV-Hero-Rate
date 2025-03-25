package com.tristaam.aovherorate.data.mapper

import com.tristaam.aovherorate.data.source.local.entity.HeroRateEntity
import com.tristaam.aovherorate.data.source.local.entity.HeroRateWithHeroRel
import com.tristaam.aovherorate.data.source.remote.dto.server_trend.HeroRateResponse
import com.tristaam.aovherorate.domain.model.HeroRate

fun HeroRateResponse.toHeroRateEntity(rankId: String, gameModeId: String): HeroRateEntity {
    return HeroRateEntity(
        rankId = rankId,
        gameModeId = gameModeId,
        heroId = heroId,
        winRate = winRate,
        pickRate = pickRate,
        banRate = banRate
    )
}

fun HeroRateWithHeroRel.toHeroRate(): HeroRate {
    return HeroRate(
        hero = heroWithHeroTypeRel.toHero(),
        winRate = heroRateEntity.winRate,
        pickRate = heroRateEntity.pickRate,
        banRate = heroRateEntity.banRate
    )
}