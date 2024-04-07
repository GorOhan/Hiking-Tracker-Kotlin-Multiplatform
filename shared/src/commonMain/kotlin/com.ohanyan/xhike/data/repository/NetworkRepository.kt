package com.ohanyan.xhike.data.repository

import com.ohanyan.xhike.data.network.KtorExampleApi
import com.ohanyan.xhike.data.network.RocketLaunch
import com.ohanyan.xhike.domain.repository.NetworkRepository

class NetworkRepositoryImpl(
    private val ktorExampleApi: KtorExampleApi
): NetworkRepository {
    override suspend fun getAllData(): List<RocketLaunch> {
       return ktorExampleApi.getAllLaunches()
    }
}