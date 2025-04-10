package com.tristaam.aovherorate.domain.model

data class Server(
    val id: String,
    val name: String,
    val type: ServerType,
    val baseUrl: String,
)

enum class ServerType(val shortName: String) {
    VN("vn"),
    TW("tw"),
    TH("th")
}