package com.tristaam.aovherorate.domain.model

data class HeroRate(
    val hero: Hero,
    val winRate: Float? = null,
    val pickRate: Float? = null,
    val banRate: Float? = null
)