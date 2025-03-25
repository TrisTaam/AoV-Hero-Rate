package com.tristaam.aovherorate.data.source.remote.dto.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigResponse(
    @SerialName("transify_id")
    val transifyId: Int,

    @SerialName("hero_type")
    val heroTypes: List<HeroTypeResponse>,

    @SerialName("game_mode")
    val gameModes: List<GameModeResponse>,

    @SerialName("rank")
    val ranks: List<RankResponse>,

    @SerialName("hero")
    val heroes: Map<String, HeroResponse>
)

@Serializable
data class HeroTypeResponse(
    @SerialName("hero_type_id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("image")
    val image: String,

    @SerialName("image_active")
    val imageActive: String,

    @SerialName("hero_ids")
    val heroIds: List<String>
)

@Serializable
data class GameModeResponse(
    @SerialName("game_mode_id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("image")
    val image: String,

    @SerialName("image_active")
    val imageActive: String,

    @SerialName("has_rank")
    val hasRank: Boolean
)

@Serializable
data class RankResponse(
    @SerialName("rank_id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("image")
    val image: String
)

@Serializable
data class HeroResponse(
    @SerialName("name")
    val name: String,

    @SerialName("image")
    val image: String,

    @SerialName("hero_type")
    val heroTypeId: String
)