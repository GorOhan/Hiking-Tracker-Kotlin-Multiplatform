package com.ohanyan.xhike.domain.usecases

import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.repository.DBRepository

class InsertHikeInDbUseCase(
    private val dbRepository: DBRepository,
) {
    fun invoke(
        hikeEntity: HikeEntity
    ) {
        dbRepository.insertData(hikeEntity)
    }
}