package com.tristaam.aovherorate.data.repository

import com.tristaam.aovherorate.data.mapper.toGameModeEntity
import com.tristaam.aovherorate.data.mapper.toHeroEntity
import com.tristaam.aovherorate.data.mapper.toHeroRateEntity
import com.tristaam.aovherorate.data.mapper.toHeroTypeEntity
import com.tristaam.aovherorate.data.mapper.toRankEntity
import com.tristaam.aovherorate.data.mapper.toServer
import com.tristaam.aovherorate.data.source.local.dao.GameModeDao
import com.tristaam.aovherorate.data.source.local.dao.GameModeRankDao
import com.tristaam.aovherorate.data.source.local.dao.HeroDao
import com.tristaam.aovherorate.data.source.local.dao.HeroRateDao
import com.tristaam.aovherorate.data.source.local.dao.HeroTypeDao
import com.tristaam.aovherorate.data.source.local.dao.RankDao
import com.tristaam.aovherorate.data.source.local.dao.ServerDao
import com.tristaam.aovherorate.data.source.local.entity.GameModeRankCrossRef
import com.tristaam.aovherorate.data.source.local.entity.HeroEntity
import com.tristaam.aovherorate.data.source.local.entity.HeroRateEntity
import com.tristaam.aovherorate.data.source.remote.dto.config.ConfigResponse
import com.tristaam.aovherorate.data.source.remote.dto.server_trend.HeroRateResponse
import com.tristaam.aovherorate.data.source.remote.service.RemoteService
import com.tristaam.aovherorate.domain.model.Result
import com.tristaam.aovherorate.domain.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class RemoteRepositoryImpl(
    private val remoteService: RemoteService,
    private val gameModeDao: GameModeDao,
    private val rankDao: RankDao,
    private val heroTypeDao: HeroTypeDao,
    private val heroDao: HeroDao,
    private val heroRateDao: HeroRateDao,
    private val gameModeRankDao: GameModeRankDao,
    private val serverDao: ServerDao
) : RemoteRepository {
    override fun getRemoteData(): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        serverDao.getAllServerEntities().collect { serverEntities ->
            try {
                coroutineScope {
                    val configDeferred =
                        async { remoteService.getConfig(serverEntities[0].toServer()) }
                    val serverTrendsDeferred = serverEntities.map { serverEntity ->
                        async {
                            serverEntity.id to remoteService.getServerTrend(serverEntity.toServer())
                        }
                    }

                    val configResponse = configDeferred.await()
                    mergeConfigIntoDatabase(configResponse)

                    val serverTrendResults = serverTrendsDeferred.awaitAll()
                    serverTrendResults.forEach { (serverId, serverTrendResponse) ->
                        mergeServerTrendIntoDatabase(serverId, serverTrendResponse)
                    }

                }
                emit(Result.Success(Unit))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun mergeConfigIntoDatabase(response: ConfigResponse) =
        withContext(Dispatchers.IO) {
            gameModeDao.upsertAllGameModeEntities(response.gameModes.map { it.toGameModeEntity() })
            rankDao.insertAllRankEntities(response.ranks.map { it.toRankEntity() })
            heroTypeDao.upsertAllHeroTypeEntities(response.heroTypes.map { it.toHeroTypeEntity() })
            val heroEntities = mutableListOf<HeroEntity>()
            response.heroes.forEach { (id, heroResponse) ->
                heroEntities.add(heroResponse.toHeroEntity(id))
            }
            heroDao.upsertAllHeroEntities(heroEntities)
        }

    private suspend fun mergeServerTrendIntoDatabase(
        serverId: String,
        response: Map<String, Map<String, List<HeroRateResponse>>>
    ) =
        withContext(Dispatchers.IO) {
            val heroRateEntities = mutableListOf<HeroRateEntity>()
            val gameModeRankCrossRefs = mutableListOf<GameModeRankCrossRef>()
            response.forEach { (gameMode, ranks) ->
                ranks.forEach { (rank, heroRates) ->
                    gameModeRankCrossRefs.add(GameModeRankCrossRef(gameMode, rank))
                    heroRates.forEach { heroRateResponse ->
                        heroRateEntities.add(
                            heroRateResponse.toHeroRateEntity(
                                serverId,
                                rank,
                                gameMode
                            )
                        )
                    }
                }
            }
            gameModeRankDao.connectAll(gameModeRankCrossRefs)
            heroRateDao.upsertAllHeroRateEntities(heroRateEntities)
        }
}