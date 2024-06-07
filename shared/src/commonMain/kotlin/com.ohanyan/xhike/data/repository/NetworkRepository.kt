package com.ohanyan.xhike.data.repository

import com.ohanyan.xhike.data.network.KtorApi
import com.ohanyan.xhike.data.network.RocketLaunch
import com.ohanyan.xhike.domain.repository.NetworkRepository

class NetworkRepositoryImpl(
    private val ktorApi: KtorApi
): NetworkRepository {
    override suspend fun getAllData(): List<RocketLaunch> {
       return ktorApi.getAllData()
    }
}