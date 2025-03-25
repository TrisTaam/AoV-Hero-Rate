package com.tristaam.aovherorate.data.repository

import com.tristaam.aovherorate.data.mapper.toRank
import com.tristaam.aovherorate.data.source.local.dao.RankDao
import com.tristaam.aovherorate.domain.model.Rank
import com.tristaam.aovherorate.domain.repository.RankRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RankRepositoryImpl(
    private val rankDao: RankDao
) : RankRepository {
    override fun getAllRanks(): Flow<List<Rank>> {
        return rankDao.getAllRankEntities().map { rankEntities ->
            rankEntities.map { it.toRank() }
        }.flowOn(Dispatchers.IO)
    }
}