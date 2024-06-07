package com.ohanyan.xhike.domain.usecases

import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.domain.repository.DBRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
class InsertHikeInDbUseCase:KoinComponent {
    private val dbRepository: DBRepository by inject()

    operator fun invoke(
        hikeEntity: HikeEntity
    ) {
        dbRepository.insertData(hikeEntity)
    }
}