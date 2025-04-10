package com.tristaam.aovherorate.data.mapper

import com.tristaam.aovherorate.data.source.local.entity.ServerEntity
import com.tristaam.aovherorate.domain.model.Server
import com.tristaam.aovherorate.domain.model.ServerType
import kotlin.enums.enumEntries

fun Server.toServerEntity(): ServerEntity {
    return ServerEntity(
        id = id,
        name = name,
        type = type.shortName,
        baseUrl = baseUrl,
    )
}

fun ServerEntity.toServer(): Server {
    return Server(
        id = id,
        name = name,
        type = enumEntries<ServerType>().first { it.shortName == type },
        baseUrl = baseUrl,
    )
}