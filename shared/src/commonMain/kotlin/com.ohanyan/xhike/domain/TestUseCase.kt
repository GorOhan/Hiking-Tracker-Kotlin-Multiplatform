package com.ohanyan.xhike.domain

import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.data.network.RocketLaunch
import com.ohanyan.xhike.domain.repository.DBRepository
import com.ohanyan.xhike.domain.repository.NetworkRepository

class TestUseCase(
    val networkRepository: NetworkRepository,
    val dbRepository: DBRepository,
) {
    suspend fun invoke(): List<RocketLaunch> {
         return networkRepository.getAllData()
    }

    fun insertToDB(hikeEntity: HikeEntity)  {
        dbRepository.insertData(hikeEntity)
    }
}