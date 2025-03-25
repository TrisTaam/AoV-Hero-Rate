package com.tristaam.aovherorate.domain.repository

import com.tristaam.aovherorate.domain.model.Rank
import kotlinx.coroutines.flow.Flow

interface RankRepository {
    fun getAllRanks(): Flow<List<Rank>>
}