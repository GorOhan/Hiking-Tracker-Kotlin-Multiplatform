package com.ohanyan.xhike.data.network

import io.ktor.client.HttpClient

interface KtorApi {
    val client: HttpClient
    suspend fun getAllData(): List<RocketLaunch>
}