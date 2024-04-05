package com.ohanyan.xhike.domain

import com.ohanyan.xhike.network.RocketLaunch
import com.ohanyan.xhike.network.KtorExampleApi


class TestUseCase(
    val spaceXApi: KtorExampleApi
) {
    suspend fun invoke(): List<RocketLaunch> {
         return spaceXApi.getAllLaunches()
    }
}