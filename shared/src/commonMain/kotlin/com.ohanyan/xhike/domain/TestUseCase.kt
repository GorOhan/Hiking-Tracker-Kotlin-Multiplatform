package com.ohanyan.xhike.domain

import com.ohanyan.xhike.data.network.RocketLaunch
import com.ohanyan.xhike.data.repository.NetworkRepositoryImpl
import com.ohanyan.xhike.domain.repository.NetworkRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class TestUseCase(
):KoinComponent {
    private val networkRepository: NetworkRepository by inject()

    suspend fun invoke(): List<RocketLaunch> {
         return networkRepository.getAllData()
    }
}