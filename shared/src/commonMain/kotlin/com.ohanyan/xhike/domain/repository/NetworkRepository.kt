package com.ohanyan.xhike.domain.repository

import com.ohanyan.xhike.data.network.RocketLaunch

interface NetworkRepository {
    suspend fun getAllData(): List<RocketLaunch>
}