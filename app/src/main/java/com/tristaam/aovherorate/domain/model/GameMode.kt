package com.tristaam.aovherorate.domain.model

data class GameMode(
    val id: String,
    val name: String,
    val image: String,
    val imageActive: String,
    val hasRank: Boolean,
    val ranks: List<Rank> = emptyList()
)