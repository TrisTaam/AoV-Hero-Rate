package com.tristaam.aovherorate.data.source.remote.service

import com.tristaam.aovherorate.data.source.remote.dto.config.ConfigResponse
import com.tristaam.aovherorate.data.source.remote.dto.server_trend.HeroRateResponse
import com.tristaam.aovherorate.domain.model.Server
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class RemoteService(
    private val client: HttpClient
) {
    suspend fun getConfig(server: Server): ConfigResponse {
        return client.get {
            url("${server.baseUrl}/${server.type.shortName}/api/config")
        }.body()
    }

    suspend fun getServerTrend(server: Server): Map<String, Map<String, List<HeroRateResponse>>> {
        return client.get {
            url("${server.baseUrl}/${server.type.shortName}/api/server_trend")
        }.body()
    }
}