package com.tristaam.aovherorate.data.repository

import com.tristaam.aovherorate.data.mapper.toRank
import com.tristaam.aovherorate.data.source.local.dao.RankDao
import com.tristaam.aovherorate.data.source.local.entity.RankEntity
import com.tristaam.aovherorate.domain.model.Rank
import com.tristaam.aovherorate.domain.repository.RankRepository
import kotlinx.coroutines.flow.Flow

class RankRepositoryImpl(
    private val rankDao: RankDao
) : BaseFlowRepository<RankEntity, Rank>(), RankRepository {
    override fun getAllRanks(): Flow<List<Rank>> {
        return mapFlow(rankDao.getAllRankEntities()) { it.toRank() }
    }
}