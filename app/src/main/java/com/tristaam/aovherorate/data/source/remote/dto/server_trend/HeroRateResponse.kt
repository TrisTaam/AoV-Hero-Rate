package com.tristaam.aovherorate.data.source.remote.dto.server_trend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroRateResponse(
    @SerialName("hero_id")
    val heroId: String,

    @SerialName("win_rate")
    val winRate: Float? = null,

    @SerialName("pick_rate")
    val pickRate: Float? = null,

    @SerialName("ban_rate")
    val banRate: Float? = null,

    @SerialName("top3")
    val top3: List<String>
)