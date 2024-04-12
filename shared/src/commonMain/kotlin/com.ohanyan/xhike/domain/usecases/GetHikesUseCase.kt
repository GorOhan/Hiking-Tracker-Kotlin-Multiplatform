package com.ohanyan.xhike.domain.usecases

import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.repository.DBRepository

class GetHikesUseCase(
    private val dbRepository: DBRepository,
) {
    fun invoke(
    ): List<HikeEntity> {
       return dbRepository.getAllHikes()
    }
}